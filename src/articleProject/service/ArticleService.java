package articleProject.service;

import articleProject.Entity.Article;
import articleProject.crudInterface.CrudInterface;
import articleProject.dao.ArticleDAO;
import articleProject.dto.ArticleDto;
// import articleProject.repository.ArticleRepository; // 메모리 버전

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleService {

    // ✅ 한 줄만 바꾸면 DB ↔ 메모리 전환
    CrudInterface repository = new ArticleDAO();           // DB 버전
    // CrudInterface repository = new ArticleRepository(); // 메모리 버전

    /** 전체 게시글 조회 → ArticleDto 리스트 반환 */
    public List<ArticleDto> all() {
        return repository.all()
                .stream()
                // ✅ 핵심 에러 수정: ArticleDto::fromArticle 은 인스턴스 없이 참조 불가
                // → new ArticleDto 인스턴스를 생성 후 fromArticle() 호출
                .map(article -> new ArticleDto(
                        article.getId(),
                        article.getName(),
                        article.getTitle(),
                        article.getContent()
                ).fromArticle(article))
                .collect(Collectors.toList());
    }

    /** 게시글 등록 */
    public void newArticle(ArticleDto dto) {
        // 등록 시간 세팅
        dto.setInsertedDate(LocalDateTime.now());
        // ArticleDto → Article Entity 변환 후 저장
        Article article = dto.fromDto(dto);
        repository.newArticle(article);
    }

    /** 게시글 단건 조회 */
    public Article detail(Long id) {
        return repository.detail(id);
    }

    /** 게시글 삭제 */
    public boolean delete(Long id) {
        return repository.delete(id);
    }

    /** 게시글 수정 */
    public void update(ArticleDto dto) {
        // 수정 시간 세팅
        dto.setUpdatedDate(LocalDateTime.now());
        // ArticleDto → Article Entity 변환 후 수정
        Article article = dto.fromDto(dto);
        repository.update(article);
    }
}