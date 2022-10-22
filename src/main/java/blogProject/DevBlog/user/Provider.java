package blogProject.DevBlog.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {

    GOOGLE("google", "구글로그인"),
    GITHUB("github","깃허브로그인");

    private final String key;
    private final String description;

}
