package com.xspaceagi.system.spec.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import java.io.IOException;

public class LongToStringSerializer extends NumberSerializers.Base<Object> {
    public LongToStringSerializer() {
        super(Long.class, NumberType.LONG, "number");
    }

    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        long number = (Long)value;
        if (number >= 10000000000000000L) {
            gen.writeString(number + "");
        } else {
            gen.writeNumber(number);
        }

    }
}
