package blogProject.DevBlog.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("select t from Tag t where t.includedPosts = :id")
    List<Tag> getAllTagsInPost(@Param("id") Long postId);

    @Query("select t from Tag t where t.name like %:searchKeyword%")
    Page<Tag> findTags(@Param("searchKeyword") String searchKeyword, Pageable pageable);
}

