package com.mars.yoyo.hotspot.cmp.config.rest.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author tookbra
 * @date 2018/4/9
 * @description
 */
@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        log.debug("===========================request begin================================================");
        log.debug("URI         : {}", request.getURI());
        log.debug("Method      : {}", request.getMethod());
        log.debug("Headers     : {}", request.getHeaders() );
        log.debug("Request body: {}", new String(body, "UTF-8"));
        log.debug("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {

//        CloneHttpResponse cloneResponse = new CloneHttpResponse(response);
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        log.debug("============================response begin==========================================");
        log.debug("Status code  : {}", response.getStatusCode());
        log.debug("Status text  : {}", response.getStatusText());
        log.debug("Headers      : {}", response.getHeaders());
        log.debug("Response body: {}", inputStringBuilder.toString());
        log.debug("=======================response end=================================================");
    }

    private class CloneHttpResponse extends AbstractClientHttpResponse {

        private ClientHttpResponse response;
        private InputStream responseStream;
        private String bodyString;

        public CloneHttpResponse(ClientHttpResponse response) {
            this.response = response;
            try {
                bodyString = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
                responseStream = new ByteArrayInputStream(bodyString.getBytes(StandardCharsets.UTF_8.name()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String bodyString(){
            return bodyString;
        }

        @Override
        public HttpHeaders getHeaders() {
            return response.getHeaders();
        }

        @Override
        public InputStream getBody() throws IOException {
            return responseStream;
        }

        @Override
        public String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return response.getRawStatusCode();
        }

        @Override
        public void close() {
            if (this.responseStream != null) {
                try {
                    StreamUtils.drain(this.responseStream);
                    this.responseStream.close();
                }
                catch (IOException ex) {
                    // ignore
                }
            }
        }
    }

}
