package cnu.swabe.domain;

import lombok.Data;

@Data
public class User {
    private String id;
    private String pw;
    private String nickname;
    private boolean isMale;
}
