package articleProject.crudInterface;

import articleProject.Entity.Article;
import articleProject.Entity.Comment;

import java.util.List;

public interface CrudInterface {
    // 게시글
    List<Article> all();
    void    newArticle(Article article);
    Article detail(Long id);
    boolean delete(Long id);
    void    update(Article article);

    // 댓글
    void insertComment(Comment comment);
    void updateComment(Comment comment);
    void deleteComment(Long deleteCommentId);
}
