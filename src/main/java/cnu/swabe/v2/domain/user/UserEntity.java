package cnu.swabe.v2.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private int userNo;
    private String id;
    private String pw;
    private String nickname;
    private boolean male;

    public UserEntity(String id, String pw, String nickname, boolean male) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
        this.male = male;
    }
}
