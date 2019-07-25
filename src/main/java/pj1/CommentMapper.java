package pj1;

import java.util.List;

public interface CommentMapper {
	void insert(Comment comment);
	List<Comment> selectByAid(int a_id);
}
