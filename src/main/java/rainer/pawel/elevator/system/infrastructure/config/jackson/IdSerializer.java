package rainer.pawel.elevator.system.infrastructure.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

import rainer.pawel.elevator.system.domain.Id;

public class IdSerializer extends StdSerializer<Id> {

    public IdSerializer(Class<Id> t) {
        super(t);
    }

    @Override
    public void serialize(Id id, JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeString(id.asString());
    }
}