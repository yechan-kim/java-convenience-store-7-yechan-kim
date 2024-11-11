package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.Application;
import store.exception.ErrorMessage;
import store.validator.InputValidator;

public class ProductTest extends NsTest {

    private static final String ERROR_PREFIX = "[ERROR] ";
    Cashier cashier = Cashier.from(List.of(Product.from(List.of("콜라", "1000", "10", "null"))));

    @Test
    void 같은_상품_담기() {
        assertThat(ShoppingCartProduct.createList("[콜라-5],[콜라-5]")).isEqualTo(
                List.of(ShoppingCartProduct.of("[콜라-10]")));
    }

    @Test
    void 구매할_상품과_수량_형식이_올바르지_않은_경우_예외_발생() {
        String command = "콜라-3";
        InputValidator validator = new InputValidator();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.shoppingCartFormat(command));
        assertThat(exception.getMessage()).isEqualTo(ERROR_PREFIX + ErrorMessage.INVALID_FORMAT.getMessage());
    }

    @Test
    void 존재하지_않는_상품을_입력한_경우_예외_발생() {
        List<ShoppingCartProduct> shoppingCart = List.of(ShoppingCartProduct.of("[몬스터-3]"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> cashier.requestPayment(shoppingCart));
        assertThat(exception.getMessage()).isEqualTo(ERROR_PREFIX + ErrorMessage.NON_EXISTENT_PRODUCT.getMessage());
    }

    @Test
    void 재고가_없는_상품을_구매하려고_하는_경우_예외_발생() {
        List<ShoppingCartProduct> shoppingCart = List.of(ShoppingCartProduct.of("[콜라-11]"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> cashier.requestPayment(shoppingCart));
        assertThat(exception.getMessage()).isEqualTo(ERROR_PREFIX + ErrorMessage.STOCK_EXCEEDED.getMessage());
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }

}
