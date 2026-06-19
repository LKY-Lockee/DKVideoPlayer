package com.danikula.videocache.headers;

import java.util.HashMap;
import java.util.Map;

/**
 * Empty {@link HeaderInjector} implementation.
 *
 * @author Lucas Nelaupe (<a href="https://github.com/lucas34">...</a>).
 */
public class EmptyHeadersInjector implements HeaderInjector {

    @Override
    public Map<String, String> addHeaders(String url) {
        return new HashMap<>();
    }

}
