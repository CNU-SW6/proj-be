package cnu.swabe.v1.service;

import cnu.swabe.v1.domain.user.User;
import cnu.swabe.v1.dto.UserDTO;
import cnu.swabe.v1.exception.ExceptionCode;
import cnu.swabe.v1.exception.custom.NicknameDuplicatedException;
import cnu.swabe.v1.exception.custom.WrongLengthUserInfoException;
import cnu.swabe.v1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static cnu.swabe.v1.service.util.UserServiceUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * version - v1
     * */
    public User addUser(UserDTO userDTO) {
        if(!checkInputLength(userDTO.getId())) throw new WrongLengthUserInfoException(ExceptionCode.WRONG_LENGTH_USER_ID);
        if(!checkInputLength(userDTO.getPw())) throw new WrongLengthUserInfoException(ExceptionCode.WRONG_LENGTH_USER_PW);
        if(!checkInputLength(userDTO.getNickname())) throw new WrongLengthUserInfoException(ExceptionCode.WRONG_LENGTH_USER_NICKNAME);
        checkDuplicateNickName(userDTO.getNickname());
        // checkDuplicatedId 구현 후 체크
        
        User user = userRepository.save(userDTO);
        return user;
    }

    /**
     * version - v1
     * refactoring 필요해 보임
     * */
    public void checkDuplicateNickName(String nickname) {
        User user = userRepository.findByNickName(nickname);
        if(user != null) {
            throw new NicknameDuplicatedException();
        }
    }

    public User login(UserDTO userDTO) {
        User userbyId = userRepository.findById(userDTO.getId());
        User userbyPw = userRepository.findByPw(userDTO.getPw());

        // 객체 비교 이렇게 노노
        if (userbyId == userbyPw) {
            return userbyPw;
        }
        else {
            return null;
        }
    }

    public boolean checkDuplicateId(String id) {
        User user = userRepository.findById(id);
        if(user == null){
            return false;
        }else{
            return true;
        }
    }
}
