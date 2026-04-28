package articleProject.main;

import articleProject.view.ArticleView;
import java.util.Scanner;

public class ArticleMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArticleView view = new ArticleView();

        while (true) {
            System.out.println("========== 게시글 메뉴 ==========");
            System.out.println("0.전체보기  1.새글  2.상세  3.삭제  4.수정  5.종료");
            System.out.print("> ");
            int menu = sc.nextInt();

            switch (menu) {
                case 0 -> view.showAll();
                case 1 -> view.showNewArticle();
                case 2 -> view.showDetail();
                case 3 -> view.showDelete();
                case 4 -> view.showUpdate();
                case 5 -> {
                    System.out.println("종료합니다.");
                    return;
                }
                default -> System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }
}