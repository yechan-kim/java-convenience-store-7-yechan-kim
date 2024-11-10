package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import store.Application;

public class PromotionTest extends NsTest {

    @Test
    void 프로모션_재고_부족_Y() {
        assertSimpleTest(() -> {
            run("[콜라-11]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈8,000");
        });
    }

    @Test
    void 프로모션_재고_부족_N() {
        assertSimpleTest(() -> {
            run("[콜라-11]", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈6,000");
        });
    }

    @Test
    void 프로모션_보너스_받기() {
        assertSimpleTest(() -> {
            run("[오렌지주스-1]", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈1,800");
        });
    }

    @Test
    void 프로모션_보너스_안_받기() {
        assertSimpleTest(() -> {
            run("[오렌지주스-1]", "N", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈1,260");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
