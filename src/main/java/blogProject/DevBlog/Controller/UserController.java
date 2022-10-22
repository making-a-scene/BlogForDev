package blogProject.DevBlog.Controller;

import blogProject.DevBlog.Security.CustomUserDetails;
import blogProject.DevBlog.user.User;

import blogProject.DevBlog.user.UserService;
import blogProject.DevBlog.user.dto.UserJoinFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /* 회원가입 */
    @PostMapping("/user/join")
    public Long join(@RequestBody UserJoinFormDto userJoinFormDto) {
        return userService.joinByForm(userJoinFormDto);

    }

    /* 신규 회원을 위한 페이지 - 최초로 닉네임과 블로그명 작성 */
    @GetMapping("/user/newAccount")
    public String newAccount(@AuthenticationPrincipal CustomUserDetails principal, Model model, HttpServletRequest request){
        userService.updateBlogName(request.getParameter("email"), request.getParameter("blogname"));
        userService.updateNickName(request.getParameter("email"), request.getParameter("nickname"));

        principal.getUser().updateRole();
        model.addAttribute("name", principal.getAttribute("name"));
        return "/user/home";
    }


    /* 회원 탈퇴 - 디비에서 삭제 */
    @RequestMapping(value = "/user/delete/{name}", method= RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMember (@AuthenticationPrincipal CustomUserDetails principal, HttpSession session) {
        String email = principal.getUsername();
        try{
            this.userService.delete(email);

            Object obj = session.getAttribute("login");
            if(obj!=null){
                session.removeAttribute("login");
                session.invalidate();
                SecurityContextHolder.clearContext();
            }
            ResponseEntity<Object> ok = new ResponseEntity<>("회원 탈퇴 완료",HttpStatus.OK);
            System.out.println(ok);
            return ok;
        }catch (Exception ex){
            ResponseEntity<Object> no = new ResponseEntity<>(ex,HttpStatus.BAD_REQUEST);
            System.out.println(no);
            return no;
        }
    }
    /* 아이디 찾기 */
    @RequestMapping(value="/findId/{name}",method=RequestMethod.GET)
    public Map<String,Object> findId(@PathVariable("name") String name){
        Map<String,Object> map = new HashMap<String,Object>();
        User user = userService.returnUser(name);

        map.put("email",user.getEmail());
        return map;
    }

    /* 블로그 이름 변경 */
    @RequestMapping(value = "/user/changeBlogName", method = RequestMethod.PUT)
    public Map<String,Object> changeBlogName(@AuthenticationPrincipal CustomUserDetails principal, @RequestBody String blogname){
        Map<String,Object> map = new HashMap<>();

        String email = principal.getUsername();
        map.put("blogname", blogname);
        userService.updateBlogName(email,blogname);

        return map;
    }

    /* 닉네임 변경 */
    @RequestMapping(value = "/user/changeNickName", method=RequestMethod.PUT)
    public Map<String,Object> changeNickName(@AuthenticationPrincipal CustomUserDetails principal, @RequestBody String nickname){

        Map<String,Object> map = new HashMap<>();
        String email = principal.getUsername();
        map.put("nickname",nickname);
        userService.updateNickName(email, nickname);
        return map;
    }


    @RequestMapping(value="/user/{name}/role",method = RequestMethod.GET)
    public Map<String,Object> returnRole (@PathVariable("name") String name){
        Map<String,Object> map = new HashMap<String,Object>();
        String role = userService.returnRole(name);

        map.put(name,role);
        return map;
    }


    @RequestMapping(value="user/{name}/info",method=RequestMethod.GET)
    public Map<String,Object> returnUserInfo(@PathVariable("name") String name){
        Map<String,Object> map = new HashMap<String,Object>();
        User user = userService.returnUser(name);

        map.put("name",user.getName());
        map.put("email",user.getEmail());
        map.put("nickname",user.getNickname());
        map.put("blogname",user.getBlogname());
        map.put("role",user.getRole());

        return map;
    }
}