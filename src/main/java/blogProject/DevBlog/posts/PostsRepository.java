package blogProject.DevBlog.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("select p from Posts p where p.title like %:searchKeyword%")
    Page<Posts> findByTitleContaining(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("select p from Posts p where p.content like %:searchKeyword%")
    Page<Posts> findByContentContaining(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("select p from Posts p where p.title like %:searchKeyword% or p.content like %:searchKeyword%")
    Page<Posts> findByAllContaining(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("select p from Posts p where p.includingTags = :tag")
    Page<Posts> findPostsByTag(@Param("tag") Long tagId, Pageable pageable);

}