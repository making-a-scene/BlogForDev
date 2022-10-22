package blogProject.DevBlog.user.dto;

import blogProject.DevBlog.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserJoinFormDto {

    private final PasswordEncoder passwordEncoder;

    private String email;
    private String password;
    private String name;
    private String nickname;

    public User toEntity() {
        return User.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .build();
    }

}
