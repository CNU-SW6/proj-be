package cnu.swabe.v1.domain.user;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class User {
    private int no;
    private String id;
    private String pw;
    private String nickname;

    private boolean isMale;

    public User() {
    }

    public User(int no, String id, String pw, String nickname, boolean isMale) {
        this.no = no;
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.isMale = isMale;
    }
}
