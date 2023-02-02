package cnu.swabe.v2.domain.user.dto;

import lombok.Data;

@Data
public class UserSignUpResponseDTO {
    private int userNo;

    public UserSignUpResponseDTO(int userNo) {
        this.userNo = userNo;
    }
}
