package blogProject.DevBlog.Controller;

import blogProject.DevBlog.Security.CustomUserDetails;
import blogProject.DevBlog.comments.Comments;
import blogProject.DevBlog.comments.CommentsService;
import blogProject.DevBlog.comments.dto.CommentsSaveRequestDto;
import blogProject.DevBlog.comments.dto.CommentsUpdateRequestDto;
import blogProject.DevBlog.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class CommentsController {

    private final PostsService postsService;
    private final CommentsService commentsService;

    // 작성한 댓글 저장
    @RequestMapping(value="/users/comments/save/{id}", consumes = "application/x-www-form-urlencoded")
    public String newComments(Model model, CommentsSaveRequestDto requestDto, @PathVariable("id") Long id) {

        commentsService.saveComments(requestDto.toEntity(postsService.viewPosts(id)));
        model.addAttribute("message", "댓글 작성이 완료되었습니다.");
        String redirectUrl = "/posts/view?id="+id;
        model.addAttribute("searchUrl", redirectUrl);
        return "message";
    }

    @GetMapping("/users/comments/updateRequest")
    public String getUpdateComments(Model model, HttpServletRequest request, @RequestParam("id") Long id, @AuthenticationPrincipal CustomUserDetails principal) {
        Comments comments = commentsService.findById(id)
                .orElseThrow(() -> {throw new NoSuchElementException("해당 댓글이 존재하지 않습니다.");});

        if(comments.getUploader().getEmail().equals(principal.getUsername())) {
            return "commentsUpdateForm";
        } else {
            model.addAttribute("message", "댓글 작성자만 수정할 수 있습니다.");
            return "redirect:" + request.getHeader("Referer");
        }
    }

    // 댓글 수정
    @RequestMapping("/users/comments/update/{id}")
    public String updateComments(Model model, @PathVariable("id") Long id, @RequestBody CommentsUpdateRequestDto commentsUpdateRequestDto) {

        commentsService.updateComments(id, commentsUpdateRequestDto);
        model.addAttribute("message", "댓글 수정이 완료되었습니다.");
        return "message";

    }

    // 댓글 삭제
    @GetMapping("/comments/delete/{id}")
    public String deleteComments(Model model, @AuthenticationPrincipal CustomUserDetails principal, @PathVariable("id") Long id) {

        try {
            commentsService.deleteComments(id, principal);
            model.addAttribute("message", "댓글 삭제가 완료되었습니다.");

        } catch(IllegalAccessException e) {
            model.addAttribute("message", "댓글 작성자만 삭제할 수 있습니다.");
        }
        String redirectUrl = "/posts/view?id="+commentsService.findPostsByRelatedComments(id).getId();
        model.addAttribute("searchUrl", redirectUrl);

        return "message";

    }
}
