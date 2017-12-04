/*
 * Copyright 2017 Hippo B.V. (http://www.onehippo.com)
 */

package org.onehippo.crosslink.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Bloomreach provider rest template which is injecting query variables
 * (instead of path variables)
 */
@SuppressWarnings("unchecked")
public class DataSourceRestTemplate extends RestTemplate {

    private static final Logger log = LoggerFactory.getLogger(DataSourceRestTemplate.class);
    private Map<String, ?> queryVariables;

    public Map<String, ?> getQueryVariables() {
        return queryVariables;
    }
    public void setQueryVariables(final Map<String, ?> queryVariables) {
        this.queryVariables = queryVariables;
    }


    @Override
    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
                         ResponseExtractor<T> responseExtractor, Map<String, ?> urlVariables) throws RestClientException {

        URI expanded = getUriTemplateHandler().expand(url, urlVariables);

        expanded = processPathAndQueryVariables(expanded, queryVariables);
        expanded = processPathAndQueryVariables(expanded, urlVariables);
        URI cleanUri = expanded;

        try {
            cleanUri = new URI(expanded.toString().replaceAll("(&?\\w+=((?=$)|(?=&)))", ""));
        } catch (URISyntaxException e) {
            // Ignore
        }

        log.info("Calling url: {}", expanded);

        return doExecute(cleanUri, method, requestCallback, responseExtractor);
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        processHeaders(headers, uriVariables);
        RequestCallback requestCallback;
        if (!headers.isEmpty()) {
            final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            requestCallback = this.httpEntityCallback(entity, responseType);
        } else {
            requestCallback = this.acceptHeaderRequestCallback(responseType);
        }
        ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
        return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {
        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        processHeaders(headers, urlVariables);
        RequestCallback requestCallback;
        if (!headers.isEmpty()) {
            final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
            requestCallback = this.httpEntityCallback(entity, responseType);
        } else {
            requestCallback = this.acceptHeaderRequestCallback(responseType);
        }
        final ResponseExtractor<ResponseEntity<T>> responseExtractor = this.responseEntityExtractor(responseType);
        return this.execute(url, HttpMethod.GET, requestCallback, responseExtractor, urlVariables);
    }

    private void processHeaders(MultiValueMap<String, String> headers, final Map<String, ?> variables) {
        if (variables == null) {
            return;
        }

        for (Map.Entry<String, ?> entry : variables.entrySet()) {

            final Object value = entry.getValue();

            if (value instanceof RestVariable) {
                RestVariable var = (RestVariable)value;
                switch (var.getType()) {
                    case HEADER:
                        headers.add(entry.getKey(), var.toString());
                        break;
                }

                continue;
            }
        }
    }

    protected URI processPathAndQueryVariables(final URI uri, final Map<String, ?> variables) {
        if (variables == null) {
            return uri;
        }
        String rawQuery = null;
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uri);
        Map<String, String> pathVariables = new HashMap<>();

        for (Map.Entry<String, ?> entry : variables.entrySet()) {
            final Object value = entry.getValue();

            if (value instanceof RestVariable) {
                RestVariable var = (RestVariable)value;
                switch (var.getType()) {
                    case QUERY:
                        builder.replaceQueryParam(entry.getKey(), var.toString());
                        break;
                    case PATH:
                        pathVariables.put(entry.getKey(), var.toString());
                        break;
                }

                continue;
            }
            //builder.replaceQueryParam(entry.getKey(), value);
        }
//        if (!Strings.isNullOrEmpty(rawQuery)) {
//            String all =  builder.build().toString();
//            all = all.indexOf('?') != -1 ? all + prepareRawQuery(rawQuery,'&') : all + prepareRawQuery(rawQuery,'?');
//            return UriComponentsBuilder.fromHttpUrl(all).build().toUri();
//        }

        return builder.buildAndExpand(pathVariables).toUri();
    }


    private String prepareRawQuery(final String rawQuery, final char c) {
        if (rawQuery.charAt(0) == c) {
            return rawQuery;
        }
        return c + rawQuery;
    }


}

