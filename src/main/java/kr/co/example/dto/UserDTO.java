package kr.co.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String userPw;
    private String userName;
    private int userAge;
    private String userHp;
    private String userRole;
    private LocalDate rDate;
    private String userImg;
    @JsonIgnore
    private MultipartFile thumbUserImg;

}
