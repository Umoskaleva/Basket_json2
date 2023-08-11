import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    static File saveFile = new File("basket.json");

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        XMLSettingsReader settingsReader = new XMLSettingsReader(new File("shop.xml"));
        File loadFile = new File(settingsReader.loadFile);
        File saveFile = new File(settingsReader.saveFile);
        File logFile = new File(settingsReader.logFile);


        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        Basket basket = new Basket();
        basket = creatBasket(loadFile, settingsReader.isLoad, settingsReader.loadFormat);
        if (saveFile.exists()) {
            basket = Basket.loadFromJsonFile(saveFile);
        } else {
            basket = new Basket(products, prices);
        }


        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт");
        }


        basket = new Basket(products, prices);
        basket.addToCart(1, 1);
        basket.addToCart(2, 1);
        basket.addToCart(3, 1);
        if (settingsReader.isSave) {
            switch (settingsReader.saveFormat) {
                case "json" -> basket.saveJson(saveFile);
                case "txt" -> basket.saveTxt(saveFile);
            }
        }
        basket.saveTxt(saveFile);
        basket.printCart();

        ClientLog clientLog = new ClientLog();
        clientLog.log(1, 1);
        clientLog.log(2, 1);
        clientLog.log(3, 1);
        basket.saveJson(saveFile);
        clientLog.exportAsCSV(new File("log.csv"));

    }

    private static Basket creatBasket(File loadFile, boolean isLoad, String loadFormat) {
        Basket basket = new Basket();
        if (isLoad && loadFile.exists()) {
            basket = switch (loadFormat) {
                case "json" -> Basket.loadFromJsonFile(loadFile);
                case "txt" -> Basket.loadFromTxtFile(loadFile);
                default -> new Basket(basket.getProducts(), basket.getPrices());
            };
        } else {
            basket = new Basket();
        }
        return basket;
    }
}
