package cnu.swabe.v2.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEntity {
    private int no;
    private String id;
    private String pw;
    private String nickname;

    private boolean male;

    public UserEntity() {
    }

    public UserEntity(String id, String pw, String nickname, boolean male) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.male = male;
    }
}
