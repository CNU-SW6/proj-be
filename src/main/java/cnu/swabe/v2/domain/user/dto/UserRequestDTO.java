package cnu.swabe.v2.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String id;
    private String pw;
    private String nickname;
    @JsonProperty(value = "isMale")
    private boolean male;
}
