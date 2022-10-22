package blogProject.DevBlog.user.dto;

import blogProject.DevBlog.user.Provider;
import blogProject.DevBlog.user.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserJoinSocialDto {
    private String name;
    private String email;
    private String nickname;
    private Provider provider;

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .provider(provider)
                .build();

    }
}
