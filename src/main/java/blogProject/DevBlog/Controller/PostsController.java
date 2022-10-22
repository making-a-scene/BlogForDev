package blogProject.DevBlog.Controller;

import blogProject.DevBlog.Security.CustomUserDetails;
import blogProject.DevBlog.comments.Comments;
import blogProject.DevBlog.comments.CommentsService;
import blogProject.DevBlog.posts.Posts;
import blogProject.DevBlog.posts.PostsService;
import blogProject.DevBlog.posts.dto.PostsSaveRequestDto;
import blogProject.DevBlog.posts.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService postsService;
    private final CommentsService commentsService;

    // 글 작성 페이지
    @GetMapping("/user/posts/new")
    public String newPosts() {
        return "newposts";
    }

    // 작성한 글 저장
    @PostMapping("/user/posts/save")
    public String savePosts(PostsSaveRequestDto postsSaveRequestDto, Model model) {

        postsService.savePosts(postsSaveRequestDto);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/posts/list");

        return "message";
    }

    // 글 삭제
    @GetMapping("/posts/delete")
    public String deletePosts(@AuthenticationPrincipal CustomUserDetails principal, Long id, Model model) {

        try{
            postsService.deletePosts(id, principal);
            model.addAttribute("message", "글 삭제가 완료되었습니다.");
        } catch(IllegalAccessException e) {
            model.addAttribute("message", "글 작성자만 삭제할 수 있습니다.");
        }
        model.addAttribute("searchUrl", "/posts/list");

        return "message";
    }

    // 파라미터로 주어진 태그가 붙어있는 모든 글 리스트 보여주기
    @GetMapping("/posts/list/tag") // localhost:8080/posts/list?tagId=1
    public String postsListIncludingTag(@RequestParam(name="tagId", required = false) Long tagId,
                                        @PageableDefault(size=10, sort="id", direction=Sort.Direction.DESC) Pageable pageable,
                                        Model model) {

        Page<Posts> list = postsService.viewPostsIncludingTag(tagId, pageable);

        int currPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(currPage - 4, 1);
        int endPage = Math.min(currPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("currPage", currPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "postslist";

    }


    // 작성한 글 리스트 보여주기
    @GetMapping("/posts/list")
    public String postsList(Model model,
                            @PageableDefault(size=10, sort="id", direction= Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(name="searchType", required = false, defaultValue="na") String searchType,
                            @RequestParam(name="searchKeyword", required = false, defaultValue="na") String searchKeyword)

                            throws Exception {

            Page<Posts> list;

            if(Objects.equals(searchKeyword, "na")) {
                list = postsService.postsList(pageable);
            }
            else if(Objects.equals(searchType, "title")) {
                list = postsService.searchedPostsByTitleList(searchKeyword, pageable);
            }
            else if(Objects.equals(searchType, "content")) {
                list = postsService.searchedPostsByContentList(searchKeyword, pageable);
            }
            else if(Objects.equals(searchType, "all")) {
                list = postsService.searchedPostsByAllList(searchKeyword, pageable);
            }
            else {
                throw new Exception();
            }

            if(list.isEmpty()) {
                model.addAttribute("searchUrl", "/posts/list");
                model.addAttribute("message", "일치하는 검색 결과가 없습니다.");
                return "message";
            }

            int currPage = list.getPageable().getPageNumber() + 1;
            int startPage = Math.max(currPage - 4, 1);
            int endPage = Math.min(currPage + 5, list.getTotalPages());

            model.addAttribute("list", list);
            model.addAttribute("currPage", currPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "postslist";
    }

    // 글 상세 페이지
    @GetMapping("/posts/view")  // localhost:8080/posts/view?id=1
    public String postsView(Model model, Long id) {

        model.addAttribute("posts", postsService.viewPosts(id));
        model.addAttribute("tags", postsService.viewPosts(id).getIncludingTags());
        model.addAttribute("likes", postsService.viewPosts(id).getLikes().getLikeCounts());

        Page<Comments> commentsList = commentsService.findByRelatedPost(id);

        if (commentsList.getNumberOfElements() == 0)  model.addAttribute("isExist",false);
        else {
            model.addAttribute("isExist",true);
            int currPage = commentsList.getPageable().getPageNumber();
            int startPage = Math.max(currPage - 4, 1);
            int endPage = Math.min(currPage + 5, commentsList.getTotalPages());

            model.addAttribute("comments", commentsList);
            model.addAttribute("nowPage", currPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

        }

        return "postsview";
    }

    // 글 수정 페이지
    @GetMapping("/user/posts/edit/{id}")
    public String postsEdit(@AuthenticationPrincipal CustomUserDetails principal, @PathVariable("id") Long id, Model model) {
        Posts posts = postsService.findById(id).orElseThrow(
                () -> {throw new NoSuchElementException("해당 글이 존재하지 않습니다.");}
        );

        if(posts.getUploader().getEmail().equals(principal.getUsername())) {
            model.addAttribute("posts", postsService.viewPosts(id));

            return "postsedit";
        } else {
            model.addAttribute("message", "글 작성자만 수정 가능합니다.");
            return "redirect:/posts/view/?id="+id;
        }


    }

    // 수정한 글 저장
    @PostMapping("/user/posts/saveedit/{id}")
    public String updatePosts(@PathVariable("id") Long id, PostsUpdateRequestDto postsUpdateRequestDto, Model model) {

        postsService.updatePosts(id, postsUpdateRequestDto);
        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/posts/list");

        return "message";
    }

    // 이미지 업로드 API
    @PostMapping("/posts/addImage")
    public ResponseEntity<Object> addImage(@RequestBody MultipartFile[] uploadFile, HttpServletRequest request){

        System.out.println("이미지 업로드 시작");
        String uploadFolder = request.getSession().getServletContext().getRealPath("/");

        System.out.println(uploadFolder);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        String datePath = str.replace("-", File.separator);

        File uploadPath = new File(uploadFolder);
        if(uploadPath.exists() == false) {
            uploadPath.mkdirs();
        }

        for(MultipartFile file: uploadFile){

            String uploadFileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            uploadFileName = uuid + "_" + uploadFileName;

            File saveFile = new File(uploadPath, uploadFileName);

            try {
                file.transferTo(saveFile);
            } catch (Exception e) {
                e.printStackTrace();
                ResponseEntity<Object> no = new ResponseEntity<>("업로드 불가", HttpStatus.BAD_REQUEST);
                return no;
            }
        }

        ResponseEntity<Object> ok = new ResponseEntity<>("업로드 성공", HttpStatus.OK);
        return ok;
    }

    // 이미지 보여주기 API
    @GetMapping(value="/display", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] display(@Param("filename") String filename, HttpServletRequest request){
        String uploadFolder = request.getSession().getServletContext().getRealPath("/");
        String path = uploadFolder+filename;

        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try{
            fileInputStream = new FileInputStream(path);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        int readCount = 0;
        byte[] buffer = new byte[1024];
        byte[] fileArray = null;

        try{
            while((readCount = fileInputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer,0,readCount);
            }
            fileArray = byteArrayOutputStream.toByteArray();
            fileInputStream.close();
            byteArrayOutputStream.close();
        }catch (IOException e){
            throw new RuntimeException("File Error");
        }

        return fileArray;
    }

    // 이미지 삭제 API
    @GetMapping(value="/posts/deleteImage/{name}")
    public ResponseEntity<Object> deleteImage(@PathVariable("name") String filename, HttpServletRequest request){

        ResponseEntity<Object> ok = new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        ResponseEntity<Object> no = new ResponseEntity<>("삭제 실패", HttpStatus.BAD_REQUEST);


        String uploadFolder = request.getSession().getServletContext().getRealPath("/");
        String path = uploadFolder+filename;

        File file = new File(path);

        if(file.exists()){
            try {
                file.delete();
            }catch (Exception e){
                e.printStackTrace();
                return no;
            }
        }
        return ok;
    }



//    @PutMapping("/posts/update/{id}")
//    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
//        return postsService.update(id, requestDto);
//    }
//
//    @DeleteMapping("/posts/delete/{id}")
//    public Long delete(@PathVariable Long id) {
//        postsService.delete(id);
//
//        return id;
//    }
//
//    @GetMapping("/posts/{id}")
//    public PostsResponseDto findById(@PathVariable Long id) {
//        return postsService.findById(id);
//    }

    //     글 저장 API
//    @PostMapping("/posts")
//    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
//        return postsService.save(requestDto);
//    }

}
