package store.model;

import java.util.List;
import store.controller.Command;

public class MemberShipDiscountHandler {

    private static final double DISCOUNT_RATE = 0.3;
    private static final int MAX_DISCOUNT = 8000;

    public int apply(List<PurchasedProduct> purchasedProductList, String command) {
        if (command.equals(Command.YES.getValue())) {
            return calculateDiscount(purchasedProductList);
        }

        return 0;
    }

    private int calculateDiscount(List<PurchasedProduct> purchasedProductList) {
        int discount = purchasedProductList.stream()
                .mapToInt(p -> (int) (p.getPrice() * p.getQuantity() * DISCOUNT_RATE))
                .sum();
        return Math.min(discount, MAX_DISCOUNT);
    }
}
