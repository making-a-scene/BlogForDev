package blogProject.DevBlog.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String nickname;
    private String blogname;
    private Role role;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.blogname = user.getBlogname();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }

//    public String getName() {
//        return name;
//    }
//
//    public String getBlogname() {
//        return blogname;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public void setBlogname(String blogname) {
//        this.blogname = blogname;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
}