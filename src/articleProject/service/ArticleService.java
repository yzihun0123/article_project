package articleProject.service;

import articleProject.Entity.Article;
import articleProject.crudInterface.CrudInterface;
import articleProject.dao.ArticleDAO;
import articleProject.dto.ArticleDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleService {

    CrudInterface repository = new ArticleDAO();

    public List<ArticleDto> all() {
        return repository.all()
                .stream()
                .map(article -> new ArticleDto(
                        article.getId(),
                        article.getName(),
                        article.getTitle(),
                        article.getContent()
                ).fromArticle(article))
                .collect(Collectors.toList());
    }

    public void newArticle(ArticleDto dto) {
        dto.setInsertedDate(LocalDateTime.now());
        if (dto.getCommentList() == null) {
            dto.setCommentList(new ArrayList<>());
        }
        Article article = dto.fromDto(dto);
        repository.newArticle(article);
    }

    public Article detail(Long id) {
        return repository.detail(id);
    }

    public boolean delete(Long id) {
        return repository.delete(id);
    }

    public void update(ArticleDto dto) {
        dto.setUpdatedDate(LocalDateTime.now());
        Article article = dto.fromDto(dto);
        repository.update(article);
    }
}