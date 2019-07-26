package pj1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    
    public String validate(User user) {
        
    	String s = user.getU_name();
        if (StringUtils.isBlank(s))
            return "이름을 입력하세요.";

        s = user.getU_email();
        if (StringUtils.isBlank(s))
            return "이메일 주소를 입력하세요.";
        
        s = user.getU_id();
        if (StringUtils.isBlank(s))
            return "사번을 입력하세요.";
        
        s = Integer.toString(user.getT_id());
        if (StringUtils.isBlank(s))
            return "직급을 선택하세요.";
        
        s = Integer.toString(user.getD_id());
        if (StringUtils.isBlank(s))
            return "부서를 선택하세요.";

        s = user.getU_id();
        User user2 = userMapper.selectByUid(s);
        if (user2 != null)
            return "이미 존재하는 사번입니다.";

        return null;
    }

    //회원수정
    public String validateBeforeUpdate(User user) {
    	String s = user.getU_name();
        if (StringUtils.isBlank(s))
            return "이름을 입력하세요.";

        s = user.getU_email();
        if (StringUtils.isBlank(s))
            return "이메일 주소를 입력하세요.";
        
        s = user.getU_id();
        if (StringUtils.isBlank(s))
            return "사번을 입력하세요.";
        
        s = Integer.toString(user.getT_id());
        if (StringUtils.isBlank(s))
            return "직급을 선택하세요.";
        
        s = Integer.toString(user.getD_id());
        if (StringUtils.isBlank(s))
            return "부서를 선택하세요.";

        if(user.getU_passwd().compareTo(user.getU_passwdck()) != 0)
        	return "비밀번호가 일치하지 않습니다.";

        return null;
    }
    
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof MyAuthenticationProvider.MyAuthenticaion)
            return ((MyAuthenticationProvider.MyAuthenticaion) authentication).getUser();
        return null;
    }

    public static void setCurrentUser(User user) {
        ((MyAuthenticationProvider.MyAuthenticaion)
                SecurityContextHolder.getContext().getAuthentication()).setUser(user);
    }


}
