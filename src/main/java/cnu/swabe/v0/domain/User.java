package cnu.swabe.v0.domain;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class User {
    private int no;
    private String id;
    private String pw;
    private String nickname;
    private boolean isMale;
}
