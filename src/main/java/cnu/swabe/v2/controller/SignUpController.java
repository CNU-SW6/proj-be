package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.user.UserEntity;
import cnu.swabe.v2.domain.user.dto.UserDTO;
import cnu.swabe.v2.response.SuccessResponse;
import cnu.swabe.v2.service.UserService;
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
        UserEntity userEntity = userService.addUser(userDTO);
        return new SuccessResponse(userEntity);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v2/users/nickname/{nickname}")
    public boolean requestCheckDuplicateNickName(@PathVariable String nickname) {
        log.info("CheckDuplicateNickName::: nickname={}", nickname);
        boolean isDuplicate = userService.checkDuplicateNickName(nickname);;
        return isDuplicate;
    }

    @GetMapping("/v0/users/id/{id}")
    public boolean requestCheckDuplicateId(@PathVariable String id){
        log.info("id={}", id);
        boolean isExist = userService.checkDuplicateId(id);
        return isExist;
    }
}
