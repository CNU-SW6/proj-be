package cnu.swabe.v0.dto;

import lombok.Data;

/**
 * UserDTO 필요함 ?
 * 걍 도메인에 pw 없는 User 쓰는게 나을듯
 * */
@Data
public class UserDTO {
    private String id;
    private String pw;
    private String nickname;
    private boolean isMale;
}
