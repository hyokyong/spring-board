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
    
    @RequestMapping(value="reading.do", method = RequestMethod.GET)
    public String reading(@RequestParam("id") int id, Pagination pagination, Comment comment, Model model) {
    	Article a = articleMapper.selectByAid(id);
    	List<Comment> c = commentMapper.selectByAid(id);
        model.addAttribute("article", a);
        model.addAttribute("list", c);
        
        return "reading";
    }

    @RequestMapping(value="reading.do", method = RequestMethod.POST)
    public String reading(Comment comment, Pagination pagination, Model model) {
        commentMapper.insert(comment);
        return "reading";
    }



}
