package kr.co.strato.demosite.security.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.strato.demosite.common.model.dto.ResponseWrapper;
import kr.co.strato.demosite.security.model.dto.AuthReqModel;
import kr.co.strato.demosite.security.model.dto.JwtTokenDto;
import kr.co.strato.demosite.security.model.dto.ReqTokenDto;
import kr.co.strato.demosite.security.model.entity.Member;
import kr.co.strato.demosite.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"사용자 회원가입 및 로그인"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/join")
    public Object join(@RequestBody AuthReqModel authReqModel) {
        try {
            Member res = authService.findByMember(authReqModel.getUsername());
            ResponseWrapper<String> response = new ResponseWrapper<>();
            if(res == null) {
                authService.joinByMember(authReqModel);
                response.setBody("회원가입 성공");
            } else {
                response.setBody("이미 사용중인 회원");
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @ApiOperation(value = "중복체크")
    @GetMapping("/duple")
    public Object duple(@RequestParam("username") String username) {
        try {
            Member res = authService.findByMember(username);
            ResponseWrapper<String> response = new ResponseWrapper<>();
            if(res == null) {
                response.setBody("사용 가능한 아이디");
            } else {
                response.setBody("이미 사용중인 아이디");
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthReqModel authReqModel) {
        JwtTokenDto res = authService.loginByMember(authReqModel);
        if(res != null) {
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail");
        }
    }

    @ApiOperation(value = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody ReqTokenDto reqTokenDto) {
        JwtTokenDto res = authService.reissueByToken(reqTokenDto);
        if(res != null) {
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail");
        }
    }

    @PostMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

}
