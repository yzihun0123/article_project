package articleProject.service;

import articleProject.Entity.Article;
import articleProject.crudInterface.CrudInterface;
import articleProject.dao.ArticleDAO;

import java.util.List;

public class ArticleService {
    CrudInterface repository = new ArticleDAO();

    public List<Article> all() {
        return repository.all();
    }

    public void newArticle(Article article) {
        repository.newArticle(article);
    }

    public Article detail(Long id) {
        return repository.detail(id);
    }

    public Boolean delete(Long id) {
        return repository.delete(id);
    }

    public void update(Article article){
        repository.update(article);
    }
}
