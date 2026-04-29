package articleProject.dao;

import articleProject.Entity.Article;
import articleProject.Entity.Comment;
import articleProject.crudInterface.CrudInterface;
import articleProject.db.DBConn;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO implements CrudInterface {

    @Override
    public List<Article> all() {
        List<Article> articles = new ArrayList<>();
        Connection conn = DBConn.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM article ORDER BY id DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Article article = new Article(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("title"),
                        rs.getString("content"),
                        new ArrayList<>()
                );
                if (rs.getTimestamp("inserted_date") != null) {
                    article.setInsertedDate(rs.getTimestamp("inserted_date").toLocalDateTime());
                }
                if (rs.getTimestamp("updated_date") != null) {
                    article.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());
                }
                articles.add(article);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("All 오류: " + e.getMessage());
        }
        return articles;
    }

    @Override
    public void newArticle(Article article) {
        Connection conn = DBConn.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO article(name, title, content, inserted_date, updated_date)" +
                    " VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, article.getName());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(5, null);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Article detail(Long id) {
        Connection conn = DBConn.getConnection();
        PreparedStatement articlePs = null;
        ResultSet articleRs = null;
        PreparedStatement commentPs = null;
        ResultSet commentRs = null;
        try {
            String articleSql = "SELECT * FROM article WHERE id=?";
            articlePs = conn.prepareStatement(articleSql);
            articlePs.setLong(1, id);
            articleRs = articlePs.executeQuery();

            if (!articleRs.next()) {
                return null;
            }

            Article article = new Article(
                    articleRs.getLong("id"),
                    articleRs.getString("name"),
                    articleRs.getString("title"),
                    articleRs.getString("content"),
                    new ArrayList<>()
            );

            String commentSql = "SELECT * FROM comments WHERE article_id=?";
            commentPs = conn.prepareStatement(commentSql);
            commentPs.setLong(1, article.getId());
            commentRs = commentPs.executeQuery();

            List<Comment> comments = new ArrayList<>();
            while (commentRs.next()) {
                comments.add(new Comment(
                        commentRs.getLong("comment_id"),
                        commentRs.getLong("article_id"),
                        commentRs.getString("name"),
                        commentRs.getString("content")
                ));
            }
            if (articleRs.getTimestamp("inserted_date") != null) {
                article.setInsertedDate(articleRs.getTimestamp("inserted_date").toLocalDateTime());
            }
            article.setCommentList(comments);

            commentRs.close();
            commentPs.close();
            articleRs.close();
            articlePs.close();

            return article;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        Connection conn = DBConn.getConnection();
        PreparedStatement ps = null;
        int result = 0;
        try {
            String sql = "DELETE FROM article WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            result = ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result > 0;
    }

    @Override
    public void update(Article article) {
        Connection conn = DBConn.getConnection();
        PreparedStatement timePs = null;
        ResultSet timeRs = null;
        PreparedStatement ps = null;
        try {
            String timeSql = "SELECT inserted_date FROM article WHERE id=?";
            timePs = conn.prepareStatement(timeSql);
            timePs.setLong(1, article.getId());
            timeRs = timePs.executeQuery();

            Timestamp insertedDate = null;
            if (timeRs.next()) {
                insertedDate = timeRs.getTimestamp("inserted_date");
            }

            String sql = "UPDATE article SET name=?, title=?, content=?," +
                    " inserted_date=?, updated_date=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, article.getName());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContent());
            ps.setTimestamp(4, insertedDate);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(6, article.getId());
            ps.executeUpdate();

            timeRs.close();
            timePs.close();
            ps.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertComment(Comment comment) {
        Connection conn = DBConn.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO comments(name, content, article_id) VALUES(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, comment.getName());
            ps.setString(2, comment.getContent());
            ps.setLong(3, comment.getArticleId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateComment(Comment comment) {
        Connection conn = DBConn.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE comments SET content=? WHERE comment_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, comment.getContent());
            ps.setLong(2, comment.getCommentId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteComment(Long deleteCommentId) {
        Connection conn = DBConn.getConnection();
        int result = 0;
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM comments WHERE comment_id=?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, deleteCommentId);
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result>0;
    }
}