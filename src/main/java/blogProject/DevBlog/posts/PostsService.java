package blogProject.DevBlog.posts;

import blogProject.DevBlog.Security.CustomUserDetails;
import blogProject.DevBlog.posts.dto.PostsSaveRequestDto;
import blogProject.DevBlog.posts.dto.PostsUpdateRequestDto;
import blogProject.DevBlog.user.User;
import blogProject.DevBlog.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserService userService;

    public Optional<Posts> findById(Long id) throws UsernameNotFoundException {
        return postsRepository.findById(id);
    }

    // 게시글 저장
    @Transactional
    public void savePosts(PostsSaveRequestDto postsSaveRequestDto) {
        postsRepository.save(postsSaveRequestDto.toEntity());
    }

    // 게시글 삭제
    @Transactional
    public void deletePosts(Long id, CustomUserDetails principal) throws IllegalAccessException {
        Posts post = findById(id).orElseThrow(
                () -> {throw new UsernameNotFoundException("해당 글이 존재하지 않습니다.");}
        );
        if(post.getUploader().getEmail().equals(principal.getUsername())) {
            postsRepository.deleteById(id);
        } else {
            throw new IllegalAccessException("글 작성자만 삭제할 수 있습니다.");
        }

    }

    // 게시글 수정
    @Transactional
    public void updatePosts(Long id, PostsUpdateRequestDto postsUpdateRequestDto) {
        Posts posts = findById(id).orElseThrow(
                () -> {throw new NoSuchElementException("해당 글이 존재하지 않습니다.");}
        );
        posts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());
    }



    // 모든 게시글 가져오기
    public Page<Posts> postsList(Pageable pageable) {

        return postsRepository.findAll(pageable);
    }


    public Page<Posts> searchedPostsByTitleList(String searchKeyword, Pageable pageable) {

        return postsRepository.findByTitleContaining(searchKeyword, pageable);
    }

    public Page<Posts> searchedPostsByContentList(String searchKeyword, Pageable pageable) {
        return postsRepository.findByContentContaining(searchKeyword, pageable);
    }

    public Page<Posts> searchedPostsByAllList(String searchKeyword, Pageable pageable) {
        return postsRepository.findByAllContaining(searchKeyword, pageable);
    }

    // 특정 게시글 가져오기
    public Posts viewPosts(Long id) {

        return postsRepository.findById(id).get();
    }

    public Page<Posts> viewPostsIncludingTag(Long tagId, Pageable pageable) {
        return postsRepository.findPostsByTag(tagId, pageable);
    }

    // 글에 좋아요 표시, 좋아요 취소
    // 유저가 해당 글에 좋아요를 표시했는지, 안 했는지 확인하는 절차 필요.

}