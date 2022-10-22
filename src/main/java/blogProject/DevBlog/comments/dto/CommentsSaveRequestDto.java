package blogProject.DevBlog.comments.dto;

import blogProject.DevBlog.comments.Comments;
import blogProject.DevBlog.posts.Posts;
import blogProject.DevBlog.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentsSaveRequestDto {

    private String content;
    private User uploader;
    private Posts relatedPosts;


    public Comments toEntity(Posts relatedPosts) {
        this.relatedPosts = relatedPosts;
        return new Comments(uploader, content, LocalDateTime.now(), relatedPosts);
    }
}
