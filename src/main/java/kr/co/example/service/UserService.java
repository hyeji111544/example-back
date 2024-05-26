package kr.co.example.service;

import kr.co.example.Repository.ArticleRepository;
import kr.co.example.Repository.UserRepository;
import kr.co.example.dto.ArticleDTO;
import kr.co.example.dto.PageRequestDTO;
import kr.co.example.dto.PageResponseDTO;
import kr.co.example.dto.UserDTO;
import kr.co.example.entity.Article;
import kr.co.example.entity.User;
import kr.co.example.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Log4j2
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CustomFileUtil fileUtil;

    // 회원 가입
    public ResponseEntity<?> registerUser(UserDTO userDTO) {
        String encoded = passwordEncoder.encode(userDTO.getUserPw());
        userDTO.setUserPw(encoded);
        userDTO.setUserRole("USER");

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);

        if (savedUser.getUserId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 회원 목록
    public ResponseEntity<?> selectUserList() {

        List<User> userList = userRepository.findAll();

        List<UserDTO> userDTOList = userList.stream()
                .map(user ->  {
                    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                    String userImgInfo = String.valueOf(fileUtil.getFile(user.getUserImg()));


                    // 정규 표현식 패턴 설정
                    String pattern = "\\[([^\\]]+)\\]";
                    Pattern r = Pattern.compile(pattern);

                    Matcher m = r.matcher(userImgInfo);
                    if (m.find()) {
                        // 파일 경로를 추출하여 변수에 저장
                        String userImg = m.group(1);
                        System.out.println(userImg); // 결과 출력
                        userDTO.setUserImg(userImg);
                    }

                    return userDTO;
                })
                .toList();

        log.info(userDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(userDTOList);
    }

}
