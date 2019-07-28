package pj1;

import java.util.List;

public interface UserMapper {
    User selectByUid(String u_id);
    User selectById(int id);
    void insert(User user);
    List<User> selectPage(Pagination pagination);
    int selectCount(Pagination pagination);
    void update(User user);
    void delete(int id);
}
