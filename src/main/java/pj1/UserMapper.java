package pj1;

public interface UserMapper {
    User selectByUid(String u_id);
    void insert(User user);
    
    void update(User user);
    void delete(int id);
}
