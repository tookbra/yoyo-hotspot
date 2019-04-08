//package com.mars.yoyo.hotspot.cmp.config.rest.client;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.util.Map;
//
///**
// * @author tookbra
// * @date 2018/4/8
// * @description
// */
//public class CmpTemplate {
//
//    private RestTemplate restTemplate;
//
//    public CmpTemplate(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        return restTemplate.getForObject(url, responseType, uriVariables);
//    }
//
//    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
//        return restTemplate.getForObject(url, responseType, uriVariables);
//    }
//
//    public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException {
//        return restTemplate.getForObject(url, responseType);
//    }
//
//    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        return restTemplate.getForEntity(url, responseType, uriVariables);
//    }
//
//    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
//        return restTemplate.getForEntity(url, responseType, uriVariables);
//    }
//
//    public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException {
//        return restTemplate.getForEntity(url, responseType);
//    }
//
//    public URI postForLocation(String url, Object request, Object... uriVariables) throws RestClientException {
//        return restTemplate.postForLocation(url, request, uriVariables);
//    }
//
//    public URI postForLocation(String url, Object request, Map<String, ?> uriVariables) throws RestClientException {
//        return restTemplate.postForLocation(url, request, uriVariables);
//    }
//
//    public URI postForLocation(URI url, Object request) throws RestClientException {
//        return restTemplate.postForLocation(url, request);
//    }
//
//    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        return restTemplate.postForObject(url, request, responseType, uriVariables);
//    }
//
//    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
//        return restTemplate.postForObject(url, request, responseType, uriVariables);
//    }
//
//    public <T> T postForObject(URI url, Object request, Class<T> responseType) throws RestClientException {
//        return restTemplate.postForObject(url, request, responseType);
//    }
//
//    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
//        return restTemplate.postForEntity(url, request, responseType, uriVariables);
//    }
//
//    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
//        return restTemplate.postForEntity(url, request, responseType, uriVariables);
//    }
//
//    public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType) throws RestClientException {
//        return restTemplate.postForEntity(url, request, responseType);
//    }
//}
