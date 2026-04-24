package articleProject.dao;

import articleProject.Entity.Article;
import articleProject.Entity.Comment;
import articleProject.crudInterface.CrudInterface;
import articleProject.db.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ArticleDAO extends CrudInterface {
    @Override
    public List<Article> all() {
        Connection conn = DBConn.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        return List.of();
    }

    @Override
    public void newArticle(Article article) {

    }

    @Override
    public Article detail(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(Article article) {

    }

    @Override
    public void insertComment(Comment comment) {

    }

    @Override
    public void updateComment(Comment comment) {

    }

    @Override
    public void deleteComment(Long deleteCommentId) {

    }
}
