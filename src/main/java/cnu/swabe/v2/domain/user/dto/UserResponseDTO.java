package cnu.swabe.v2.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String nickname;

    @JsonProperty(value = "isMale")
    private boolean isMale;

    public UserResponseDTO(String id, String nickname, boolean isMale) {
        this.id = id;
        this.nickname = nickname;
        this.isMale = isMale;
    }
}
