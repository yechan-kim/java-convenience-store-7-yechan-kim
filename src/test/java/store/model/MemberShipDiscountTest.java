package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.Application;

public class MemberShipDiscountTest extends NsTest {

    private final MemberShipDiscountHandler memberShipDiscountHandler = new MemberShipDiscountHandler();

    @Test
    void 멤버십_할인_적용() {
        List<PurchasedProduct> purchasedProducts = List.of(PurchasedProduct.of("콜라", 1000, 0, 1, null));
        assertThat(memberShipDiscountHandler.apply(purchasedProducts, "Y")).isEqualTo(300);
    }

    @Test
    void 멤버십_할인_미적용() {
        List<PurchasedProduct> purchasedProducts = List.of(PurchasedProduct.of("콜라", 1000, 0, 1, null));
        assertThat(memberShipDiscountHandler.apply(purchasedProducts, "N")).isEqualTo(0);
    }

    @Test
    void 멤버십_할인_한도_초과() {
        List<PurchasedProduct> purchasedProducts = List.of(PurchasedProduct.of("정식도시락", 6400, 0, 8, null));
        assertThat(memberShipDiscountHandler.apply(purchasedProducts, "Y")).isEqualTo(8000);
    }

    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
