package cnu.swabe.v1.service;

import cnu.swabe.v1.domain.user.User;
import cnu.swabe.v1.dto.UserDTO;
import cnu.swabe.v1.dto.UserLoginDTO;
import cnu.swabe.v1.exception.ExceptionCode;
import cnu.swabe.v1.exception.custom.IdDuplicatedException;
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

    public boolean login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findUser(userLoginDTO.getId(), userLoginDTO.getPw());
        log.info("idDTO = {}, pwDTO= {}, id = {}, pw= {}", userLoginDTO.getId(), userLoginDTO.getPw(), user.getId(), user.getPw());
        if (user.getId().equals(userLoginDTO.getId()) && user.getPw().equals(userLoginDTO.getPw())) {
            return true;
        }else{
            return false;
        }
    }

    public void checkDuplicateId(String id) {
        User user = userRepository.findById(id);
        if(user != null){
            throw new IdDuplicatedException();
        }
    }
}
