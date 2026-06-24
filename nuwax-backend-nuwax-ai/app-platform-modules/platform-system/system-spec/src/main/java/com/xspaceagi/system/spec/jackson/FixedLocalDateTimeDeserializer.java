package com.xspaceagi.system.spec.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FixedLocalDateTimeDeserializer extends LocalDateTimeDeserializer {
    private static final DateTimeFormatter DEFAULT_FORMATTER;

    public FixedLocalDateTimeDeserializer(DateTimeFormatter formatter) {
        super(formatter);
    }

    protected LocalDateTime _fromString(JsonParser p, DeserializationContext ctxt, String string0) throws IOException {
        String string = string0.trim();
        if (string.length() == 0) {
            return (LocalDateTime)this._fromEmptyString(p, ctxt, string);
        } else {
            try {
                return string.length() > 10 && string.charAt(10) == 'T' ? LocalDateTime.parse(string, DEFAULT_FORMATTER) : LocalDateTime.parse(string, this._formatter);
            } catch (DateTimeException var6) {
                return (LocalDateTime)this._handleDateTimeException(ctxt, var6, string);
            }
        }
    }

    static {
        DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }
}