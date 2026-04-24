package articleProject.dto;

import articleProject.Entity.Comment;
import articleProject.Entity.CommonField;

import java.time.LocalDateTime;
import java.util.List;

public class articleDto extends CommonField {
    private long id;
    private String name;
    private String title;
    private String content;
    private List<Comment> commentList;

    public articleDto(long id, String name, String title, String content) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
    }
    public articleDto(long id, String name, String title, String content, LocalDateTime insertedDate) {
        this(id,name,title,content);
        this.setInsertedDate(insertedDate);
    }
    public articleDto(long id, String name, String title, String content, LocalDateTime insertedDate,LocalDateTime updatedDate) {
        this(id,name,title,content,insertedDate);
        this.setUpdatedDate(updatedDate);
    }
    public articleDto(long id, String name, String title, String content, LocalDateTime insertedDate,LocalDateTime updatedDate,List<Comment> comments) {
        this(id,name,title,content,insertedDate,updatedDate);
        this.commentList = comments;
    }
    
}
