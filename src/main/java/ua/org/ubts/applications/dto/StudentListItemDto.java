package ua.org.ubts.applications.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentListItemDto extends BaseDto {

    private Integer id;
    private String name;
    private ProgramDto program;

}
