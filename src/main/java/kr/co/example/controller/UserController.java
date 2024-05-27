package kr.co.example.controller;


import kr.co.example.Repository.UserRepository;
import kr.co.example.dto.UserDTO;
import kr.co.example.entity.User;
import kr.co.example.security.MyUserDetails;
import kr.co.example.service.UserService;
import kr.co.example.util.CustomFileUtil;
import kr.co.example.util.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final CustomFileUtil fileUtil;

    // 토근 인증 방식 로그인
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO){

        try {
            // Security 인증 처리
            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(userDTO.getUserId(), userDTO.getUserPw());

            // 사용자 DB 조회
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 인증된 사용자 가져오기
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            // 토큰 발급(액세스, 리프레쉬)
            String access  = jwtProvider.createToken(user, 1); // 1일
            String refresh = jwtProvider.createToken(user, 7); // 7일

            // 리프레쉬 토큰 DB 저장

            // 액세스 토큰 클라이언트 전송
            Map<String, Object> map = new HashMap<>();
            map.put("grantType", "Bearer");
            map.put("username", user.getUserId());
            map.put("accessToken", access);
            map.put("refreshToken", refresh);

            return ResponseEntity.ok().body(map);

        }catch (Exception e){
            log.info("login...3 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }



    // 회원가입
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(UserDTO userDTO) {
        log.info("userDTO : " + userDTO);

        String uploadFileName = fileUtil.saveFile(userDTO.getThumbUserImg());
        userDTO.setUserImg(uploadFileName);

        return userService.registerUser(userDTO);
    }
    
    // 회원 목록
    @GetMapping("/user/list")
    public ResponseEntity<?> registerUser() {
        return userService.selectUserList();
    }
/*
    @GetMapping("/user/thumb/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        return fileUtil.getFile(fileName);
    }

 */
}
