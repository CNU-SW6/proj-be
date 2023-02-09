package cnu.swabe.v2.service;


import cnu.swabe.v2.domain.user.UserEntity;
import cnu.swabe.v2.domain.user.dto.UserSignUpRequestDTO;
import cnu.swabe.v2.domain.user.dto.UserSignUpResponseDTO;
import cnu.swabe.v2.dto.UserLoginDTO;
import cnu.swabe.v2.exception.custom.DuplicatedInfoException;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.IdDuplicatedException;
import cnu.swabe.v2.exception.custom.WrongInfoAccessException;
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
    public UserSignUpResponseDTO register(UserSignUpRequestDTO userRequestDTO) {
        if(!checkInputLength(userRequestDTO.getId())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_ID);
        if(!checkInputLength(userRequestDTO.getPw())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_PW);
        if(!checkInputLength(userRequestDTO.getNickname())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_NICKNAME);
        if(checkDuplicateNickName(userRequestDTO.getNickname())) throw new DuplicatedInfoException(ExceptionCode.EXIST_USER_NICKNAME);
        // checkDuplicatedId 구현 후 체크

        UserEntity user = userRepository.save(userRequestDTO);

        UserSignUpResponseDTO userSignUpResponse = new UserSignUpResponseDTO(user.getNo());
        return userSignUpResponse;
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

    public void login(UserLoginDTO userLoginDTO) {
        UserEntity user = userRepository.findUser(userLoginDTO.getId(), userLoginDTO.getPw());
        if(user == null){
            throw new WrongInfoAccessException(ExceptionCode.WRONG_INFO_USER_ACCESS);
        }
    }

    public void checkDuplicateId(String id) {
        UserEntity user = userRepository.findById(id);
        if(user != null){
            throw new IdDuplicatedException();
        }
    }
}
