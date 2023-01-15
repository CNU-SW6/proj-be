package cnu.swabe.v0.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String pw;
    private String nickname;
    private boolean isMale;
}
