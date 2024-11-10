package store.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.view.OutputView;

public class Receipt {

    private final OutputView outputView = new OutputView();
    private final Map<String, Integer> productQuantityMap = new HashMap<>();

    private int totalPrice = 0;
    private int totalQuantity = 0;
    private int totalDiscountPrice = 0;

    public void printReceipt(List<PurchasedProduct> purchasedProductList, int discount) {
        init();

        outputView.receiptHeader();

        processPurchasedProducts(purchasedProductList);

        printGiftProducts();
        printFooter(discount);
    }

    private void init() {
        productQuantityMap.clear();
        totalPrice = 0;
        totalQuantity = 0;
        totalDiscountPrice = 0;
    }

    private void processPurchasedProducts(List<PurchasedProduct> purchasedProductList) {
        purchasedProductList.forEach(purchasedProduct -> {
            String name = purchasedProduct.getName();
            int price = purchasedProduct.getPrice();
            int quantity = purchasedProduct.getQuantity() + purchasedProduct.getPromotionQuantity();
            int promotionGift = calculatePromotionGift(purchasedProduct);

            addPromotionGiftToMap(name, promotionGift);

            outputView.receiptProduct(name, quantity, price * quantity);
            updateValues(price, quantity, promotionGift);
        });
    }

    private int calculatePromotionGift(PurchasedProduct purchasedProduct) {
        Promotion promotion = purchasedProduct.getPromotion();

        if (promotion != null) {
            return (purchasedProduct.getPromotionQuantity() / (promotion.getBuy() + promotion.getGet()))
                    * promotion.getGet();
        }

        return 0;
    }

    private void addPromotionGiftToMap(String name, int promotionGift) {
        if (promotionGift > 0) {
            productQuantityMap.put(name, promotionGift);
        }
    }

    private void updateValues(int price, int quantity, int promotionGift) {
        totalPrice += price * quantity;
        totalDiscountPrice += price * promotionGift;
        totalQuantity += quantity;
    }

    private void printGiftProducts() {
        outputView.receiptGiftHeader();

        productQuantityMap.forEach(outputView::receiptGift);
    }

    private void printFooter(int discount) {
        outputView.receiptFooter();
        outputView.receiptTotalAmount(totalQuantity, totalPrice);
        outputView.receiptPromotionDiscount(totalDiscountPrice);
        outputView.receiptMembershipDiscount(discount);
        outputView.receiptFinalAmount(totalPrice - totalDiscountPrice - discount);
    }
}
