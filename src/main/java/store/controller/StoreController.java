package store.controller;

import java.util.List;
import store.model.Cashier;
import store.model.MemberShipDiscountHandler;
import store.model.Product;
import store.model.PurchasedProduct;
import store.model.ShoppingCartProduct;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final MemberShipDiscountHandler memberShipDiscountHandler = new MemberShipDiscountHandler();
    private final List<Product> productList = inputView.readProductList();

    public void start() {
        outputView.intro();
        outputView.products(productList);

        handlePayment();
    }

    private void handlePayment() {
        while (true) {
            try {
                processPayment();
                return;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e);
            }
        }
    }

    private void processPayment() {
        List<ShoppingCartProduct> shoppingCart = getShoppingCart();
        Cashier cashier = Cashier.from(productList);
        List<PurchasedProduct> purchasedProductList = cashier.requestPayment(shoppingCart);
        int memberShipDiscount = memberShipDiscountHandler.apply(purchasedProductList);
    }

    private List<ShoppingCartProduct> getShoppingCart() {
        outputView.buyProductMessage();
        return ShoppingCartProduct.createList(inputView.readShoppingCart().rawShoppingCart());
    }
}
