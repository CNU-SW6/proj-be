package cnu.swabe.v1.controller;

import cnu.swabe.v1.domain.user.User;
import cnu.swabe.v1.dto.UserDTO;
import cnu.swabe.v1.response.SuccessResponse;
import cnu.swabe.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/users/signup")
    public SuccessResponse requestSignUp(@RequestBody UserDTO userDTO) {
        log.info("??? id={}, pw={}, nickname={}, isMale={}", userDTO.getId(), userDTO.getPw(), userDTO.getNickname(), userDTO.isMale());
        User user = userService.addUser(userDTO);
        return new SuccessResponse(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/users/nickname/{nickname}")
    public SuccessResponse requestCheckDuplicateNickName(@PathVariable String nickname) {
        log.info("??? nickname={}", nickname);
        userService.checkDuplicateNickName(nickname);;
        return new SuccessResponse();
    }

    @GetMapping("/v0/users/id/{id}")
    public boolean requestCheckDuplicateId(@PathVariable String id){
        log.info("id={}", id);
        boolean isExist = userService.checkDuplicateId(id);
        return isExist;
    }
}