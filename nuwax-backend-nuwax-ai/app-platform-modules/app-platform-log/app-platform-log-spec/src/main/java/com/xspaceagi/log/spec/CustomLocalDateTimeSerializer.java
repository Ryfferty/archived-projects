package com.xspaceagi.log.spec;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义 LocalDateTime 的序列化器,按照UTC时间格式输出
 */
public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {


    private static final String UTC_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final ZoneId BEIJING_ZONE_ID = ZoneId.of("Asia/Shanghai");
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");


    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(UTC_DATE_TIME_PATTERN);


    public CustomLocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            // LocalDateTime 是东八区的本地时间，将其转换为 UTC 时间
            ZonedDateTime beijingDateTime = value.atZone(BEIJING_ZONE_ID);
            ZonedDateTime utcDateTime = beijingDateTime.withZoneSameInstant(UTC_ZONE_ID);
            // 使用指定的格式输出 UTC 时间
            gen.writeString(utcDateTime.format(formatter));
        } else {
            gen.writeNull();
        }
    }
}
