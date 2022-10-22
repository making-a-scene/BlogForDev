package blogProject.DevBlog.posts.likes;

import blogProject.DevBlog.posts.Posts;
import blogProject.DevBlog.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final PostsService postsService;
    private final LikesService likesService;

    @GetMapping("/users/posts/view/like")
    public String likeOrDislike(Model model, HttpServletRequest request, Long id) {
        Posts posts = postsService.findById(id).orElseThrow(
                () -> {throw new NoSuchElementException("해당 글이 존재하지 않습니다.");}
        );
        try {
            if(likesService.likeOrDislike(id, request)) {
                model.addAttribute("message", "좋아요가 완료되었습니다.");
            } else
                model.addAttribute("message", "좋아요가 취소되었습니다.");

        } catch(AuthenticationServiceException e1) {
            model.addAttribute("message", "자신의 글에는 좋아요할 수 없습니다.");

        } catch(InsufficientAuthenticationException e2) {
            model.addAttribute("message", "좋아요하기 위해서는 로그인해야 합니다.");
        }

        return "message";

    }

}
