package blogProject.DevBlog.user;

import blogProject.DevBlog.user.dto.UserJoinFormDto;
import blogProject.DevBlog.user.dto.UserJoinSocialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getList(){
        return this.userRepository.findAll();
    }

    /* 최초 회원가입 - 일반 */
    public Long joinByForm(UserJoinFormDto userJoinFormDto) {
        User newUser = userRepository.save(userJoinFormDto.toEntity());
        return newUser.getId();
    }

    /* 최초 회원가입 - 소셜 */
    public Long joinBySocial(UserJoinSocialDto userJoinSocialDto) {
        User newUser = userRepository.save(userJoinSocialDto.toEntity());
        return newUser.getId();
    }


    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> {throw new UsernameNotFoundException("해당 회원 정보가 존재하지 않습니다.");}
        );
    }

    /* 블로그 이름 변경 */
    public void updateBlogName(String email, String newBlogName){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        user.updateBlogName(newBlogName);
        this.userRepository.save(user);
    }

    /* 닉네임 변경 */
    public void updateNickName(String email, String newNickname){
        User user = userRepository.findByName(email)
                .orElseThrow(()->new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        user.updateNickName(newNickname);
        this.userRepository.save(user);
    }

    /* 현재 회원 등급 반환 */
    @Transactional(readOnly = true)
    public String returnRole(String name){
        User user = userRepository.findByName(name)
                .orElseThrow(()->new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        return user.getRoleKey();
    }

    @Transactional(readOnly = true)
    public User returnUser(String name){
        return userRepository.findByName(name)
                .orElseThrow(()->new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
    }

    /* 탈퇴 */
    public void delete(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(()->new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        this.userRepository.delete(user);
    }
}
