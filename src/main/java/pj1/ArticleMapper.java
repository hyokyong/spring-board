package pj1;

import java.util.List;

public interface ArticleMapper {
    List<Article> selectPage(Pagination pagination);
    int selectCount(Pagination pagination);
    Article selectByAid(int a_id);
    void updateClick(int a_id);
    void insert(Article article);
    void update(Article article);
    void delete(int a_id);
}
