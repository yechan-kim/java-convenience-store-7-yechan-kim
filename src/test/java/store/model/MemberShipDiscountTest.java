package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import store.Application;

public class MemberShipDiscountTest extends NsTest {

    @Test
    void 멤버십_할인_적용() {
        assertSimpleTest(() -> {
            run("[콜라-3],[에너지바-5]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈9,000");
        });
    }

    @Test
    void 멤버십_할인_미적용() {
        assertSimpleTest(() -> {
            run("[콜라-3],[에너지바-5]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈12,000");
        });
    }

    @Test
    void 멤버십_할인_한도_초과() {
        assertSimpleTest(() -> {
            run("[정식도시락-8]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈43,200");
        });
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
