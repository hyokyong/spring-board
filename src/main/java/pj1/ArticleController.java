package pj1;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ArticleController {

	@Autowired ArticleMapper articleMapper;
	@Autowired CommentMapper commentMapper;
    @Autowired UserMapper userMapper;
    @Autowired DepartmentMapper departmentMapper;
    @Autowired TypeMapper typeMapper;
    @Autowired FileMapper fileMapper;
    @Autowired UserService userService;
    
    //목록
    @RequestMapping("list.do")
    public String list(Model model, Pagination pagination) {
    	pagination.setStart(pagination.getPg());
        pagination.setEnd(pagination.getSz(), pagination.getPg());
    	
    	//게시판
        if(pagination.getBd() == 1 || pagination.getBd() == 2){
    		pagination.setRecordCount(articleMapper.selectCount(pagination));
    		model.addAttribute("list", articleMapper.selectPage(pagination));
    	}
        //사원목록
    	else{
    		pagination.setRecordCount(userMapper.selectCount(pagination));
    		model.addAttribute("list", userMapper.selectPage(pagination));
    	}
        return "list";
    }
    
    //글읽기 + 댓글쓰기 + 댓글수정
    @RequestMapping(value="reading.do", method = RequestMethod.GET)
    public String reading(@RequestParam("aid") int aid, @RequestParam("cid") int cid, Pagination pagination, Model model) {
    	Article a = articleMapper.selectByAid(aid);
    	List<Comment> c = commentMapper.selectByAid(aid);
    	String u_id = UserService.getCurrentUser().getU_id();
    	
    	//댓글수정
    	if(cid != 0){
    		Comment comment = commentMapper.selectByCid(cid);
    		model.addAttribute("c", comment);
    	}
    	else{
    		if(UserService.getCurrentUser().getU_id() != a.getA_writer()){
        		articleMapper.updateClick(aid);
        	}
    		Comment comment = new Comment();
    		model.addAttribute("c", comment);
    	}
        model.addAttribute("article", a);
        model.addAttribute("list", c);
        model.addAttribute("u_id", u_id);
        
        //파일보이기
        File f = fileMapper.selectByAid(aid);
        if(f == null){
        	File file = new File();
        	model.addAttribute("file", file);
        }
        else
        	model.addAttribute("file", f);
  
        return "reading";
    }

    //댓글 등록 + 댓글 수정등록
    @RequestMapping(value="reading.do", method = RequestMethod.POST)
    public String reading(@RequestParam("aid") int aid, @RequestParam("cid") int cid, Comment comment, Pagination pagination, Model model) throws UnsupportedEncodingException {
    	comment.setA_id(aid);
        comment.setC_writer(UserService.getCurrentUser().getU_id());
        //댓글등록
        if(cid == 0)
        	commentMapper.insert(comment);
        //댓글수정
        else{
        	comment.setC_id(cid);
        	commentMapper.update(comment);
        }
        model.addAttribute("files", fileMapper.selectByAid(aid));
        return "redirect:reading.do?aid="+aid+"&cid=0&"+pagination.getQueryString();
    }
    
    //글쓰기
    @RequestMapping(value="write.do", method=RequestMethod.GET)
    public String write(Model model, Pagination pagination) {
    	Article article = new Article();
    	File file = new File();
    	model.addAttribute("article", article);
    	model.addAttribute("file", file);
        return "write";
    }

    @RequestMapping(value="write.do", method=RequestMethod.POST)
    public String write(Model model, Pagination pagination, Article article, @RequestParam("file") MultipartFile uploadedFile) throws IOException {
    	article.setB_id(pagination.getBd());
        article.setA_writer(UserService.getCurrentUser().getU_id());
        articleMapper.insert(article);
        
        //파일업로드
        if (uploadedFile.getSize() > 0 ) {
            File file = new File();
            file.setA_id(article.getA_id());
            file.setU_id(UserService.getCurrentUser().getU_id());
            file.setF_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
            file.setF_size((int)uploadedFile.getSize());
            file.setData(uploadedFile.getBytes());
            fileMapper.insert(file);
        }
        return "redirect:list.do?bd=" + pagination.getBd();
    }
    
    //글수정
    @RequestMapping(value="edit.do", method = RequestMethod.GET)
    public String edit(@RequestParam("aid") int aid, @RequestParam("fid") int fid, Pagination pagination, Model model) {
    	Article a = articleMapper.selectByAid(aid);
        model.addAttribute("article", a);
        
        //글수정버튼 눌렀을때
        if(fid == 0){
        	File f = fileMapper.selectByAid(aid);
        	if(f == null){
        		File file = new File();
        		model.addAttribute("file", file);
        	}
        	else
        		model.addAttribute("file", fileMapper.selectByAid(aid));	
        }
        //업로드파일삭제
        else{
        	fileMapper.delete(fid);
        	File file = new File();
        	model.addAttribute("file", file);
        }
        
        return "write";
    }

    @RequestMapping(value="edit.do", method = RequestMethod.POST)
    public String edit(@RequestParam("aid") int aid, Article article, Pagination pagination, Model model, @RequestParam("file") MultipartFile uploadedFile) throws IOException {
        article.setA_id(aid);
    	article.setA_writer(UserService.getCurrentUser().getU_id());
        article.setB_id(pagination.getBd());
    	articleMapper.update(article);
    	
    	//파일수정업로드
    	 if (uploadedFile.getSize() > 0 ) {
             File file = new File();
             file.setA_id(article.getA_id());
             file.setU_id(UserService.getCurrentUser().getU_id());
             file.setF_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
             file.setF_size((int)uploadedFile.getSize());
             file.setData(uploadedFile.getBytes());
             fileMapper.insert(file);
         }
        return "redirect:reading.do?aid="+aid+"&cid=0&"+pagination.getQueryString();
    }
  
    //글삭제, 댓글삭제
    @RequestMapping(value="delete.do")
    public String delete(@RequestParam("aid") int aid, @RequestParam("cid") int cid, Pagination pagination, Model model) throws UnsupportedEncodingException {
    	
    	//글삭제
    	if(cid == 0){
    		articleMapper.delete(aid);
    		commentMapper.deleteAll(aid);
    		return "redirect:list.do?" + pagination.getQueryString();
    	}
    	//댓글삭제
    	else{
    		commentMapper.delete(cid);
    		return "redirect:reading.do?aid="+aid+"&cid=0&"+pagination.getQueryString();
    	}
    	 
    }
    
    //파일 다운로드
    @RequestMapping("download.do")
    public void download(@RequestParam("fid") int fid, HttpServletResponse response) throws IOException {
        File file = fileMapper.selectByFid(fid);
        if (file == null) return;
        String fileName = URLEncoder.encode(file.getF_name(),"UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
        try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
            output.write(file.getData());
        }
    }


    
    




}
