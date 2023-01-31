package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.user.UserEntity;
import cnu.swabe.v2.domain.user.dto.UserDTO;
import cnu.swabe.v2.extradto.UserLoginDTO;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.NicknameDuplicatedException;
import cnu.swabe.v2.exception.custom.WrongLengthUserInfoException;
import cnu.swabe.v2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
    public UserEntity addUser(UserDTO userDTO) {
        if(!checkInputLength(userDTO.getId())) throw new WrongLengthUserInfoException(ExceptionCode.WRONG_LENGTH_USER_ID);
        if(!checkInputLength(userDTO.getPw())) throw new WrongLengthUserInfoException(ExceptionCode.WRONG_LENGTH_USER_PW);
        if(!checkInputLength(userDTO.getNickname())) throw new WrongLengthUserInfoException(ExceptionCode.WRONG_LENGTH_USER_NICKNAME);
        checkDuplicateNickName(userDTO.getNickname());
        // checkDuplicatedId 구현 후 체크
        
        UserEntity userEntity = userRepository.save(userDTO);
        return userEntity;
    }

    /**
     * version - v2
     * */
    public boolean checkDuplicateNickName(String nickname) {
        UserEntity userEntity = userRepository.findByNickName(nickname);
        if(userEntity != null) {
            return true;
        }
        return false;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        UserEntity userEntity = userRepository.findUser(userLoginDTO.getId(), userLoginDTO.getPw());
        log.info("idDTO = {}, pwDTO= {}, id = {}, pw= {}", userLoginDTO.getId(), userLoginDTO.getPw(), userEntity.getId(), userEntity.getPw());
        if (userEntity.getId().equals(userLoginDTO.getId()) && userEntity.getPw().equals(userLoginDTO.getPw())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDuplicateId(String id) {
        UserEntity userEntity = userRepository.findById(id);
        if(userEntity == null){
            return false;
        }else{
            return true;
        }
    }
}
