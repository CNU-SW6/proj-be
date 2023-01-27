package cnu.swabe.v0.controller;

import cnu.swabe.v0.domain.User;
import cnu.swabe.v0.dto.UserDTO;
import cnu.swabe.v0.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/v1/users/signup")
    public User requestSignUp(@RequestBody UserDTO userDTO) {
        log.info("??? id={}, pw={}, nickname={}, isMale={}", userDTO.getId(), userDTO.getPw(), userDTO.getNickname(), userDTO.isMale());
        User user = userService.addUser(userDTO);
        return user;
    }

    @ResponseBody
    @GetMapping("/v1/users/nickname/{nickname}")
    public User requestCheckDuplicateNickName(@PathVariable String nickname) {
        log.info("??? nickname={}", nickname);
        User user = userService.checkDuplicateNickName(nickname);
        return user;
    }

    @ResponseBody
    @GetMapping("/v0/users/id/{id}")
    public boolean requestCheckDuplicateId(@PathVariable String id){
        log.info("id={}", id);
        boolean isExist = userService.checkDuplicateId(id);
        return isExist;
    }
}
