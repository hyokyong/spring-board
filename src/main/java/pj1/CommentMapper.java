package pj1;

import java.util.List;

public interface CommentMapper {
	void insert(Comment comment);
	List<Comment> selectByAid(int a_id);
	Comment selectByCid(int c_id);
	void deleteAll(int a_id);
	void delete(int c_id);
	void update(Comment comment);
}
