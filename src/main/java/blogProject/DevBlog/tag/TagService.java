package blogProject.DevBlog.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    // 서버에 존재하는 모든 태그 리턴 - Page
    public Page<Tag> showAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    // 서버에 존재하는 모든 태그 리턴 - List
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    // 검색어가 포함된 태그 검색
    public Page<Tag> findBySearchKeyword(String searchKeyword, Pageable pageable) {
        return tagRepository.findTags(searchKeyword, pageable);
    }

    // 서버에 새로운 태그 등록
    @Transactional
    public void registerTag(Tag tag) {
       if(tagRepository.findById(tag.getId()).isEmpty()) {
           tagRepository.save(tag);
           return;
       }

       throw new IllegalStateException("이미 존재하는 태그입니다.");
    }

}
