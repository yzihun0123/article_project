package articleProject.service;

import articleProject.Entity.Comment;
import articleProject.crudInterface.CrudInterface;
import articleProject.dao.ArticleDAO;

public class CommentService {
    CrudInterface dao = new ArticleDAO();

    public void insertComment(Comment comment) {
        dao.insertComment(comment);
    }

    public void updateComment(Comment comment) {
        dao.updateComment(comment);
    }

    public void deleteComment(Long deleteCommentId) {
        dao.deleteComment(deleteCommentId);
    }
}
