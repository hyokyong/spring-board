package pj1;

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
    	
    	if(pagination.getBd() == 1 || pagination.getBd() == 2){
    		pagination.setRecordCount(articleMapper.selectCount(pagination.getBd()));
    		model.addAttribute("list", articleMapper.selectPage(pagination));
    	}
    	else{
    		pagination.setRecordCount(userMapper.selectCount());
    		model.addAttribute("list", userMapper.selectPage(pagination));
    	}
        return "list";
    }
    
    //글읽기 + 댓글쓰기
    @RequestMapping(value="reading.do", method = RequestMethod.GET)
    public String reading(@RequestParam("id") int id, Pagination pagination, Model model) {
    	Article a = articleMapper.selectByAid(id);
    	List<Comment> c = commentMapper.selectByAid(id);
    	Comment comment = new Comment();
    	if(UserService.getCurrentUser().getU_id() != a.getA_writer()){
    		articleMapper.updateClick(id);
    	}
        model.addAttribute("article", a);
        model.addAttribute("list", c);
        model.addAttribute("comment", comment);
        
        return "reading";
    }

    @RequestMapping(value="reading.do", method = RequestMethod.POST)
    public String reading(@RequestParam("aid") String aid, Comment comment, Pagination pagination, Model model) {
        comment.setA_id(aid);
        comment.setC_writer(UserService.getCurrentUser().getU_id());
    	commentMapper.insert(comment);
        return "redirect:reading.do?id="+aid+"&"+pagination.getQueryString();
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




}
