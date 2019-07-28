package pj1;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

	@Autowired ArticleMapper articleMapper;
	@Autowired CommentMapper commentMapper;
    @Autowired UserMapper userMapper;
    @Autowired DepartmentMapper departmentMapper;
    @Autowired TypeMapper typeMapper;
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
        return "redirect:reading.do?aid="+aid+"&cid=0&"+pagination.getQueryString();
    }
    
    //글쓰기
    @RequestMapping(value="write.do", method=RequestMethod.GET)
    public String write(Model model, Pagination pagination) {
    	Article article = new Article();
    	model.addAttribute("article", article);
        return "write";
    }

    @RequestMapping(value="write.do", method=RequestMethod.POST)
    public String write(Model model, Pagination pagination, Article article) {
    	article.setB_id(pagination.getBd());
        article.setA_writer(UserService.getCurrentUser().getU_id());
        articleMapper.insert(article);
        return "redirect:list.do?bd=" + pagination.getBd();
    }
    
    //글수정
    @RequestMapping(value="edit.do", method = RequestMethod.GET)
    public String edit(@RequestParam("aid") int aid, Pagination pagination, Model model) {
    	Article a = articleMapper.selectByAid(aid);
        model.addAttribute("article", a);
       
        return "write";
    }

    @RequestMapping(value="edit.do", method = RequestMethod.POST)
    public String edit(@RequestParam("aid") int aid, Article article, Pagination pagination, Model model) throws UnsupportedEncodingException {
        article.setA_id(aid);
    	article.setA_writer(UserService.getCurrentUser().getU_id());
        article.setB_id(pagination.getBd());
    	articleMapper.update(article);
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
    
    




}
