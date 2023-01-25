package cnu.swabe.v0.controller;

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
    @PostMapping("/v0/users/signup")
    public String requestSignUp(@RequestBody UserDTO userDTO) {
        log.info("id={}, pw={}, nickname={}, isMale={}", userDTO.getId(), userDTO.getPw(), userDTO.getNickname(), userDTO.isMale());
        int addedUserNo = userService.addUser(userDTO);
        return String.valueOf(addedUserNo);
    }

    @ResponseBody
    @GetMapping("/v0/users/nickname/{nickname}")
    public boolean requestCheckDuplicateNickName(@PathVariable String nickname) {
        log.info("nickname={}", nickname);
        boolean isExist = userService.checkDuplicateNickName(nickname);
        return isExist;
    }

    @ResponseBody
    @GetMapping("/v0/users/id/{id}")
    public boolean requestCheckDuplicateId(@PathVariable String id){
        log.info("id={}", id);
        boolean isExist = userService.checkDuplicateId(id);
        return isExist;
    }
}
