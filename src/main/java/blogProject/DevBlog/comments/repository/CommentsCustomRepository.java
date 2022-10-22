package blogProject.DevBlog.comments.repository;

import blogProject.DevBlog.comments.Comments;
import blogProject.DevBlog.posts.Posts;
import org.springframework.data.domain.Page;

public interface CommentsCustomRepository {
    Page<Comments> findByPostsId(Long Id);
}
