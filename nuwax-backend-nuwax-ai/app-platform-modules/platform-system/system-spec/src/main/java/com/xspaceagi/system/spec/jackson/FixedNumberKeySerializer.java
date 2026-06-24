package com.xspaceagi.system.spec.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixedNumberKeySerializer extends StdKeySerializers.Default {
    private static final Logger log = LoggerFactory.getLogger(FixedNumberKeySerializer.class);
    private Class<?> cls;

    public FixedNumberKeySerializer(int typeId, Class<?> type) {
        super(typeId, type);
        this.cls = type;
    }

    public void serialize(Object value, JsonGenerator g, SerializerProvider provider) throws IOException {
        if (this.isNumber() && !(value instanceof Number)) {
            log.warn("javaType:{},valueType:{} 不匹配,请检查!", this.cls.getName(), value.getClass().getName());
            g.writeFieldName(value.toString());
        } else {
            super.serialize(value, g, provider);
        }
    }

    private boolean isNumber() {
        return this._typeId == 5 || this._typeId == 6;
    }
}
