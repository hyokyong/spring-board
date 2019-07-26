package pj1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired UserMapper userMapper;
    @Autowired DepartmentMapper departmentMapper;
    @Autowired TypeMapper typeMapper;

    @Autowired UserService userService;

    //회원가입
    @RequestMapping(value="join.do", method = RequestMethod.GET)
    public String join(User user, Model model) {
    	model.addAttribute("departments", departmentMapper.selectAll());
    	model.addAttribute("types", typeMapper.selectAll());
    	return "join";
    }

    @RequestMapping(value="join.do", method = RequestMethod.POST)
    public String join2(User user, Model model) {
        
    	String message = userService.validate(user);
        if (message == null) {
        	//비밀번호 암호화
            userMapper.insert(user);
            model.addAttribute("success", "저장했습니다.");
        } else
            model.addAttribute("error", message);
        
        model.addAttribute("departments", departmentMapper.selectAll());
    	model.addAttribute("types", typeMapper.selectAll());
    	
        return "join";
    }
    
    //마이페이지
    @RequestMapping(value="mypage.do", method = RequestMethod.GET)
    public String mypage(@RequestParam("id") int id, Model model) {
        User user = userMapper.selectById(id);
        model.addAttribute("user", user);
        model.addAttribute("departments", departmentMapper.selectAll());
    	model.addAttribute("types", typeMapper.selectAll());
        return "edit";
    }

    @RequestMapping(value="mypage.do", method = RequestMethod.POST)
    public String mypage(User user, Model model) {
        String message = userService.validate(user);
        if (message == null) {
            userMapper.update(user);
            model.addAttribute("success", "저장했습니다.");
        } else
            model.addAttribute("error", message);
        model.addAttribute("departments", departmentMapper.selectAll());
        model.addAttribute("types", typeMapper.selectAll());
        
        return "edit";
    }


}
