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

    private final Receipt receipt;
    private final InputView inputView;
    private final OutputView outputView;
    private final StockUpdater stockUpdater;
    private final MemberShipDiscountHandler memberShipDiscountHandler;
    private final List<Product> productList;

    public StoreController(Receipt receipt, InputView inputView, OutputView outputView, StockUpdater stockUpdater,
                           MemberShipDiscountHandler memberShipDiscountHandler) {
        this.receipt = receipt;
        this.inputView = inputView;
        this.outputView = outputView;
        this.stockUpdater = stockUpdater;
        this.memberShipDiscountHandler = memberShipDiscountHandler;
        this.productList = inputView.readProductList();
    }

    public void run() {
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
        outputView.membershipDiscountMessage();
        int memberShipDiscount = memberShipDiscountHandler.apply(purchasedProductList, inputView.readCommand());

        receipt.printReceipt(purchasedProductList, memberShipDiscount);
    }
}
