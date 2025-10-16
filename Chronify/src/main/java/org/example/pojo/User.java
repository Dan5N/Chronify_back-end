package org.example.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String gender;
    private String school;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}