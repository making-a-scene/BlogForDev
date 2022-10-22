package blogProject.DevBlog.posts.likes;

import blogProject.DevBlog.posts.Posts;
import blogProject.DevBlog.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Likes {

    @Id @GeneratedValue
    private Long id;

    private int likeCounts;

    @OneToOne(mappedBy="likes")
    private Posts relatedPost;

    @OneToMany
    private List<User> likedUsers = new ArrayList<>();

    public void increaseLikes() {
        likeCounts++;
    }
    public void decreaseLikes() {
        likeCounts--;
    }

    public void setRelatedPost(Posts relatedPost){this.relatedPost = relatedPost;}

}
