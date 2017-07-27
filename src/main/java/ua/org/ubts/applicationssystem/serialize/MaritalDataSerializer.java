package ua.org.ubts.applicationssystem.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;
import ua.org.ubts.applicationssystem.entity.MaritalData;

import java.io.IOException;

public class MaritalDataSerializer extends StdSerializer<MaritalData> {

    public MaritalDataSerializer() {
        this(null);
    }

    public MaritalDataSerializer(Class<MaritalData> t) {
        super(t);
    }

    @Override
    public void serialize(MaritalData maritalData, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("status", maritalData.getStatus().getName());
        if (StringUtils.isNotEmpty(maritalData.getSpouseName())) {
            jsonGenerator.writeStringField("spouseName", maritalData.getSpouseName());
        }
        if (StringUtils.isNotEmpty(maritalData.getMarriageDate())) {
            jsonGenerator.writeStringField("marriageDate", maritalData.getMarriageDate());
        }
        if (StringUtils.isNotEmpty(maritalData.getSpouseChurchService())) {
            jsonGenerator.writeStringField("spouseChurchService", maritalData.getSpouseChurchService());
        }
        if (maritalData.getChildrenNumber() != null) {
            jsonGenerator.writeNumberField("childrenNumber", maritalData.getChildrenNumber());
        }
        if (maritalData.isSpouseChurchMember() != null) {
            jsonGenerator.writeBooleanField("isSpouseChurchMember", maritalData.isSpouseChurchMember());
        }
        if (maritalData.isSpouseApproveSeminary() != null) {
            jsonGenerator.writeBooleanField("isSpouseApproveSeminary", maritalData.isSpouseApproveSeminary());
        }
        jsonGenerator.writeEndObject();
    }

}
