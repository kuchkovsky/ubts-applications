package ua.org.ubts.applications.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ua.org.ubts.applications.dto.YearDto;

import java.io.IOException;

public class YearDtoSerializer extends StdSerializer<YearDto> {

    public YearDtoSerializer() {
        this(null);
    }

    public YearDtoSerializer(Class<YearDto> t) {
        super(t);
    }

    @Override
    public void serialize(YearDto year, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeNumber(year.getValue());
    }

}
