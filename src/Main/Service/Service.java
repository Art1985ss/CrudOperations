package Main.Service;

import Main.Database.Category;
import Main.Database.Product;
import Main.Database.ProductDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Service implements DatabaseService{
    private ProductDatabase productDatabase;

    public Service(ProductDatabase productDatabase) {
        this.productDatabase = productDatabase;
    }
    public Service(){
        this.productDatabase = new ProductDatabase();
    }

    public boolean addProduct(Category category, Map<String, String> productData){
        if (category == null){
            showWarning("Select valid category for product!");
            return false;
        }
        for (Map.Entry<String,String> data: productData.entrySet()) {
            if (data.getValue() == null) {
                if (data.getKey() != "Discount" && data.getKey() != "Description") {
                    showWarning("Enter " + data.getKey() + " for product!");
                } else if (data.getKey() == "Discount") {
                    data.setValue((new Integer(0).toString()));
                } else
                    data.setValue("");
            }
        }
        Product product = null;
        try {
            product = new Product(
                    productData.get("Name"),
                    new BigDecimal(productData.get("Price")),
                    category,
                    new BigDecimal(productData.get("Discount")),
                    productData.get("Description"));
        }catch (NumberFormatException e){
            showWarning("Please check data input!");
            return false;
        }
        System.out.println(product);
        return productDatabase.add(product);

    }
    public boolean addProduct(Product product){
        return productDatabase.add(product);
    }

    @Override
    public Product findById(Long id) {
        return productDatabase.getById(id);
    }

    @Override
    public boolean removeById(Long id) {
        return productDatabase.removeById(id);
    }

    @Override
    public List<Product> getProductList() {
        return productDatabase.getProductList();
    }

    @Override
    public List<Product> getListByCategory(Category category) {
        return productDatabase.getListByCategory(category);
    }

    @Override
    public boolean changePrice(Long id, BigDecimal newPrice) {
        return productDatabase.changePrice(id,newPrice);
    }

    @Override
    public boolean changeDiscount(Long id, BigDecimal newDiscount) {
        return productDatabase.changeDiscount(id, newDiscount);
    }

    @Override
    public boolean changeDescription(Long id, String newDescription) {
        return productDatabase.changeDescription(id, newDescription);
    }

    @Override
    public void setDiscountForCategory(Category category, BigDecimal newDiscount) {
        productDatabase.setDiscountForCategory(category,newDiscount);
    }

    public void showWarning(String message){
        JFrame frame = new JFrame("Main.Service error!");
        frame.setLayout(new BorderLayout());
        frame.add(new Label(message),BorderLayout.PAGE_START);
        JButton btn = new JButton("Ok");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });
        frame.add(btn, BorderLayout.PAGE_END);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void populateProductList(){
        Product product = null;
        product = new Product("Apple", new BigDecimal("1"), Category.FRUIT, new BigDecimal("0.01"),"");
        this.addProduct(product);
        product = new Product("Watermelon", new BigDecimal("1.30"), Category.FRUIT, new BigDecimal("0.05"), "");
        this.addProduct(product);
        product = new Product("Potato", new BigDecimal("0.45"), Category.VEGETABLE, new BigDecimal("0.03"), "");
        this.addProduct(product);
        product = new Product("Carrot", new BigDecimal("0.3"), Category.VEGETABLE, new BigDecimal("0.1"), "");
        this.addProduct(product);

    }

    public void populateProductList(int count){
        String[] fruits = {"Apple", "Apricot", "Avocado", "Banana", "Blackcurrant", "Blackberry", "Blueberry", "Cherry", "Coconut", "Grape", "Kiwi", "Lemon", "Lime", "Mango", "Nectarine", "Orange", "Strawberry"};
        String[] vegetables = {"Corn", "Broccoli", "Cucumber", "Capsicum", "Brussels sprouts", "Carrots", "Tomatoes", "Pumpkin", "Cabbage", "Potatoes", "Eggplant", "Lettuce", "Onions", "Green chilies", "Courgette"};
        String[] mushrooms = {"Russula", "Aspen", "Greasers", "Saffron", "Gypsy", "Champignon", "Truffle"};
        String[] meat = {"Pork", "Beef", "Lamb", "Chicken", "Turkey", "Venison", "Duck", "Wild boar", "Bison", "Goose", "Rabbit", "Pheasant"};
        String[] grain = {"Barley", "Brown rice", "Buckwheat", "Bulgur", "Millet", "Oatmeal", "Popcorn"};

        Map<Category, String[]> productMap = new HashMap<>();
        productMap.put(Category.FRUIT, fruits);
        productMap.put(Category.VEGETABLE, vegetables);
        productMap.put(Category.MUSHROOM, mushrooms);
        productMap.put(Category.MEAT, meat);
        productMap.put(Category.GRAIN, grain);
        Random random = new Random();
        for (int i = 0; i < count ; i++) {
            int randomNumber = random.nextInt(Category.values().length);
            Category category = Category.values()[randomNumber];
            String name = productMap.get(category)[random.nextInt(productMap.get(category).length)];
            Product product = new Product(
                    name,
                    new BigDecimal(random.nextInt(999) * 0.01),
                    category,
                    new BigDecimal(random.nextFloat() * 0.5),
                    ""
            );
            this.addProduct(product);
        }
    }


}
