package com.xspaceagi.system.spec.jackson;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.util.ObjectUtils;

public class JacksonBuilder {
    private ObjectMapper objectMapper;
    private final JacksonProperties jacksonProperties;
    private final SimpleModule customModule;

    private JacksonBuilder() {
        this(new JacksonProperties());
    }

    private JacksonBuilder(JacksonProperties jacksonProperties) {
        this.jacksonProperties = jacksonProperties;
        this.customModule = new SimpleModule();
        this.defaultObjectMapper();
    }

    private void defaultObjectMapper() {
        DefaultSerializerProvider provider = new DefaultSerializerProvider.Impl();
        provider.setNullKeySerializer(new NullKeySerializer());
        this.customModule.addKeySerializer(Integer.class, new FixedNumberKeySerializer(5, Integer.class));
        this.customModule.addKeySerializer(Long.class, new FixedNumberKeySerializer(6, Integer.class));
        this.objectMapper = (new ObjectMapper()).setSerializationInclusion(Include.NON_NULL).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).setVisibility(PropertyAccessor.ALL, Visibility.ANY).registerModule(this.javaTimeModule()).registerModule(new Jdk8Module());
        String utilDateFormat = this.jacksonProperties.getUtilDateFormat();
        if (!ObjectUtils.isEmpty(utilDateFormat)) {
            this.objectMapper.setDateFormat(new SimpleDateFormat(utilDateFormat));
        }

    }

    private Module javaTimeModule() {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(this.localDateTimeSerializer());
        module.addSerializer(this.localDateSerializer());
        module.addSerializer(this.localTimeSerializer());
        module.addDeserializer(LocalDateTime.class, this.fixedLocalDateTimeDeserializer());
        module.addDeserializer(LocalDate.class, this.localDateDeserializer());
        module.addDeserializer(LocalTime.class, this.localTimeDeserializer());
        return module;
    }

    private LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(this.jacksonProperties.getDateTimeFormat()));
    }

    private FixedLocalDateTimeDeserializer fixedLocalDateTimeDeserializer() {
        return new FixedLocalDateTimeDeserializer(DateTimeFormatter.ofPattern(this.jacksonProperties.getDateTimeFormat()));
    }

    private LocalDateSerializer localDateSerializer() {
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(this.jacksonProperties.getDateFormat()));
    }

    private LocalTimeSerializer localTimeSerializer() {
        return new LocalTimeSerializer(DateTimeFormatter.ofPattern(this.jacksonProperties.getTimeFormat()));
    }

    private LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(this.jacksonProperties.getDateTimeFormat()));
    }

    private LocalDateDeserializer localDateDeserializer() {
        return new LocalDateDeserializer(DateTimeFormatter.ofPattern(this.jacksonProperties.getDateFormat()));
    }

    private LocalTimeDeserializer localTimeDeserializer() {
        return new LocalTimeDeserializer(DateTimeFormatter.ofPattern(this.jacksonProperties.getTimeFormat()));
    }

    public JacksonBuilder writeNull() {
        this.objectMapper.setSerializationInclusion(Include.ALWAYS);
        return this;
    }

    public JacksonBuilder withJavaType() {
        this.objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, DefaultTyping.NON_FINAL, As.PROPERTY);
        return this;
    }

    public JacksonBuilder addSerializer(JsonSerializer<?> ser) {
        this.customModule.addSerializer(ser);
        return this;
    }

    public ObjectMapper build() {
        this.objectMapper.registerModule(this.customModule);
        return this.objectMapper;
    }

    public static JacksonBuilder builder() {
        return new JacksonBuilder();
    }

    public static JacksonBuilder builder(JacksonProperties jacksonProperties) {
        return new JacksonBuilder(jacksonProperties);
    }
}
