package articleProject.dto;

import articleProject.Entity.Comment;

public class CommentDto {
    private long commentId;
    private long articleId;
    private String name;
    private String content;

    public CommentDto(Long commentId, Long articleId, String name, String content) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.name = name;
        this.content = content;
    }

    public CommentDto fromEntity(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getArticleId(),
                comment.getName(),
                comment.getContent()
        );
    }

    public Comment fromDto(CommentDto dto) {
        return new Comment(
                dto.getCommentId(),
                dto.getArticleId(),
                dto.getName(),
                dto.getContent()
        );
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "\t🏷️ " + commentId + "\t" + name + "\t" + content;
    }
}
