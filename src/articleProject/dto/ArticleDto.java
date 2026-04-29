package articleProject.dto;

import articleProject.Entity.Article;
import articleProject.Entity.Comment;
import articleProject.Entity.CommonField;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDto extends CommonField {
    private long id;
    private String name;
    private String title;
    private String content;
    private List<Comment> commentList;

    public ArticleDto(long id, String name, String title, String content) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
    }
    public ArticleDto(long id, String name, String title, String content, LocalDateTime insertedDate) {
        this(id,name,title,content);
        this.setInsertedDate(insertedDate);
    }
    public ArticleDto(long id, String name, String title, String content, LocalDateTime insertedDate, LocalDateTime updatedDate) {
        this(id,name,title,content,insertedDate);
        this.setUpdatedDate(updatedDate);
    }
    public ArticleDto(long id, String name, String title, String content, LocalDateTime insertedDate, LocalDateTime updatedDate, List<Comment> comments) {
        this(id,name,title,content,insertedDate,updatedDate);
        this.commentList = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public ArticleDto makeArticleDto(Long id, String name, String title, String content, LocalDateTime insertedDate) {
        return new ArticleDto(id, name, title, content, insertedDate);
    }

    public ArticleDto fromArticle(Article article) {
        return new ArticleDto(
                article.getId(), //id
                article.getName(), //이름
                article.getTitle(), //제목
                article.getContent(), //내용
                article.getInsertedDate(), // 등록시간
                article.getUpdatedDate(), // 업데이트 시간
                article.getCommentList() // 댓글 리스트
        );
    }

    public Article fromDto(ArticleDto dto) {
        return new Article(
                dto.getId(), //id
                dto.getName(), //이름
                dto.getTitle(), //제목
                dto.getContent(), //내용
                dto.getCommentList() // 댓글 리스트
        );
    }
}
