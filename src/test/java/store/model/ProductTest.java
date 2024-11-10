package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import store.Application;

public class ProductTest extends NsTest {

    @Test
    void 구매할_상품과_수량_형식이_올바르지_않은_경우_예외_발생() {
        assertSimpleTest(() -> {
            run("콜라-3", "[콜라-3]", "N", "N");
            assertThat(output()).contains("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 존재하지_않는_상품을_입력한_경우_예외_발생() {
        assertSimpleTest(() -> {
            run("[몬스터-3]", "[콜라-3]", "N", "N");
            assertThat(output()).contains("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 재고가_없는_상품을_구매하려고_하는_경우_예외_발생() {
        assertSimpleTest(() -> {
            run("[오렌지주스-10]", "[오렌지주스-2]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }

}
