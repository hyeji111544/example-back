package kr.co.example.dto;

import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private int articleNo;
    private String articleTitle;
    private String articleContent;
    private String articleCate;
    private String articleWriter;
    private LocalDateTime articleDate;
}
