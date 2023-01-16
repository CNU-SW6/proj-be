package cnu.swabe.v0.service;

import cnu.swabe.v0.dto.UserDTO;
import cnu.swabe.v0.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    @Transactional
    public int addUser(UserDTO userDTO) {
        int addedUserNo = userRepository.save(userDTO);
        return addedUserNo;
    }
}
