package blogProject.DevBlog.posts.dto;

import blogProject.DevBlog.posts.Posts;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private List<String> fileName = new ArrayList<String>();
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getUploader().getNickname();
        this.fileName=entity.getFileName();
    }
}
