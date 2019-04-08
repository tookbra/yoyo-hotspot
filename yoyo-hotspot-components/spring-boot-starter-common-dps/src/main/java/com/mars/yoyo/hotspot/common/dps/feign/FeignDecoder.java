package com.mars.yoyo.hotspot.common.dps.feign;

import com.mars.yoyo.hotspot.common.dps.hystrix.HystrixCredentialsContext;
import com.mars.yoyo.hotspot.common.dps.hystrix.MicroContext;
import com.mars.yoyo.hotspot.common.dps.util.RestStatusUtil;
import com.mars.yoyo.hotspot.dto.output.RestError;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * feign自定义解码器
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
public class FeignDecoder implements Decoder {
    private Decoder decoder;

    public FeignDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(final Response response, Type type) throws IOException, FeignException {
        Response resetResponse = null;
        FeignResponseAdapter responseAdpter = new FeignResponseAdapter(response);
        if (responseAdpter.canRead()) {
            List<Charset> charsets = responseAdpter.getHeaders().getAcceptCharset();
            byte[] byBody = responseAdpter.extractData();
            String body = StreamUtils.copyToString(new ByteArrayInputStream(byBody), Charset.forName("utf-8"));
            RestError restResult = RestStatusUtil.decode(body);
            if(restResult != null) {
                MicroContext securityContext = HystrixCredentialsContext.getInstance().get();
                if (securityContext != null) {
                    securityContext.setRestResult(restResult);
                }
                return null;
            } else {
                resetResponse = Response.builder().body(byBody).headers(response.headers()).status(response.status()).reason(response.reason()).request(response.request()).build();
            }
        } else {
            resetResponse = response;
        }

        if (isParameterizeHttpEntity(type)) {
            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            Object decodedObject = decoder.decode(resetResponse, type);

            return createResponse(decodedObject, resetResponse);
        } else if (isHttpEntity(type)) {
            return createResponse(null, resetResponse);
        } else {
            return decoder.decode(resetResponse, type);
        }
    }

    private boolean isParameterizeHttpEntity(Type type) {
        if (type instanceof ParameterizedType) {
            return isHttpEntity(((ParameterizedType) type).getRawType());
        }
        return false;
    }

    private boolean isHttpEntity(Type type) {
        if (type instanceof Class) {
            Class c = (Class) type;
            return HttpEntity.class.isAssignableFrom(c);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createResponse(Object instance, Response response) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        for (String key : response.headers().keySet()) {
            headers.put(key, new LinkedList<>(response.headers().get(key)));
        }

        return new ResponseEntity<>((T) instance, headers, HttpStatus.valueOf(response.status()));
    }

    private class FeignResponseAdapter implements ClientHttpResponse {

        private final Response response;

        private FeignResponseAdapter(Response response) {
            this.response = response;
        }

        public byte[] extractData() throws IOException {
            return StreamUtils.copyToByteArray(response.body().asInputStream());
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.valueOf(this.response.status());
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return this.response.status();
        }

        @Override
        public String getStatusText() throws IOException {
            return this.response.reason();
        }

        @Override
        public void close() {
            try {
                this.response.body().close();
            } catch (IOException ex) {
                // Ignore exception on close...
            }
        }

        @Override
        public InputStream getBody() throws IOException {
            return this.response.body().asInputStream();
        }

        @Override
        public HttpHeaders getHeaders() {
            return FeignUtils.getHttpHeaders(this.response.headers());
        }

        private boolean canRead() {
            MediaType contentType = getHeaders().getContentType();
            if (contentType != null && contentType != MediaType.APPLICATION_OCTET_STREAM) {
                return true;
            }
            return false;
        }

    }
}
