package articleProject.service;

import articleProject.crudInterface.CrudInterface;
import articleProject.dao.ArticleDAO;
import articleProject.dto.CommentDto;
// import articleProject.repository.ArticleRepository; // 메모리 버전

public class CommentService {

    CrudInterface dao = new ArticleDAO();           // DB 버전
    // CrudInterface dao = new ArticleRepository(); // 메모리 버전

    /** 댓글 등록 */
    public void commentAdd(CommentDto comment) {
        // CommentDto → Comment Entity 변환 후 저장
        dao.insertComment(comment.fromDto(comment));
    }

    /** 댓글 수정 */
    public void commentUpdate(CommentDto comment) {
        // CommentDto → Comment Entity 변환 후 수정
        dao.updateComment(comment.fromDto(comment));
    }

    /** 댓글 삭제 */
    public void commentDelete(Long deleteCommentId) {
        dao.deleteComment(deleteCommentId);
    }
}