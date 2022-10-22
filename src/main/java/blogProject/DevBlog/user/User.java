package blogProject.DevBlog.user;

import blogProject.DevBlog.posts.Posts;
import blogProject.DevBlog.posts.dto.BaseTimeEntity;
import blogProject.DevBlog.tag.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name="UserDB")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=50)
    private String name;

    @Column(length=100, nullable = false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    private LocalDateTime createDate;

    @Column(length=30)
    private String nickname;

    @Column(length=30)
    private String blogname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "uploader")
    private List<Posts> writtenPosts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    public User(String name, String email, String password, LocalDateTime createDate, String nickname, String blogname, Role role, Provider provider, Tag tag) {

        this.name = name;
        this.password = password;
        this.email = email;
        this.createDate = LocalDateTime.now();
        this.nickname = name;
        this.role = Role.NEW;
        this.blogname = name+"님의 블로그";
        this.provider = provider;

    }

    public User update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    /* 회원 등급 변경 */
    public void updateRole(){
        if(this.role==Role.NEW){
            this.role = Role.USER;
        }
    }

    /* 블로그명 변경 */
    public User updateBlogName(String blogname){
        this.blogname=blogname;
        return this;
    }

    /* 닉네임 변경 */
    public User updateNickName(String nickname){
        this.nickname=nickname;
        return this;
    }

}