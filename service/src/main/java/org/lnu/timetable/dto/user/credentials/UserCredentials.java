package org.lnu.timetable.dto.user.credentials;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCredentials {

    @ApiModelProperty(example = "oleh")
    private String username;

    @ApiModelProperty(example = "my-password9!")
    private String password;
}
