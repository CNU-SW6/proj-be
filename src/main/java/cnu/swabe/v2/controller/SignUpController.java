package cnu.swabe.v2.controller;

import cnu.swabe.v2.domain.user.UserEntity;
import cnu.swabe.v2.domain.user.dto.UserRequestDTO;
import cnu.swabe.v2.domain.user.dto.UserResponseDTO;
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
    public SuccessResponse<UserResponseDTO> requestSignUp(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("SignUp::: id={}, pw={}, nickname={}, isMale={}",
                userRequestDTO.getId(),
                userRequestDTO.getPw(),
                userRequestDTO.getNickname(),
                userRequestDTO.isMale()
        );
        UserEntity user = userService.register(userRequestDTO);
        UserResponseDTO userResponse = new UserResponseDTO(
                user.getId(),
                user.getNickname(),
                user.isMale()
        );

        return new SuccessResponse(userResponse);
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
