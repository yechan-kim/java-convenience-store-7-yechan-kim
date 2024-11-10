package store.view;

import java.util.List;
import store.model.Product;

public class OutputView {

    private static final String BUY_PRODUCT_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String CURRENT_PRODUCTS_MESSAGE = "현재 보유하고 있는 상품입니다.";
    private static final String GREETING_MESSAGE = "안녕하세요. W편의점입니다.";
    private static final String NON_PROMOTION_PRODUCT_FORMAT = "- %s %,d원 %s";
    private static final String OUT_OF_STOCK = "재고 없음";
    private static final String PROMOTION_PRODUCT_FORMAT = "- %s %,d원 %s %s";
    private static final String QUANTITY_SUFFIX = "개";

    public void errorMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    public void intro() {
        System.out.println(GREETING_MESSAGE);
        System.out.println(CURRENT_PRODUCTS_MESSAGE + System.lineSeparator());
    }

    public void buyProductMessage() {
        System.out.println(System.lineSeparator() + BUY_PRODUCT_MESSAGE);
    }

    public void products(List<Product> products) {
        for (Product product : products) {
            if (product.getPromotion() != null) {
                promotionProduct(product);
            }

            nonPromotionProduct(product);
        }
    }

    private void nonPromotionProduct(Product product) {
        if (product.getQuantity() == 0) {
            System.out.printf(NON_PROMOTION_PRODUCT_FORMAT, product.getName(), product.getPrice(),
                    OUT_OF_STOCK + System.lineSeparator());
            return;
        }

        System.out.printf(NON_PROMOTION_PRODUCT_FORMAT, product.getName(), product.getPrice(),
                product.getQuantity() + QUANTITY_SUFFIX + System.lineSeparator());
    }

    private void promotionProduct(Product product) {
        if (product.getPromotionQuantity() == 0) {
            System.out.printf(PROMOTION_PRODUCT_FORMAT, product.getName(), product.getPrice(), OUT_OF_STOCK,
                    product.getPromotion().getName() + System.lineSeparator());
            return;
        }

        System.out.printf(PROMOTION_PRODUCT_FORMAT, product.getName(), product.getPrice(),
                product.getPromotionQuantity() + QUANTITY_SUFFIX,
                product.getPromotion().getName() + System.lineSeparator());
    }
}
