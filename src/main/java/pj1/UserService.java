package pj1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public String validateBeforeInsert(User user) { //회원가입
        
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

    //public String validateBeforeUpdate(User user) { //마이페이지
  //      String s = user.getName();
    //    if (StringUtils.isBlank(s))
   //         return "이름을 입력하세요.";

   //     s = user.getEmail();
   //     if (StringUtils.isBlank(s))
   //         return "이메일 주소를 입력하세요.";

   //     s = user.getUserType();
   //     if (StringUtils.isBlank(s))
   //         return "사용자 유형을 선택하세요.";

   //     s = user.getLoginId();
   //     if (StringUtils.isBlank(s))
  //          return "로그인ID를 입력하세요.";

   //     User user2 = userMapper.selectByLoginId(s);
   //     if (user2 != null && user.getId() != user2.getId())
   //         return "로그인ID가 중복됩니다.";

  //      return null;
  //  }

}
