package blogProject.DevBlog.comments;

import blogProject.DevBlog.Security.CustomUserDetails;
import blogProject.DevBlog.comments.dto.CommentsUpdateRequestDto;
import blogProject.DevBlog.comments.repository.CommentsRepository;
import blogProject.DevBlog.posts.Posts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsService {

    private final CommentsRepository commentsRepository;

    @Transactional
    public void saveComments(Comments comments) {

        commentsRepository.save(comments);
    }

    public void updateComments(Long id, CommentsUpdateRequestDto commentsUpdateRequestDto) {
        Comments comments = findById(id).orElseThrow(
                () -> {throw new NoSuchElementException("해당 댓글이 존재하지 않습니다.");}
        );
        comments.update(commentsUpdateRequestDto.getContent());
        commentsRepository.save(comments);
    }

    @Transactional
    public void deleteComments(Long id, CustomUserDetails principal) throws IllegalAccessException {
        Comments comments = findById(id).orElseThrow(
                () -> {throw new NoSuchElementException("해당 댓글이 존재하지 않습니다.");}
        );
        if(comments.getUploader().getEmail().equals(principal.getUsername())) {
            commentsRepository.deleteById(id);
        } else {
            throw new IllegalAccessException("댓글 작성자만 삭제할 수 있습니다.");
        }
    }

    public Optional<Comments> findById(Long id) {
        return commentsRepository.findById(id);
    }

    public Posts findPostsByRelatedComments(Long id) {
        return commentsRepository.findById(id).get().getRelatedPost();
    }
    public Page<Comments> findByRelatedPost(Long postId) {
        return commentsRepository.findByPostsId(postId);
    }


}
