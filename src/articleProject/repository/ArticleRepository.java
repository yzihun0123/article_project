package articleProject.repository;

import articleProject.Entity.Article;
import articleProject.Entity.Comment;
import articleProject.crudInterface.CrudInterface;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository implements CrudInterface {

    static Long articleId = 1L;
    static Long commentId = 1L;
    static List<Article> articleList = new ArrayList<>();

    @Override
    public List<Article> all() {
        return articleList;
    }

    @Override
    public void newArticle(Article article) {
        articleList.add(article);
    }

    // articleList.get(i) == article
    // article.getid()
    @Override
    public Article detail(Long id) {
        for (int i = 0; i <= articleList.size(); i++) {
            if (id.equals(articleList.get(i).getId())) {
                return articleList.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        for (int i = 0; i <= articleList.size(); i++) {
            if (id.equals(articleList.get(i).getId())) {
                articleList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Article article) {
        for (int i = 0; i <= articleList.size(); i++) {
            if (article.getId() == (articleList.get(i).getId())) {
//                articleList.get(i).setId(article.getId());
//                articleList.get(i).setName(article.getName());
//                articleList.get(i).setTitle(article.getTitle());
//                articleList.get(i).setContent(article.getContent());
                articleList.set(i, article);
            }
        }
    }

    @Override
    public void insertComment(Comment comment) {
        detail(articleId).addComments(comment);
        commentId++;
    }

    @Override
    public void updateComment(Comment comment) {
        for (Article article : articleList) {
            for (int i = 0; i <= article.getCommentList().size(); i++) {
                if (comment.getCommentId() == article.getCommentList().get(i).getCommentId()) {
                    article.getCommentList().set(i, comment);
                }
            }
        }
    }

    @Override
    public void deleteComment(Long deleteCommentId) {
        for (Article article : articleList) {
            for (int i = 0; i <= article.getCommentList().size(); i++) {
                if (deleteCommentId == article.getCommentList().get(i).getCommentId()) {
                    article.getCommentList().remove(i);
                }
            }
        }
    }
}
