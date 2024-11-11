package store.model;

import java.util.ArrayList;
import java.util.List;

public class ProductPromotionParser {

    private final List<Product> products = new ArrayList<>();

    public List<Promotion> parsePromotions(List<String> promotionLines) {
        return promotionLines.stream()
                .map(line -> List.of(line.split(",")))
                .map(Promotion::from)
                .toList();
    }

    public List<Product> parseProducts(List<String> productLines, List<Promotion> promotions) {
        productLines.stream()
                .map(line -> List.of(line.split(",")))
                .forEach(values -> processProduct(values, promotions));

        return products;
    }

    private void processProduct(List<String> values, List<Promotion> promotions) {
        Promotion promotion = findPromotion(promotions, values.get(3));
        Product product = findProduct(values.get(0));

        if (promotion == null) {
            addOrUpdateProduct(product, values);
            return;
        }

        products.add(Product.of(values, promotion));
    }

    private Promotion findPromotion(List<Promotion> promotions, String promotionName) {
        return promotions.stream()
                .filter(p -> p.getName().equals(promotionName))
                .findFirst()
                .orElse(null);
    }

    private Product findProduct(String productName) {
        return products.stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    private void addOrUpdateProduct(Product product, List<String> values) {
        if (product == null) {
            products.add(Product.from(values));
            return;
        }

        product.updateQuantity(Integer.parseInt(values.get(2)));
    }
}
