package cnu.swabe.v2.domain.user;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserEntity {
    private int no;
    private String id;
    private String pw;
    private String nickname;

    private boolean isMale;

    public UserEntity() {
    }

    public UserEntity(int no, String id, String pw, String nickname, boolean isMale) {
        this.no = no;
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.isMale = isMale;
    }
}
