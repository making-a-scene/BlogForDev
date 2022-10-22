package blogProject.DevBlog.Security;

import blogProject.DevBlog.user.User;
import blogProject.DevBlog.user.UserService;
import blogProject.DevBlog.user.dto.UserJoinSocialDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialUserDetailsService extends DefaultOAuth2UserService {
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        return new CustomUserDetails(user, attributes);
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        return userService.findByEmail(attributes.getEmail()).orElse(
                userService.findById(userService.joinBySocial(
                                new UserJoinSocialDto(attributes.getName(), attributes.getEmail(), attributes.getNickname(), attributes.getProvider())))
        );

    }

}