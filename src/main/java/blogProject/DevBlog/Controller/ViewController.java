package blogProject.DevBlog.Controller;

import blogProject.DevBlog.Security.CustomUserDetails;
import blogProject.DevBlog.Security.SocialUserDetailsService;
import blogProject.DevBlog.user.User;
import blogProject.DevBlog.user.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class ViewController {

    private final UserService userService;

    public ViewController(UserService userService, SocialUserDetailsService socialUserDetailsService) {
        this.userService = userService;
    }

    /* 기본 화면 */
    @GetMapping("/home")
    public String home (HttpServletRequest request){
        return "views/home";
    }

    /* 소셜로그인 종류 선택 - 구글 / 깃허브 */
    @GetMapping("/social")
    public String socialLoginApp(HttpServletRequest request) {
        return "views/socialLogin";
    }

}
