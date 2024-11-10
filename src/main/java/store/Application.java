package store;

import camp.nextstep.edu.missionutils.Console;
import store.controller.StoreController;

public class Application {

    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        storeController.start();

        Console.close();
    }
}
