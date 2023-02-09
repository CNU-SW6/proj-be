package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.user.User;
import cnu.swabe.v2.dto.UserDTO;
import cnu.swabe.v2.dto.UserLoginDTO;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.IdDuplicatedException;
import cnu.swabe.v2.exception.custom.NicknameDuplicatedException;
import cnu.swabe.v2.exception.custom.WrongInfoAccessException;
import cnu.swabe.v2.exception.custom.WrongLengthUserInfoException;
import cnu.swabe.v2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static cnu.swabe.v2.service.util.UserServiceUtil.*;

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
        checkDuplicateId(userDTO.getId());
        
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

    public void login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findUser(userLoginDTO.getId(), userLoginDTO.getPw());
        if(user == null){
            throw new WrongInfoAccessException(ExceptionCode.WRONG_INFO_USER_ACCESS);
        }
    }

    public void checkDuplicateId(String id) {
        User user = userRepository.findById(id);
        if(user != null){
            throw new IdDuplicatedException();
        }
    }
}
