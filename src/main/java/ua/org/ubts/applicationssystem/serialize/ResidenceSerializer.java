package ua.org.ubts.applicationssystem.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;
import ua.org.ubts.applicationssystem.entity.Residence;

import java.io.IOException;

public class ResidenceSerializer extends StdSerializer<Residence> {

    public ResidenceSerializer() {
        this(null);
    }

    public ResidenceSerializer(Class<Residence> t) {
        super(t);
    }

    @Override
    public void serialize(Residence residence, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("country", residence.getCountry().getName());
        jsonGenerator.writeStringField("region", residence.getRegion());
        jsonGenerator.writeStringField("cityVillage", residence.getCityVillage());
        jsonGenerator.writeStringField("index", residence.getIndex());
        if (StringUtils.isNotEmpty(residence.getDistrict())) {
            jsonGenerator.writeStringField("district", residence.getDistrict());
        }
        jsonGenerator.writeStringField("street", residence.getStreet());
        jsonGenerator.writeStringField("house", residence.getHouse());
        if (StringUtils.isNotEmpty(residence.getApartment())) {
            jsonGenerator.writeStringField("apartment", residence.getApartment());
        }
        jsonGenerator.writeEndObject();
    }

}
