package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.user.dto.UserSignUpRequestDTO;
import cnu.swabe.v2.domain.user.dto.UserSignUpResponseDTO;
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
    @PostMapping("/v2/users/signup")
    public SuccessResponse<UserSignUpResponseDTO> requestSignUp(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        log.info("SignUp::: id={}, pw={}, nickname={}, isMale={}",
                userSignUpRequestDTO.getId(),
                userSignUpRequestDTO.getPw(),
                userSignUpRequestDTO.getNickname(),
                userSignUpRequestDTO.isMale()
        );
        UserSignUpResponseDTO userSignUpResponse = userService.register(userSignUpRequestDTO);

        return new SuccessResponse(userSignUpResponse);
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
