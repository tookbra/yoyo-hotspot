package com.mars.yoyo.hotspot.cmp.config.rest.handler;

import com.mars.yoyo.hotspot.cmp.config.rest.CmpProperties;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.AbstractUriTemplateHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/4/9
 * @description
 */
public class CmpUriTemplateHandler extends AbstractUriTemplateHandler {

    private CmpProperties cmpProperties;

    private static final String AUTH_PARAM = "userId={0}&apikey={1}&times={2}";

    private boolean parsePath;

    private boolean strictEncoding;

    public CmpUriTemplateHandler() {
    }

    public CmpUriTemplateHandler(CmpProperties cmpProperties) {
        this.cmpProperties = cmpProperties;
    }

    public void setParsePath(boolean parsePath) {
        this.parsePath = parsePath;
    }

    public boolean shouldParsePath() {
        return this.parsePath;
    }

    public void setStrictEncoding(boolean strictEncoding) {
        this.strictEncoding = strictEncoding;
    }

    public boolean isStrictEncoding() {
        return this.strictEncoding;
    }

    @Override
    protected URI expandInternal(String uriTemplate, Map<String, ?> uriVariables) {
        UriComponentsBuilder uriComponentsBuilder = initUriComponentsBuilder(uriTemplate);
        UriComponents uriComponents = expandAndEncode(uriComponentsBuilder, uriVariables);
        return createUri(uriComponents);
    }

    @Override
    protected URI expandInternal(String uriTemplate, Object... uriVariables) {
        UriComponentsBuilder uriComponentsBuilder = initUriComponentsBuilder(uriTemplate);
        UriComponents uriComponents = expandAndEncode(uriComponentsBuilder, uriVariables);
        return createUri(uriComponents);
    }

    protected UriComponentsBuilder initUriComponentsBuilder(String uriTemplate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriTemplate);
        if (shouldParsePath() && !isStrictEncoding()) {
            List<String> pathSegments = builder.build().getPathSegments();
            builder.replacePath(null);
            for (String pathSegment : pathSegments) {
                builder.pathSegment(pathSegment);
            }
        }
        return builder;
    }

    protected UriComponents expandAndEncode(UriComponentsBuilder builder, Object[] uriVariables) {
        if (!isStrictEncoding()) {
            return builder.buildAndExpand(uriVariables).encode();
        }
        else {
            Object[] encodedUriVars = new Object[uriVariables.length];
            for (int i = 0; i < uriVariables.length; i++) {
                encodedUriVars[i] = applyStrictEncoding(uriVariables[i]);
            }
            return builder.buildAndExpand(encodedUriVars);
        }
    }

    protected UriComponents expandAndEncode(UriComponentsBuilder builder, Map<String, ?> uriVariables) {
        if (!isStrictEncoding()) {
            return builder.buildAndExpand(uriVariables).encode();
        }
        else {
            Map<String, Object> encodedUriVars = new HashMap<String, Object>(uriVariables.size());
            for (Map.Entry<String, ?> entry : uriVariables.entrySet()) {
                encodedUriVars.put(entry.getKey(), applyStrictEncoding(entry.getValue()));
            }
            String time = String.valueOf(System.currentTimeMillis());
            String auth = MessageFormat.format(AUTH_PARAM, cmpProperties.getUserId(), cmpProperties.getApiKey(), time);
            builder.queryParam("userId", cmpProperties.getUserId())
                    .queryParam("times", time)
                    .queryParam("sign", StringUtils.upperCase(DigestUtils.md5Hex(auth)));
            return builder.buildAndExpand(encodedUriVars);
        }
    }

    private String applyStrictEncoding(Object value) {
        String stringValue = (value != null ? value.toString() : "");
        try {
            return UriUtils.encode(stringValue, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            // Should never happen
            throw new IllegalStateException("Failed to encode URI variable", ex);
        }
    }

    private URI createUri(UriComponents uriComponents) {
        try {
            return new URI(uriComponents.toUriString());
        }
        catch (URISyntaxException ex) {
            throw new IllegalStateException("Could not create URI object: " + ex.getMessage(), ex);
        }
    }
}
