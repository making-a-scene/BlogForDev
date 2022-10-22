package blogProject.DevBlog.posts.dto;

import blogProject.DevBlog.posts.Posts;
import blogProject.DevBlog.posts.likes.Likes;
import blogProject.DevBlog.tag.PostTag;
import blogProject.DevBlog.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private User uploader;
    private ArrayList<String> fileName = new ArrayList<>();
    private List<PostTag> tagList = new ArrayList<>();


    public Posts toEntity() {

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .uploader(uploader)
                .fileName(fileName)
                .tagList(tagList)
                .likes(Likes.builder().likeCounts(0).build())
                .build();
        posts.getLikes().setRelatedPost(posts);
        return posts;
    }
}
