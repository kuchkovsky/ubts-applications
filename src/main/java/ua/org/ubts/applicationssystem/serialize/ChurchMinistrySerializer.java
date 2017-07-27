package ua.org.ubts.applicationssystem.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;
import ua.org.ubts.applicationssystem.entity.ChurchMinistry;

import java.io.IOException;

public class ChurchMinistrySerializer extends StdSerializer<ChurchMinistry> {

    public ChurchMinistrySerializer() {
        this(null);
    }

    public ChurchMinistrySerializer(Class<ChurchMinistry> t) {
        super(t);
    }

    @Override
    public void serialize(ChurchMinistry churchMinistry, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("repentanceDate", churchMinistry.getRepentanceDate());
        jsonGenerator.writeStringField("baptismDate", churchMinistry.getBaptismDate());
        jsonGenerator.writeStringField("type", churchMinistry.getType().getName());
        if (StringUtils.isNotEmpty(churchMinistry.getOrdinationDate())) {
            jsonGenerator.writeStringField("ordinationDate", churchMinistry.getOrdinationDate());
        }
        jsonGenerator.writeStringField("churchParticipation", churchMinistry.getChurchParticipation());
        jsonGenerator.writeEndObject();
    }

}
