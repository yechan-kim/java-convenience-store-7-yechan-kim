package store.model;

import java.util.List;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class Cashier {

    private final List<Product> productList;
    private final PromotionHandler promotionHandler = new PromotionHandler();

    private Cashier(List<Product> productList) {
        this.productList = productList;
    }

    public static Cashier from(List<Product> productList) {
        return new Cashier(productList);
    }

    public List<PurchasedProduct> requestPayment(List<ShoppingCartProduct> shoppingCart) {
        return shoppingCart.stream()
                .map(this::processShoppingCartProduct)
                .toList();
    }

    private PurchasedProduct processShoppingCartProduct(ShoppingCartProduct shoppingCartProduct) {
        Product product = findProduct(productList, shoppingCartProduct.getName());
        int quantity = shoppingCartProduct.getQuantity();

        if (product.getPromotionQuantity() + product.getQuantity() < quantity) {
            throw StoreException.from(ErrorMessage.STOCK_EXCEEDED);
        }

        return promotionHandler.applyPromotion(product, quantity);
    }

    private Product findProduct(List<Product> productList, String name) {
        return productList.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> StoreException.from(ErrorMessage.NON_EXISTENT_PRODUCT));
    }
}
