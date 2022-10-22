package blogProject.DevBlog.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig {
    private final SocialUserDetailsService socialUserDetailsService;
    private final FormUserDetailsService formUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationConfiguration authConfig;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(formUserDetailsService);
        authProvider.setPasswordEncoder(encodePassword());

        return authProvider;
    }


    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.addFilter(new FormAuthenticationFilter(authenticationManager(authConfig)));
        http.csrf().disable();
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/**","/error", "/webjars/**","/h2-console/**" ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .formLogin()
                .loginPage("/users/login")
                .defaultSuccessUrl("/user")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/social")
                .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/social/idCheck")
                .userInfoEndpoint()
                .userService(socialUserDetailsService);

        http.headers().frameOptions().sameOrigin();
        return http.build();

    }

}
