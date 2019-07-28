package pj1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pj1.MyAuthenticationProvider;

@Controller
public class UserController {

    @Autowired UserMapper userMapper;
    @Autowired DepartmentMapper departmentMapper;
    @Autowired TypeMapper typeMapper;
    @Autowired UserService userService;
    
    //로그인
   @RequestMapping(value="login.do", method=RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    //회원가입
    @RequestMapping(value="join.do", method = RequestMethod.GET)
    public String join(Model model) {
    	User user = new User();
    	model.addAttribute("user", user);
    	model.addAttribute("departments", departmentMapper.selectAll());
    	model.addAttribute("types", typeMapper.selectAll());
    	return "join";
    }

    @RequestMapping(value="join.do", method = RequestMethod.POST)
    public String join(User user, Model model) {
        
    	String message = userService.validate(user);
        if (message == null) {
        	String passwd = MyAuthenticationProvider.encryptPasswd(user.getU_passwd());
        	user.setU_passwd(passwd);
            userMapper.insert(user);
            return "login";
        } else {
            model.addAttribute("error", message);
            model.addAttribute("departments", departmentMapper.selectAll());
        	model.addAttribute("types", typeMapper.selectAll());
        	return "join";
        }
    	
    }
    
    //마이페이지
    @RequestMapping(value="mypage.do", method = RequestMethod.GET)
    public String mypage(Model model) {
    	User user = UserService.getCurrentUser();
    	user.setU_passwd(null); 
    	user.setU_passwdck(null);
    	model.addAttribute("user", user);
        model.addAttribute("departments", departmentMapper.selectAll());
    	model.addAttribute("types", typeMapper.selectAll());
        return "mypage";
    }

    @RequestMapping(value="mypage.do", method = RequestMethod.POST)
    public String mypage(User user, Model model) {
    	user.setId(UserService.getCurrentUser().getId());
        String message = userService.validateBeforeUpdate(user);
        if (message == null) {
        	String passwd = MyAuthenticationProvider.encryptPasswd(user.getU_passwd());
        	user.setU_passwd(passwd);
            userMapper.update(user);
            UserService.setCurrentUser(user);
            model.addAttribute("success", "저장했습니다.");
            return "redirect:list.do?bd=1";
        } else{
            model.addAttribute("error", message);
            model.addAttribute("departments", departmentMapper.selectAll());
            model.addAttribute("types", typeMapper.selectAll());
            return "mypage";
        }
    }


}
