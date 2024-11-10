package store.model;

import java.util.List;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class StockUpdater {

    public void updateStock(List<PurchasedProduct> purchasedProductList, List<Product> productList) {
        for (PurchasedProduct purchasedProduct : purchasedProductList) {
            Product product = findProduct(productList, purchasedProduct.getName());
            product.updateQuantity(product.getQuantity() - purchasedProduct.getQuantity());
            product.updatePromotionQuantity(product.getPromotionQuantity() - purchasedProduct.getPromotionQuantity());
        }
    }

    private Product findProduct(List<Product> productList, String name) {
        return productList.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> StoreException.from(ErrorMessage.NON_EXISTENT_PRODUCT));
    }
}
