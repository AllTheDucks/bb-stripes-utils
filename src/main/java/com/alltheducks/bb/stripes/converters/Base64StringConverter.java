package com.alltheducks.bb.stripes.converters;

import net.sourceforge.stripes.util.Base64;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Locale;

public class Base64StringConverter implements TypeConverter<String> {

    public void setLocale(final Locale locale) {
    }

    public String convert(final String input, final Class<? extends String> targetType, final Collection<ValidationError> clctn) {
        final byte[] decodedBytes = Base64.decode(input);
        try {
            return new String(decodedBytes, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Couldn't decode base64 string: " + input, ex);
        }
    }
}