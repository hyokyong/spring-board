package pj1;

import java.util.List;

public interface UserMapper {
    User selectByUid(String u_id);
    User selectById(int id);
    void insert(User user);
    List<User> selectPage(Pagination pagination);
    int selectCount();
    void update(User user);
    void delete(int id);
}
