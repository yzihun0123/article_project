package articleProject.view;

import articleProject.Entity.Article;
import articleProject.Entity.Comment;
import articleProject.dao.ArticleDAO;
import articleProject.dto.ArticleDto;
import articleProject.dto.CommentDto;
import articleProject.repository.ArticleRepository;
import articleProject.service.ArticleService;
import articleProject.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ArticleView {

    Scanner sc = new Scanner(System.in);
    ArticleService articleService = new ArticleService();
    CommentService commentService = new CommentService();
    List<ArticleDto> articles;

    public void showAll() {
        articles = articleService.all();

        System.out.println("==========================================");
        System.out.printf("%-6s %-12s %-16s %s %n", "id", "name", "title", "작성일");
        System.out.println("==========================================");

        if (articles.isEmpty()) {
            System.out.println("게시글이 없습니다.");
        } else {
            for (ArticleDto article : articles) {
                System.out.printf("%-6d %-12s %-16s %s %n",
                        article.getId(),
                        article.getName(),
                        article.getTitle(),
                        article.getInsertedDate()
                );
                if (article.getCommentList() != null) {
                    for (var comment : article.getCommentList()) {
                        CommentDto commentDto = new CommentDto(
                                comment.getCommentId(),
                                comment.getArticleId(),
                                comment.getName(),
                                comment.getContent()
                        );
                        System.out.println(commentDto);
                    }
                }
            }
        }
        System.out.println("==========================================");
    }

    public void showNewArticle() {
        System.out.println("새글 입력창입니다.");

        System.out.print("작성자 : ");
        String name = sc.nextLine();

        System.out.print("제목   : ");
        String title = sc.nextLine();

        System.out.print("내용   : ");
        String content = sc.nextLine();

        ArticleDto dto = new ArticleDto(
                ArticleRepository.articleId,
                name, title, content
        ).makeArticleDto(
                ArticleRepository.articleId,
                name, title, content,
                LocalDateTime.now()
        );

        ArticleRepository.articleId++;
        articleService.newArticle(dto);
    }

    public void showDetail() {
        System.out.print("확인할 게시글의 아이디를 입력하세요\n> ");
        Long id = Long.parseLong(sc.nextLine().trim());

        Article article = articleService.detail(id);
        articles = articleService.all();
        if (article == null) {
            System.out.println("게시글을 찾을 수 없습니다.");
            return;
        }

        System.out.println();
        System.out.println("🚀 ID      : " + article.getId());
        System.out.println("🚀 Name    : " + article.getName());
        System.out.println("🚀 Title   : " + article.getTitle());
        System.out.println("🚀 Content : " + article.getContent());
        System.out.println("🚀 작성일   : " + article.getInsertedDate());
        System.out.println("🚀 수정일   : " + article.getUpdatedDate());
        System.out.println();

        System.out.println("댓글 리스트");
        if (article.getCommentList() == null || article.getCommentList().isEmpty()) {
            System.out.println("해당 게시글에는 댓글이 없습니다.");
        } else {
            for (var comment : article.getCommentList()) {
                CommentDto commentDto = new CommentDto(
                        comment.getCommentId(),
                        comment.getArticleId(),
                        comment.getName(),
                        comment.getContent()
                );
                System.out.println(commentDto.toString());
            }
        }
        System.out.println();

        while (true) {
            System.out.println("0. 댓글보기 1.댓글입력  2.댓글수정  3.댓글삭제  4.돌아가기");
            System.out.print("> ");
            int choice = Integer.parseInt(sc.nextLine().trim());

            switch (choice) {
                case 0 -> {
                    if (article.getCommentList().isEmpty()) {
                        System.out.println("해당 게시글에 댓글이 없습니다.");
                    } else {
                        System.out.println("==========================================");
                        System.out.printf("%-6d %-12s %-16s %s %n",
                                article.getId(),
                                article.getName(),
                                article.getTitle(),
                                article.getInsertedDate()
                        );
                        for (var comment : article.getCommentList()) {
                            CommentDto commentDto = new CommentDto(
                                    comment.getCommentId(),
                                    comment.getArticleId(),
                                    comment.getName(),
                                    comment.getContent()
                            );
                            System.out.println(commentDto.toString());
                        }
                        System.out.println("==========================================");
                    }
                }
                case 1 -> {
                    System.out.print("댓글 등록자 이름: ");
                    String name = sc.nextLine();
                    if (name.isEmpty()) {
                        System.out.println("이름을 입력하지 않았습니다");
                        break;
                    }
                    System.out.print("댓글 내용: ");
                    String content = sc.nextLine();
                    if (content.isEmpty()) {
                        System.out.println("내용을 입력하지 않았습니다.");
                        break;
                    }

                    CommentDto comment = new CommentDto(
                            ArticleRepository.commentId,
                            article.getId(),
                            name,
                            content
                    );
                    ArticleRepository.commentId++;
                    commentService.commentAdd(comment);
                    System.out.println("댓글이 등록되었습니다.");
                }
                case 2 -> {
                    System.out.print("수정할 댓글 번호: ");
                    Long updateCommentId = Long.parseLong(sc.nextLine().trim());
                    System.out.print("수정 내용: ");
                    String updateContent = sc.nextLine();

                    CommentDto comment = new CommentDto(
                            updateCommentId,
                            article.getId(),
                            "",
                            updateContent
                    );
                    commentService.commentUpdate(comment);
                    System.out.println("댓글이 수정되었습니다.");
                }
                case 3 -> {
                    System.out.print("삭제할 댓글 번호: ");
                    Long deleteCommentId = Long.parseLong(sc.nextLine().trim());
                    if (commentService.commentDelete(deleteCommentId)) {
                        System.out.println("댓글이 삭제되었습니다.");
                    } else {
                        System.out.println("해당하는 댓글 번호가 없습니다");
                    }
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    public void showDelete() {
        System.out.print("삭제할 게시글 ID: ");
        Long id = Long.parseLong(sc.nextLine().trim());
        boolean result = articleService.delete(id);
        System.out.println(result ? "삭제됐습니다." : "게시글을 찾을 수 없습니다.");
    }

    public void showUpdate() {
        System.out.print("수정할 게시글 ID: ");
        Long id = Long.parseLong(sc.nextLine().trim());

        Article article = articleService.detail(id);
        if (article == null) {
            System.out.println("게시글을 찾을 수 없습니다.");
            return;
        }

        System.out.println("현재 제목 : " + article.getTitle());
        System.out.println("현재 내용 : " + article.getContent());

        System.out.print("새 제목   : ");
        String newTitle = sc.nextLine();
        System.out.print("새 내용   : ");
        String newContent = sc.nextLine();

        ArticleDto dto = new ArticleDto(
                article.getId(),
                article.getName(),
                newTitle,
                newContent,
                null,
                LocalDateTime.now(),
                article.getCommentList()
        );

        articleService.update(dto);
        System.out.println("수정되었습니다.");
    }
}