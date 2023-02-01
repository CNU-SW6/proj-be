package cnu.swabe.v2.service;

import cnu.swabe.v2.domain.user.UserEntity;
import cnu.swabe.v2.domain.user.dto.UserRequestDTO;
import cnu.swabe.v2.exception.custom.DuplicatedInfoException;
import cnu.swabe.v2.extradto.UserLoginDTO;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.WrongUserFormException;
import cnu.swabe.v2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cnu.swabe.v2.service.util.UserServiceUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * version - v2
     * problem1. userRequestDTO가 God Class가 되는 문제
     * problem2. transaction으로 동시성 이슈를 해결할 수 있는지
     * */
    @Transactional
    public UserEntity register(UserRequestDTO userRequestDTO) {
        if(!checkInputLength(userRequestDTO.getId())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_ID);
        if(!checkInputLength(userRequestDTO.getPw())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_PW);
        if(!checkInputLength(userRequestDTO.getNickname())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_NICKNAME);
        if(checkDuplicateNickName(userRequestDTO.getNickname())) throw new DuplicatedInfoException(ExceptionCode.EXIST_USER_NICKNAME);
        // checkDuplicatedId 구현 후 체크
        
        UserEntity user = userRepository.save(userRequestDTO);
        return user;
    }

    /**
     * version - v2
     * */
    public boolean checkDuplicateNickName(String nickname) {
        UserEntity user = userRepository.findByNickName(nickname);
        if(user != null) {
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
