package blogProject.DevBlog.comments.repository;

import blogProject.DevBlog.comments.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>, CommentsCustomRepository {

}

