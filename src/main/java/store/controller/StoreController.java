package store.controller;

import java.util.List;
import store.model.Cashier;
import store.model.MemberShipDiscountHandler;
import store.model.Product;
import store.model.PurchasedProduct;
import store.model.Receipt;
import store.model.ShoppingCartProduct;
import store.model.StockUpdater;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final Receipt receipt = new Receipt();
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final StockUpdater stockUpdater = new StockUpdater();
    private final MemberShipDiscountHandler memberShipDiscountHandler = new MemberShipDiscountHandler();
    private final List<Product> productList = inputView.readProductList();

    public void start() {
        String command = Command.YES.getValue();

        while (command.equals(Command.YES.getValue())) {
            outputView.intro();
            outputView.products(productList);

            handlePayment();

            outputView.anotherProductMessage();
            command = inputView.readCommand();
        }
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

        processReceipt(purchasedProductList);
        stockUpdater.updateStock(purchasedProductList, productList);
    }

    private List<ShoppingCartProduct> getShoppingCart() {
        outputView.buyProductMessage();
        return ShoppingCartProduct.createList(inputView.readShoppingCart().rawShoppingCart());
    }

    private void processReceipt(List<PurchasedProduct> purchasedProductList) {
        int memberShipDiscount = memberShipDiscountHandler.apply(purchasedProductList);

        receipt.printReceipt(purchasedProductList, memberShipDiscount);
    }
}
