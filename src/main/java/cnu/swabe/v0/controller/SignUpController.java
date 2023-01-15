package cnu.swabe.v0.controller;

import cnu.swabe.v0.dto.UserDTO;
import cnu.swabe.v0.repository.UserRepository;
import cnu.swabe.v0.service.UserService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/v0/users/signup")
    public String requestSignUp(@RequestBody UserDTO userDTO) {
        log.info("id={}, pw={}, nickname={}, isMale={}", userDTO.getId(), userDTO.getPw(), userDTO.getNickname(), userDTO.isMale());
        int addedUserNo = userService.addUser(userDTO);
        return String.valueOf(addedUserNo);
    }
}
