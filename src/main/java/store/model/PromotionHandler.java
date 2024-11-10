package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.controller.Command;
import store.view.InputView;
import store.view.OutputView;

public class PromotionHandler {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public PurchasedProduct applyPromotion(Product product, int quantity) {
        Promotion promotion = product.getPromotion();

        if (promotion == null) {
            return PurchasedProduct.of(product.getName(), product.getPrice(), 0, quantity, null);
        }

        return processPromotion(product, promotion, quantity);
    }

    private PurchasedProduct processPromotion(Product product, Promotion promotion, int quantity) {
        int promotionQuantity = product.getPromotionQuantity();

        if (validateNoPromotionDate(promotion)) {
            return PurchasedProduct.of(product.getName(), product.getPrice(), 0, quantity, promotion);
        }

        if (promotionQuantity < quantity) {
            return handleInsufficientPromotion(product, quantity, promotionQuantity, promotion);
        }

        if (isEligibleForFreeProduct(promotion, quantity, promotionQuantity)) {
            return handleFreeProductPromotion(product, quantity, promotion);
        }

        return PurchasedProduct.of(product.getName(), product.getPrice(), quantity, 0, promotion);
    }

    private boolean validateNoPromotionDate(Promotion promotion) {
        LocalDate now = DateTimes.now().toLocalDate();
        return promotion.getStartDate().isAfter(now) || promotion.getEndDate().isBefore(now);
    }

    private PurchasedProduct handleInsufficientPromotion(Product product, int quantity, int promotionQuantity,
                                                         Promotion promotion) {
        String name = product.getName();
        int promotionGroup = promotion.getBuy() + promotion.getGet();
        int canPromotionQuantity = promotionQuantity / promotionGroup * promotionGroup;

        if (isNoPromotionRefused(name, quantity - canPromotionQuantity)) {
            return PurchasedProduct.of(name, product.getPrice(), canPromotionQuantity, 0, promotion);
        }

        return PurchasedProduct.of(name, product.getPrice(), promotionQuantity, quantity - promotionQuantity,
                promotion);
    }

    private boolean isNoPromotionRefused(String name, int noPromotionQuantity) {
        outputView.noPromotionMessage(name, noPromotionQuantity);
        return inputView.readCommand().equals(Command.NO.getValue());
    }

    private boolean isEligibleForFreeProduct(Promotion promotion, int quantity, int promotionQuantity) {
        return quantity % (promotion.getGet() + promotion.getBuy()) == promotion.getBuy()
                && quantity < promotionQuantity;
    }

    private PurchasedProduct handleFreeProductPromotion(Product product, int quantity, Promotion promotion) {
        outputView.freeProductPromotionMessage(product.getName());
        if (inputView.readCommand().equals(Command.YES.getValue())) {
            return PurchasedProduct.of(product.getName(), product.getPrice(), quantity + 1, 0, promotion);
        }

        return PurchasedProduct.of(product.getName(), product.getPrice(), 0, quantity, promotion);
    }
}
