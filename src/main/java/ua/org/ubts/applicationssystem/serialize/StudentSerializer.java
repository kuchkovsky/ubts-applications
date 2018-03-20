package ua.org.ubts.applicationssystem.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;
import ua.org.ubts.applicationssystem.entity.Student;

import java.io.IOException;

public class StudentSerializer extends StdSerializer<Student> {

    public StudentSerializer() {
        this(null);
    }

    public StudentSerializer(Class<Student> t) {
        super(t);
    }

    @Override
    public void serialize(Student student, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("firstName", student.getFirstName());
        jsonGenerator.writeStringField("middleName", student.getMiddleName());
        jsonGenerator.writeStringField("lastName", student.getLastName());
        jsonGenerator.writeStringField("birthDate", student.getBirthDate());
        jsonGenerator.writeStringField("phone1", student.getPhone1());
        if (StringUtils.isNotEmpty(student.getPhone2())) {
            jsonGenerator.writeStringField("phone2", student.getPhone2());
        }
        jsonGenerator.writeStringField("email", student.getEmail());
        jsonGenerator.writeObjectField("residence", student.getResidence());
        jsonGenerator.writeStringField("education", student.getEducation().getName());
        jsonGenerator.writeObjectField("program", student.getProgram());
        jsonGenerator.writeNumberField("entryYear", student.getEntryYear().getValue());
        jsonGenerator.writeObjectField("maritalData", student.getMaritalData());
        if (student.getChurchData() != null) {
            jsonGenerator.writeBooleanField("isChurchMember", true);
            jsonGenerator.writeObjectField("churchData", student.getChurchData());
        } else {
            jsonGenerator.writeBooleanField("isChurchMember", false);
        }
        if (student.getChurchMinistry() != null) {
            jsonGenerator.writeObjectField("churchMinistry", student.getChurchMinistry());
        }
        if (StringUtils.isNotEmpty(student.getJob())) {
            jsonGenerator.writeStringField("job", student.getJob());
        }
        jsonGenerator.writeStringField("donationAmount", student.getDonationAmount());
        if (StringUtils.isNotEmpty(student.getFinanceComments())) {
            jsonGenerator.writeStringField("financeComments", student.getFinanceComments());
        }
        jsonGenerator.writeObjectField("healthData", student.getHealthData());
        jsonGenerator.writeStringField("reasonsToStudy", student.getReasonsToStudy());
        jsonGenerator.writeStringField("howCameToGod", student.getHowCameToGod());
        if (student.getHowFindOut() != null) {
            jsonGenerator.writeStringField("howFindOut", student.getHowFindOut().getName());
        } else {
            jsonGenerator.writeStringField("howFindOut", "Від Бога");
        }
        jsonGenerator.writeEndObject();
    }

}
