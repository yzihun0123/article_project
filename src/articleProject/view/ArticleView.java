package articleProject.view;

import articleProject.Entity.Article;
import articleProject.dto.ArticleDto;
import articleProject.dto.CommentDto;
import articleProject.repository.ArticleRepository;
import articleProject.service.ArticleService;
import articleProject.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ArticleView {

    // ── 필드 ────────────────────────────────────────────
    Scanner sc = new Scanner(System.in);
    ArticleService articleService = new ArticleService();
    CommentService commentService = new CommentService();
    List<ArticleDto> articles;

    // ── 전체 게시글 보기 (댓글 포함) ────────────────────
    public void showAll() {
        articles = articleService.all();

        System.out.println("==========================================");
        System.out.printf("%-6s %-12s %-16s %s%n", "id", "name", "title", "작성일");
        System.out.println("==========================================");

        if (articles.isEmpty()) {
            System.out.println("게시글이 없습니다.");
        } else {
            for (ArticleDto article : articles) {
                // 게시글 한 줄 출력
                System.out.printf("%-6d %-12s %-16s %s%n",
                        article.getId(),
                        article.getName(),
                        article.getTitle(),
                        article.getInsertedDate() != null ? article.getInsertedDate() : ""
                );

                // 댓글 출력 (CommentDto.toString() 활용)
                if (article.getCommentList() != null) {
                    for (int i = 0; i < article.getCommentList().size(); i++) {
                        CommentDto commentDto = new CommentDto(
                                article.getCommentList().get(i).getCommentId(),
                                article.getCommentList().get(i).getArticleId(),
                                article.getCommentList().get(i).getName(),
                                article.getCommentList().get(i).getContent()
                        );
                        System.out.println(commentDto.toString());
                    }
                }
            }
        }

        System.out.println("==========================================");
        sc.nextLine(); // 버퍼 비우기
    }

    // ── 새글 작성 ───────────────────────────────────────
    public void showNewArticle() {
        sc.nextLine(); // 버퍼 비우기

        System.out.print("작성자 : ");
        String name = sc.next();

        System.out.print("제목   : ");
        String title = sc.next();

        System.out.print("내용   : ");
        String content = sc.next();

        // makeArticleDto()로 DTO 생성
        ArticleDto dto = new ArticleDto(
                ArticleRepository.articleId,
                name,
                title,
                content
        ).makeArticleDto(
                ArticleRepository.articleId,
                name,
                title,
                content,
                LocalDateTime.now()
        );

        // articleId 증가
        ArticleRepository.articleId++;

        // 서비스 호출
        articleService.newArticle(dto);

        System.out.println("게시글이 등록되었습니다.");
    }

    // ── 게시글 상세 + 댓글 CRUD ─────────────────────────
    public void showDetail() {
        System.out.print("조회할 게시글 ID : ");
        Long id = sc.nextLong();

        Article article = articleService.detail(id);

        if (article == null) {
            System.out.println("게시글을 찾을 수 없습니다.");
            return;
        }

        // 게시글 상세 출력
        System.out.println();
        System.out.println("🚀 ID      : " + article.getId());
        System.out.println("🚀 Name    : " + article.getName());
        System.out.println("🚀 Title   : " + article.getTitle());
        System.out.println("🚀 Content : " + article.getContent());
        // insertedDate / updatedDate 는 ArticleDto 기준 (Entity에 없으므로 서비스에서 가져온 경우 출력)
        System.out.println("🚀 작성일   : ");
        System.out.println("🚀 수정일   : ");
        System.out.println();

        // 댓글 리스트 출력
        System.out.println("🚗🚗  댓글 리스트  🚗🚗");
        if (article.getCommentList() != null && !article.getCommentList().isEmpty()) {
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

        // 댓글 CRUD 서브메뉴
        boolean running = true;
        while (running) {
            System.out.println("1.댓글입력  2.댓글수정  3.댓글삭제  4.돌아가기");
            System.out.print("> ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    // 댓글 입력
                    sc.nextLine();
                    System.out.print("등록자 이름 : ");
                    String name = sc.next();
                    System.out.print("내용        : ");
                    String content = sc.next();

                    CommentDto comment = new CommentDto(
                            null,               // commentId: null (신규)
                            article.getId(),    // articleId
                            name,
                            content
                    );
                    commentService.commentAdd(comment);
                    System.out.println("댓글이 등록되었습니다.");
                }
                case 2 -> {
                    // 댓글 수정
                    System.out.print("수정할 댓글 번호 : ");
                    Long updateCommentId = sc.nextLong();
                    sc.nextLine();
                    System.out.print("수정 내용 : ");
                    String updateContent = sc.next();

                    CommentDto comment = new CommentDto(
                            updateCommentId,
                            article.getId(),
                            "",                 // name은 수정 불필요
                            updateContent
                    );
                    commentService.commentUpdate(comment);
                    System.out.println("댓글이 수정되었습니다.");
                }
                case 3 -> {
                    // 댓글 삭제
                    System.out.print("삭제할 댓글 번호 : ");
                    Long deleteCommentId = sc.nextLong();

                    commentService.commentDelete(deleteCommentId);
                    System.out.println("댓글이 삭제되었습니다.");
                }
                case 4 -> {
                    // 돌아가기
                    running = false;
                }
                default -> System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    // ── 게시글 삭제 ─────────────────────────────────────
    public void showDelete() {
        System.out.print("삭제할 게시글 ID : ");
        Long id = sc.nextLong();

        boolean result = articleService.delete(id);

        if (result) {
            System.out.println("삭제됐습니다.");
        } else {
            System.out.println("실패했습니다.");
        }
    }

    // ── 게시글 수정 ─────────────────────────────────────
    public void showUpdate() {
        System.out.print("수정할 게시글 ID : ");
        Long id = sc.nextLong();
        sc.nextLine(); // 버퍼 비우기

        // 현재 내용 조회 후 출력
        Article article = articleService.detail(id);
        if (article == null) {
            System.out.println("게시글을 찾을 수 없습니다.");
            return;
        }

        System.out.println("현재 제목 : " + article.getTitle());
        System.out.println("현재 내용 : " + article.getContent());

        System.out.print("새 제목   : ");
        String newTitle = sc.next();
        System.out.print("새 내용   : ");
        String newContent = sc.next();

        // 수정 DTO 생성
        ArticleDto dto = new ArticleDto(
                article.getId(),
                article.getName(),
                newTitle,
                newContent,
                null,
                LocalDateTime.now(),          // setUpdatedDate
                article.getCommentList()
        );

        articleService.update(dto);
        System.out.println("수정되었습니다.");
    }
}