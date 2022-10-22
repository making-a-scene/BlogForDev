package blogProject.DevBlog.tag;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag")
    private List<PostTag> includedPosts = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }

}
