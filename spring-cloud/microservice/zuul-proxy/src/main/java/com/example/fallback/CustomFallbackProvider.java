package com.example.fallback;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 自定义网关容错
 */
@Component
@Slf4j
public class CustomFallbackProvider implements FallbackProvider {
    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        log.info("请求出现错误, {}", cause);
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT);
        } else {
            return this.fallbackResponse();
        }
    }

    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return this.response(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ClientHttpResponse response(final HttpStatus httpStatus) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return httpStatus;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return httpStatus.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return httpStatus.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("服务不可用，请稍后重试。".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                // 这里的contenttype必须精确，不能写* mediatype.all等
                headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
                return headers;
            }
        };
    }
}
