package cnu.swabe.v2.service;


import cnu.swabe.v2.domain.user.UserEntity;
import cnu.swabe.v2.domain.user.dto.UserLoginDTO;
import cnu.swabe.v2.domain.user.dto.UserSignUpDTO;
import cnu.swabe.v2.exception.custom.DuplicatedInfoException;
import cnu.swabe.v2.exception.ExceptionCode;
import cnu.swabe.v2.exception.custom.WrongInfoException;
import cnu.swabe.v2.exception.custom.WrongUserFormException;
import cnu.swabe.v2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cnu.swabe.v2.service.util.UserServiceUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * version - v2.1
     * problem1. transaction으로 동시성 이슈를 해결할 수 있는지
     * */
    @Transactional
    public UserSignUpDTO.Response register(UserSignUpDTO.Request userSignUpRequestDTO) {
        if(!checkInputLength(userSignUpRequestDTO.getId())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_ID);
        if(!checkInputLength(userSignUpRequestDTO.getPw())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_PW);
        if(!checkInputLength(userSignUpRequestDTO.getNickname())) throw new WrongUserFormException(ExceptionCode.WRONG_LENGTH_USER_NICKNAME);
        if(checkDuplicateNickName(userSignUpRequestDTO.getNickname())) throw new DuplicatedInfoException(ExceptionCode.EXIST_USER_NICKNAME);
        if(checkDuplicateId(userSignUpRequestDTO.getId())) throw new DuplicatedInfoException(ExceptionCode.EXIST_USER_ID);

        UserEntity user = modelMapper.map(userSignUpRequestDTO, UserEntity.class);
        userRepository.save(user);

        UserSignUpDTO.Response userSignUpResponseDTO = new UserSignUpDTO.Response(user.getUserNo());
        return userSignUpResponseDTO;
    }

    /**
     * version - v2.1
     * */
    public boolean checkDuplicateNickName(String nickname) {
        UserEntity user = userRepository.findByNickName(nickname);
        if(user != null) {
            return true;
        }
        return false;
    }

    /**
     * version - v2.1
     * */
    public boolean checkDuplicateId(String id) {
        UserEntity user = userRepository.findById(id);
        if(user != null){
            return true;
        }
        return false;
    }

    /**
     * version - v2.1
     * */
    public UserLoginDTO.Response login(UserLoginDTO.Request userLoginRequestDTO) {
        UserEntity user = userRepository.findByIdAndPw(userLoginRequestDTO.getId(), userLoginRequestDTO.getPw());
        if(user == null){
            throw new WrongInfoException(ExceptionCode.WRONG_USER_INFO);
        }

        UserLoginDTO.Response userLoginResponseDTO = new UserLoginDTO.Response(user.getUserNo());

        return userLoginResponseDTO;
    }
}
