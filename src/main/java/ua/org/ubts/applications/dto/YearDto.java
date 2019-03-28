package ua.org.ubts.applications.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.org.ubts.applications.serializer.YearDtoSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = YearDtoSerializer.class)
public class YearDto extends BaseDto {

    private Integer value;

}
