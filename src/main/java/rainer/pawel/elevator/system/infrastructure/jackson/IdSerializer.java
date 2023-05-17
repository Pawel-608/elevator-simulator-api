package rainer.pawel.elevator.system.infrastructure.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import rainer.pawel.elevator.system.domain.Id;
import java.io.IOException;

public class IdSerializer extends StdSerializer<Id> {

    public IdSerializer(Class<Id> t) {
        super(t);
    }

    @Override
    public void serialize(
            Id id, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeString(id.asString());
    }
}