package com.mars.yoyo.hotspot.device;

import com.mars.yoyo.hotspot.device.codec.MessageCodecAggregator;
import com.mars.yoyo.hotspot.device.codec.HeaderCodec;
import com.mars.yoyo.hotspot.device.handler.BlackHoleHandler;
import com.mars.yoyo.hotspot.device.handler.DispatchHandler;
import com.mars.yoyo.hotspot.device.packet.State;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Slf4j
@Component
public class BoxServer {

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private ServerBootstrap serverBootstrap;

    protected final AtomicReference<State> serverState = new AtomicReference<>(State.Created);

    @Autowired
    BoxProperties boxProperties;

    @Autowired
    DispatchHandler dispatchHandler;

    private void init() {
        bossGroup = new NioEventLoopGroup(boxProperties.getBossLoopGroupCount(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "boss_" + index.incrementAndGet());
            }
        });
        workerGroup = new NioEventLoopGroup(boxProperties.getWorkerLoopGroupCount(), new ThreadFactory() {
            private AtomicInteger index = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "worker_" + index.incrementAndGet());
            }
        });
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                // tcp/ip协议 backlog
                // 初始化服务端可连接队列
                // backlog 指定队列大小
                .option(ChannelOption.SO_BACKLOG, 2048)
                .option(ChannelOption.SO_RCVBUF, 256 * 1024)
                .option(ChannelOption.SO_SNDBUF, 256 * 1024)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(channelInitializer());
    }

    private ChannelInitializer channelInitializer() {
        return new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pip = ch.pipeline();
                pip.addLast("FrameDecoder", new LengthFieldBasedFrameDecoder( 18247 ,
                        0, 2, 0, 0, true));
                pip.addLast("ChildLogger",new LoggingHandler(LogLevel.TRACE));
                pip.addLast("idle", new IdleStateHandler(0,0, 15, TimeUnit.SECONDS));
                pip.addLast("PacketCodec", new HeaderCodec());
                pip.addLast("Aggregtor", MessageCodecAggregator.INSTANCE);
                pip.addLast("handler", dispatchHandler);
                // 黑洞处理，丢弃所有消息
                pip.addLast("BlackHole", new BlackHoleHandler());
            }
        };
    }

    public void start() {
        try {
            init();
            ChannelFuture f = serverBootstrap.bind(boxProperties.getPort()).sync();
            f.channel().closeFuture().addListener((ChannelFutureListener) future -> {
                log.info("future channel close");
                stop();
            });
            f.addListener(future -> {
                if(future.isSuccess()) {
                    serverState.set(State.Connect);
                    log.info("server start success on:{}", boxProperties.getPort());
                } else {
                    log.error("server start failure on:{}", boxProperties.getPort(), future.cause());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            if (bossGroup != null && !bossGroup.isShutdown()) {
                bossGroup.shutdownGracefully();
                bossGroup.terminationFuture().sync();
                bossGroup = null;
            }

            if (workerGroup != null && !workerGroup.isShutdown()) {
                workerGroup.shutdownGracefully();
                workerGroup.terminationFuture().sync();
                workerGroup = null;
            }
        } catch (Exception e) {
            log.error("shutdown netty gracefully error", e);
        }
    }

}
