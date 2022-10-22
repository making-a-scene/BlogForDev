package blogProject.DevBlog.comments;

import blogProject.DevBlog.posts.Posts;
import blogProject.DevBlog.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Comments {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User uploader;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Posts relatedPost;

    @Builder
    public Comments(User uploader, String content, LocalDateTime uploadDate, Posts relatedPost) {
        this.uploader = uploader;
        this.content = content;
        this.uploadDate = uploadDate;
        this.relatedPost = relatedPost;
    }

    public void update(String content) {
        this.content = content;
    }

}