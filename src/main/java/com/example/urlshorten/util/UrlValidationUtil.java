package com.example.urlshorten.util;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlValidationUtil {
    private static final UrlValidator validator = new UrlValidator(new String[]{"http", "https"});

    public static boolean isValidUrl(String url) {
        return validator.isValid(url);
    }
}
