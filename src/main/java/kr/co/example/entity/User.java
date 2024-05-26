package kr.co.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    private String userId;
    private String userPw;
    private String userName;
    private int userAge;
    private String userHp;
    private String userRole;
    private String userImg;
    @CreationTimestamp
    private LocalDate rDate;
}
