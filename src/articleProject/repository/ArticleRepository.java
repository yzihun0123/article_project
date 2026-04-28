package articleProject.repository;

import articleProject.Entity.Article;
import articleProject.Entity.Comment;
import articleProject.crudInterface.CrudInterface;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository implements CrudInterface {

    public static Long articleId = 1L;
    public static Long commentId = 1L;
    public static List<Article> articleList = new ArrayList<>();

    @Override
    public List<Article> all() {
        return articleList;
    }

    @Override
    public void newArticle(Article article) {
        articleList.add(article);
    }

    @Override
    public Article detail(Long id) {
        for (int i = 0; i < articleList.size(); i++) {
            if (id.equals(articleList.get(i).getId())) {
                return articleList.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        for (int i = 0; i < articleList.size(); i++) {
            if (id.equals(articleList.get(i).getId())) {
                articleList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Article article) {
        for (int i = 0; i < articleList.size(); i++) {
            if (article.getId() == articleList.get(i).getId()) {
                articleList.set(i, article);
            }
        }
    }

    @Override
    public void insertComment(Comment comment) {
        Article article = detail(comment.getArticleId());
        if (article != null) {
            article.addComments(comment);
            commentId++;
        }
    }

    @Override
    public void updateComment(Comment comment) {
        for (Article article : articleList) {
            List<Comment> comments = article.getCommentList();
            for (int i = 0; i < comments.size(); i++) {
                if (comment.getCommentId() == comments.get(i).getCommentId()) {
                    comments.set(i, comment);
                    return;
                }
            }
        }
    }

    @Override
    public void deleteComment(Long deleteCommentId) {
        for (Article article : articleList) {
            List<Comment> comments = article.getCommentList();
            for (int i = 0; i < comments.size(); i++) {
                if (deleteCommentId == comments.get(i).getCommentId()) {
                    comments.remove(i);
                    return;
                }
            }
        }
    }
}