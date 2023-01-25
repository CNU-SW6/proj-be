package cnu.swabe.v0.service;

import cnu.swabe.v0.domain.User;
import cnu.swabe.v0.dto.UserDTO;
import cnu.swabe.v0.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 여기서도 중복체크를 해야할까?
     * 비즈니스 로직이 필요할까?
     * */
    @Transactional
    public int addUser(UserDTO userDTO) {
        int addedUserNo = userRepository.save(userDTO);
        return addedUserNo;
    }

    /**
     * 여기서도 중복체크를 해야할까?
     * 비즈니스 로직이 필요할까?
     * */
    public boolean checkDuplicateNickName(String nickname) {
        boolean isExist = userRepository.findByNickName(nickname);
        return isExist;
    }

    public User login(UserDTO userDTO) {
        User userbyId = userRepository.findById(userDTO.getId());
        User userbyPw = userRepository.findByPw(userDTO.getPw());

        if (userbyId == userbyPw) {
            return userbyPw;
        }
        else {
            return null;
        }
    }
}
