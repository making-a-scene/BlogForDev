package blogProject.DevBlog.Security;

import blogProject.DevBlog.user.User;
import blogProject.DevBlog.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username).orElseThrow(
                () -> {throw new UsernameNotFoundException("해당 아이디가 존재하지 않습니다.");}
        );
        return new CustomUserDetails(user);
    }
}
