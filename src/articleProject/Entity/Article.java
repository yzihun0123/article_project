package articleProject.Entity;

import java.util.ArrayList;
import java.util.List;

public class Article {
    private long id;
    private String name;
    private String title;
    private String content;

    private List<Comment> commentList = new ArrayList<>();

    public void addComments(Comment comment) {
        commentList.add(comment);
    }

    public void setCommentList(List<Comment> comments) {
        commentList = comments;
    }
}
