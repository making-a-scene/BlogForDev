package blogProject.DevBlog.comments.repository;

import blogProject.DevBlog.comments.Comments;
import blogProject.DevBlog.posts.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CommentsCustomRepositoryImpl implements CommentsCustomRepository{

    private final EntityManager em;

    @Override
    public Page<Comments> findByPostsId(Long postsId) {
        List<Comments> commentsPage =
                em.createQuery("select c from Comments c join c.relatedPost p" +
                                " where p.id = :id")
                        .setParameter("id", postsId)
                        .getResultList();



        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "id");

        if(commentsPage.isEmpty())  return new PageImpl<>(new ArrayList<>(), pageable, 0);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), commentsPage.size());
        return new PageImpl<>(commentsPage.subList(start, end), pageable, commentsPage.size());

    }

}
