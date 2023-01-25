package cnu.swabe.v0.controller;

import cnu.swabe.v0.domain.User;
import cnu.swabe.v0.dto.UserDTO;
import cnu.swabe.v0.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @ResponseBody
    @PostMapping("/v0/users/signin")
    public User requestLogin(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }
}
