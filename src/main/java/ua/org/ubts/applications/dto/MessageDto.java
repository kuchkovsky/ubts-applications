package ua.org.ubts.applications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto extends BaseDto {

    private String message;

}
