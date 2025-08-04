package com.example.odgateway.infrastructure.util;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class QueryStringUtil {

    public static MultiValueMap<String, String> getRequestParamMap(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString == null) {
            return new LinkedMultiValueMap<>();
        }

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx),
                StandardCharsets.UTF_8) : pair;
            String value = idx > 0 && pair.length() > idx + 1
                ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8) : null;
            map.add(key, value);
        }

        return map;
    }
}
