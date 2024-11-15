package store;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import store.validator.InputValidator;

class ApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 잘못된_명령을_입력하면_예외_발생() {
        String command = "test";
        InputValidator inputValidator = new InputValidator();
        assertThrows(IllegalArgumentException.class, () -> inputValidator.command(command));
    }

    @Test
    void 실행_결과_예시() {
        assertNowTest(() -> {
            run("[콜라-3],[에너지바-5]", "Y", "Y", "[콜라-10]", "Y", "N", "Y", "[오렌지주스-1]", "Y", "Y", "N");
            assertThat(output()).contains(
                    // 1회
                    "안녕하세요. W편의점입니다.",
                    "현재 보유하고 있는 상품입니다.",

                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개",

                    "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])",

                    "멤버십 할인을 받으시겠습니까? (Y/N)",

                    "==============W 편의점================",
                    "상품명\t\t\t\t수량\t\t금액",
                    "콜라        \t\t3   \t     3,000",
                    "에너지바      \t\t5   \t    10,000",
                    "===============증\t정=================",
                    "콜라        \t\t1   ",
                    "=======================================",
                    "총구매액\t\t\t8   \t    13,000",
                    "행사할인\t\t\t\t\t    -1,000",
                    "멤버십할인\t\t\t\t\t    -3,000",
                    "내실돈\t\t\t\t\t\t     9,000",

                    "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)",

                    // 2회
                    "안녕하세요. W편의점입니다.",
                    "현재 보유하고 있는 상품입니다.",

                    "- 콜라 1,000원 7개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 재고 없음",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개",

                    "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])",

                    "현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)",

                    "멤버십 할인을 받으시겠습니까? (Y/N)",

                    "==============W 편의점================",
                    "상품명\t\t\t\t수량\t\t금액",
                    "콜라        \t\t10  \t    10,000",
                    "===============증\t정=================",
                    "콜라        \t\t2   ",
                    "=======================================",
                    "총구매액\t\t\t10  \t    10,000",
                    "행사할인\t\t\t\t\t    -2,000",
                    "멤버십할인\t\t\t\t\t        -0",
                    "내실돈\t\t\t\t\t\t     8,000",

                    "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)",

                    // 3회
                    "안녕하세요. W편의점입니다.",
                    "현재 보유하고 있는 상품입니다.",

                    "- 콜라 1,000원 재고 없음 탄산2+1",
                    "- 콜라 1,000원 7개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 재고 없음",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개",

                    "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])",

                    "현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)",

                    "멤버십 할인을 받으시겠습니까? (Y/N)",

                    "==============W 편의점================",
                    "상품명\t\t\t\t수량\t\t금액",
                    "오렌지주스     \t\t2   \t     3,600",
                    "===============증\t정=================",
                    "오렌지주스     \t\t1   \n",
                    "=======================================",
                    "총구매액\t\t\t2   \t     3,600",
                    "행사할인\t\t\t\t\t    -1,800",
                    "멤버십할인\t\t\t\t\t        -0",
                    "내실돈\t\t\t\t\t\t     1,800",

                    "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"
            );
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
