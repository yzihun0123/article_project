package articleProject.service;

import articleProject.crudInterface.CrudInterface;
import articleProject.dao.ArticleDAO;
import articleProject.dto.CommentDto;

public class CommentService {

    CrudInterface dao = new ArticleDAO();

    public void commentAdd(CommentDto comment) {
        dao.insertComment(comment.fromDto(comment));
    }

    public void commentUpdate(CommentDto comment) {
        dao.updateComment(comment.fromDto(comment));
    }

    public boolean commentDelete(Long deleteCommentId) {
        return dao.deleteComment(deleteCommentId);
    }
}