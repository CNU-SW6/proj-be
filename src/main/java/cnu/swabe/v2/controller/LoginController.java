package cnu.swabe.v2.controller;

import cnu.swabe.v2.dto.UserLoginDTO;
import cnu.swabe.v2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/v0/users/signin")
    public boolean requestLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("id = {}, pw= {}", userLoginDTO.getId(), userLoginDTO.getPw());
        return userService.login(userLoginDTO);
    }
}
