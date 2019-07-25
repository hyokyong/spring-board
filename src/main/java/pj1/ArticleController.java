package pj1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

	@Autowired ArticleMapper articleMapper;
    @Autowired UserMapper userMapper;
    @Autowired DepartmentMapper departmentMapper;
    @Autowired TypeMapper typeMapper;
    @Autowired UserService userService;

    //목록
    @RequestMapping("list.do")
    public String list(Model model, Pagination pagination) {
        pagination.setRecordCount(articleMapper.selectCount());
        pagination.setStart(pagination.getPg());
        pagination.setEnd(pagination.getSz(), pagination.getPg());
        model.addAttribute("list", articleMapper.selectPage(pagination));
        return "list";
    }
    
    @RequestMapping(value="reading.do") //method = RequestMethod.GET)
    public String reading(@RequestParam("id") int id, Pagination pagination, Model model) {
    	Article a = articleMapper.selectByAid(id);
        model.addAttribute("article", a);
        return "reading";
    }

  //  @RequestMapping(value="reading.do", method = RequestMethod.POST)
//    public String reading(User user, Pagination pagination, Model model) {
       // String message = userService.validateBeforeUpdate(user);
      //  if (message == null) {
       //     userMapper.update(user);
       //     model.addAttribute("success", "저장했습니다.");
       // } else
       //     model.addAttribute("error", message);
      //  model.addAttribute("departments", departmentMapper.selectAll());
  //      return "reading";
  //  }



}
