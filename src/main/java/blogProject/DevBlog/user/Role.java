package blogProject.DevBlog.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    NEW("ROLE_NEW", "회원가입을 한 회원 - 아직 닉네임과 블로그 명을 짓지 않은 회원"),
    USER("ROLE_USER", "블로그를 오픈한 회원 - 1회 이상 닉네임과 블로그 명을 지은 경험이 있는 회원");

    private final String key;
    private final String description;

}
