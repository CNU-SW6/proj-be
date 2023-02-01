package cnu.swabe.v1.controller;

import cnu.swabe.v1.domain.user.User;
import cnu.swabe.v1.dto.UserDTO;
import cnu.swabe.v1.dto.UserLoginDTO;
import cnu.swabe.v1.response.SuccessResponse;
import cnu.swabe.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/users/signin")
    public SuccessResponse requestLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("id = {}, pw= {}", userLoginDTO.getId(), userLoginDTO.getPw());
        userService.login(userLoginDTO);
        return new SuccessResponse(userLoginDTO);
    }
}