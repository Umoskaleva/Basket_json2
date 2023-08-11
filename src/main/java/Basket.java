import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable{
    private static final long serialVersionUID = 1L;
    private String[] products;
    private int[] prices;
    private int productNum;
    private int amount;
    private int[] quantity;


    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.quantity = new int[products.length];
    }

    public Basket() {
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

    public void setPrices(int[] prices) {
        this.prices = prices;
    }

    public void setProducts(String[] products) {
        this.products = products;
    }


    public void addToCart(int productNum, int amount) {
        quantity[productNum - 1] += amount;
    }

    public void printCart() {
        int sum = 0;
        System.out.println();
        System.out.println("Список покупок: ");
        for (int i = 0; i < products.length; i++) {
            if (quantity[i] > 0) {
                System.out.println(products[i] + " " +
                        prices[i] + "руб/шт " +
                        quantity[i] + "шт " + " = " +
                        (prices[i] * quantity[i]) + "руб");
                int sumProducts = quantity[i] * prices[i];
                sum += sumProducts;
            }
        }
        System.out.println("Итого корзина: " + sum);
    }

    public void saveTxt(File textFile) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : products) {
                out.print(product + " ");
            }
            out.println();

            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();

            for (int q : quantity) {
                out.print(q + " ");
            }
            out.println();
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String productsStr = bufferedReader.readLine();
            String pricesStr = bufferedReader.readLine();
            String quantityStr = bufferedReader.readLine();

            basket.products = productsStr.split(" ");
            basket.prices = Arrays.stream(pricesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            basket.quantity = Arrays.stream(quantityStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public void saveBin(File file) { //сохранение файла корзины в бинарном коде
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket = new Basket();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public void saveJson(File file) { //сохранение файла корзины в json
        try (PrintWriter writer = new PrintWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); // построчное разделение текста в json
            String json = gson.toJson(this);
            writer.println(json);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromJsonFile(File file) {
        Basket basket = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(stringBuffer.toString(),Basket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }


}



