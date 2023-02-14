package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.user.dto.UserSignUpDTO;
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

    @PostMapping("/v2.1/users/signup")
    public SuccessResponse<UserSignUpDTO.Response> requestSignUp(@RequestBody UserSignUpDTO.Request userSignUpRequestDTO) {
        log.info("SignUp::: id={}, pw={}, nickname={}, isMale={}",
                userSignUpRequestDTO.getId(),
                userSignUpRequestDTO.getPw(),
                userSignUpRequestDTO.getNickname(),
                userSignUpRequestDTO.isMale()
        );
        UserSignUpDTO.Response userSignUpResponseDTO = userService.register(userSignUpRequestDTO);

        return new SuccessResponse(userSignUpResponseDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v2.1/users/nickname/{nickname}")
    public boolean requestCheckDuplicateNickName(@PathVariable String nickname) {
        log.info("CheckDuplicatedNickName::: nickname={}", nickname);
        boolean isDuplicate = userService.checkDuplicateNickName(nickname);;
        return isDuplicate;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v2.1/users/id/{id}")
    public boolean requestCheckDuplicateId(@PathVariable String id){
        log.info("CheckDuplicatedId::: id={}", id);
        boolean isDuplicate = userService.checkDuplicateId(id);
        return isDuplicate;
    }
}
