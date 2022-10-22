package blogProject.DevBlog.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostsViewController {

    @GetMapping("/post/view")
    public String postView(){
        return "views/postView";
    }
}
