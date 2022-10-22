package blogProject.DevBlog.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // paging method //
    public Model pagingConfig(Page<Tag> pagedTags, Model model) {

        int currPage = pagedTags.getPageable().getPageNumber();
        int startPage = Math.max(currPage - 4, 1);
        int endPage = Math.min(currPage + 5, pagedTags.getTotalPages());

        model.addAttribute("comments", pagedTags);
        model.addAttribute("nowPage", currPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return model;
    }

    // 서버 내 전체 태그 목록 리스트
    @GetMapping("/tags/list")
    public String showTagList(Model model,
                              @PageableDefault(size=10, sort="id", direction= Sort.Direction.DESC) Pageable pageable) {

        Page<Tag> pagedTags = tagService.showAllTags(pageable);
        pagingConfig(pagedTags, model);

        return "tagList";
    }

    // 태그 이름으로 검색
    @GetMapping("/tag/search/{searchKeyword}")
    public String searchTag(@PathVariable("searchKeyword") String searchKeyword,
                            Model model,
                            @PageableDefault(size=10, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {

        Page<Tag> searchedTagPages = tagService.findBySearchKeyword(searchKeyword, pageable);
        pagingConfig(searchedTagPages, model);

        return "tagList";

    }

    @GetMapping("/tags/new")
    public String registerTagForm() {
        return "newTag.html";
    }

    // 서버에 새로운 태그 추가
    @GetMapping("/tags/new/register")
    public String registerTag(@RequestParam String name, Model model) {

        List<Tag> tagList = tagService.findAll();
        List<String> tagNameList = tagList.stream().map(Tag::getName).toList();
        if(tagNameList.contains(name))  model.addAttribute("message", "이미 존재하는 태그입니다.");
        else {
            tagService.registerTag(new Tag(name));
            model.addAttribute("message", "태그가 정상적으로 등록되었습니다.");
        }

        return "tagList";

    }

}
