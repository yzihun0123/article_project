package articleProject.Entity;

import java.util.ArrayList;
import java.util.List;

public class Article extends  CommonField{
    private long id;
    private String name;
    private String title;
    private String content;

    private List<Comment> commentList = new ArrayList<>();

    public Article(long id, String name, String title, String content, List<Comment> commentList) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.commentList = commentList;
    }

    public void addComments(Comment comment) {
        commentList.add(comment);
    }

    public void setCommentList(List<Comment> comments) {
        commentList = comments;
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
}
