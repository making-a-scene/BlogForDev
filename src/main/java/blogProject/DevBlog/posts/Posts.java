package blogProject.DevBlog.posts;

import blogProject.DevBlog.posts.dto.BaseTimeEntity;
import blogProject.DevBlog.posts.likes.Likes;
import blogProject.DevBlog.tag.PostTag;
import blogProject.DevBlog.comments.Comments;
import blogProject.DevBlog.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Data
public class Posts extends BaseTimeEntity {

    @Column(name="posts_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=500, nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable=false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User uploader;

    @Column(length=10000)
    @ElementCollection
    private List<String> fileName = new ArrayList<>();

    @Column
    @OneToMany(mappedBy = "relatedPost")
    private List<Comments> relatedComments = new ArrayList<>();

    @OneToMany(mappedBy="posts")
    private List<PostTag> includingTags = new ArrayList<>();

    @OneToOne
    @JoinColumn
    private Likes likes;

    @Builder
    public Posts(String title, String content, User uploader, List<String> fileName, List<PostTag> tagList, Likes likes) {
        this.title = title;
        this.content = content;
        this.uploader = uploader;
        this.fileName = fileName;
        this.includingTags = tagList;
        this.likes = likes;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
