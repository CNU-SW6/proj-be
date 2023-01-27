package cnu.swabe.v0.service;

import cnu.swabe.v0.domain.User;
import cnu.swabe.v0.dto.UserDTO;
import cnu.swabe.v0.exception.ErrorCode;
import cnu.swabe.v0.exception.custom.NicknameDuplicatedException;
import cnu.swabe.v0.exception.custom.WrongLengthUserInfoException;
import cnu.swabe.v0.repository.UserRepository;
import cnu.swabe.v0.service.util.UserServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static cnu.swabe.v0.service.util.UserServiceUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * version - v1
     * */
    public User addUser(UserDTO userDTO) {
        if(!checkInputLength(userDTO.getId())) {
            throw new WrongLengthUserInfoException(ErrorCode.WRONG_LENGTH_USER_ID);
        }

        if(!checkInputLength(userDTO.getPw())) {
            throw new WrongLengthUserInfoException(ErrorCode.WRONG_LENGTH_USER_PW);
        }

        if(!checkInputLength(userDTO.getNickname())) {
            throw new WrongLengthUserInfoException(ErrorCode.WRONG_LENGTH_USER_NICKNAME);
        }



        //int addedUserNo = userRepository.save(userDTO);
        return null;
    }

    /**
     * version - v1
     * */
    public User checkDuplicateNickName(String nickname) {
        User user = userRepository.findByNickName(nickname);
        if(user == null) {
            throw new NicknameDuplicatedException();
        }

        return user;
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
