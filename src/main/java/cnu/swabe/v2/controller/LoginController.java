package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.user.dto.UserLoginDTO;
import cnu.swabe.v2.response.SuccessResponse;
import cnu.swabe.v2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v2.1/users/signin")
    public SuccessResponse<UserLoginDTO.Response> requestLogin(@RequestBody UserLoginDTO.Request userLoginRequestDTO) {
        log.info("Login::: id = {}, pw= {}", userLoginRequestDTO.getId(), userLoginRequestDTO.getPw());

        UserLoginDTO.Response userLoginResponseDTO = userService.login(userLoginRequestDTO);

        return new SuccessResponse(userLoginResponseDTO);
    }
}