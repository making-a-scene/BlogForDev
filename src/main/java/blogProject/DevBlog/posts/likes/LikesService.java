package blogProject.DevBlog.posts.likes;

import blogProject.DevBlog.Security.JwtTokenProvider;
import blogProject.DevBlog.posts.Posts;
import blogProject.DevBlog.posts.PostsService;
import blogProject.DevBlog.user.User;
import blogProject.DevBlog.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PostsService postsService;

    @Transactional
    public boolean likeOrDislike(Long postId, HttpServletRequest request) throws AuthenticationException {

        String token = getToken(request);
        Posts posts = postsService.findById(postId).orElseThrow(
                () -> {throw new NoSuchElementException("해당 글이 존재하지 않습니다.");}
        );
        Likes likes = posts.getLikes();

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            User user = getUserFromJwt(token);

            if (user.equals(posts.getUploader())) {
                
                if (likes.getLikedUsers().contains(user)) {
                    likes.decreaseLikes();
                    return false;
                }
                else {
                    likes.increaseLikes();
                    return true;
                }

            } else
                throw new AuthenticationServiceException("자신의 글에는 좋아요할 수 없습니다.");

        } else
            throw new InsufficientAuthenticationException("좋아요하기 위해서는 로그인해야 합니다.");

    }

    public String getToken(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            return authorizationHeader.substring("Bearer ".length());
        } catch (NullPointerException e) {
            System.out.println("좋아요하기 위해서는 로그인해야 합니다.");
            throw e;
        }
    }

    public User getUserFromJwt(String token) {
        String email = jwtTokenProvider.getUserPk(token);
        return userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));
    }
}
