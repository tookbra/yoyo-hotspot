package com.mars.yoyo.hotspot.common.dps.feign;

import com.mars.yoyo.hotspot.common.dps.util.HttpUtil;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;

/**
 * feign自定义编码器
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
public class FeignEncoder implements Encoder {
    private ObjectFactory<HttpMessageConverters> messageConverters;

    public FeignEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }



    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (object != null) {
            Class<?> requestType = object.getClass();
            Collection<String> contentTypes = template.headers().get(HttpHeaders.CONTENT_TYPE);
            MediaType requestContentType = null;
            if (contentTypes != null && !contentTypes.isEmpty()) {
                String type = contentTypes.iterator().next();
                requestContentType = MediaType.valueOf(type);
            }
            HttpServletRequest request = HttpUtil.getRequest();
            if (null != request) {
                String lang = request.getHeader("lang");
                if(StringUtils.isNotBlank(lang)) {
                    template.header("lang", lang);
                }
                String userId = request.getHeader("userId");
                if(StringUtils.isNotBlank(userId)) {
                    template.header("userId", userId);
                }
                String requestIp = request.getHeader("requestIp");
                if(StringUtils.isNotBlank(requestIp)) {
                    template.header("requestIp", requestIp);
                }
                String clientType = request.getHeader("clientType");
                if(StringUtils.isNotBlank(clientType)) {
                    template.header("clientType", clientType);
                }
            }
            for (HttpMessageConverter<?> messageConverter : this.messageConverters.getObject().getConverters()) {
                if (messageConverter.canWrite(requestType, requestContentType)) {

                    FeignOutputMessage outputMessage = new FeignOutputMessage(template);
                    try {
                        @SuppressWarnings("unchecked")
                        HttpMessageConverter<Object> copy = (HttpMessageConverter<Object>) messageConverter;
                        copy.write(object, requestContentType, outputMessage);
                    } catch (IOException ex) {
                        throw new EncodeException("Error converting request body", ex);
                    }
                    template.headers(null);
                    template.headers(FeignUtils.getHeaders(outputMessage.getHeaders()));
                    template.body(outputMessage.getOutputStream().toByteArray(), Charset.forName("UTF-8"));
                    // TODO:
                    return;
                }
            }
            String message = "Could not write request: no suitable HttpMessageConverter " + "found for request type ["
                    + requestType.getName() + "]";
            if (requestContentType != null) {
                message += " and content type [" + requestContentType + "]";
            }
            throw new EncodeException(message);
        }
    }

    private class FeignOutputMessage implements HttpOutputMessage {

        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        private final HttpHeaders httpHeaders;

        private FeignOutputMessage(RequestTemplate request) {
            httpHeaders = FeignUtils.getHttpHeaders(request.headers());
        }

        @Override
        public OutputStream getBody() throws IOException {
            return this.outputStream;
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }

        public ByteArrayOutputStream getOutputStream() {
            return this.outputStream;
        }
    }
}
