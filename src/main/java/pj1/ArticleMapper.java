package pj1;

import java.util.List;

public interface ArticleMapper {
    List<Article> selectPage(Pagination pagination);
    int selectCount();
    Article selectByAid(int a_id);
    
    void insert(User user);
    void update(User user);
    void delete(int id);
}
