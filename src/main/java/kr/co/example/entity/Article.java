package kr.co.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleNo;
    private String articleTitle;
    private String articleContent;
    private String articleCate;
    private String articleWriter;
    @CreationTimestamp
    private LocalDateTime articleDate;
}
