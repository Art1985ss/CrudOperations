package main.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    List<Product> productSet;

    public ProductDatabase() {
        productSet = new ArrayList<>();
    }

    public boolean add(Product product){
        if (product == null) return false;
        return productSet.add(product);
    }

    public String[][] getTableData(){
        if (productSet.isEmpty()) return null;
        String[][] data = new String[productSet.size()][7];
        for (int i =0; i < productSet.size(); i++){
            data[i][0] = productSet.get(i).getName();
            data[i][1] = productSet.get(i).getId().toString();
            data[i][2] = String.format("%.2f", productSet.get(i).getPrice());
            data[i][3] = productSet.get(i).getCategory().toString();
            data[i][4] = String.format("%.0f", productSet.get(i).getDiscount().multiply(new BigDecimal(100)));
            data[i][5] = String.format("%.2f", productSet.get(i).getActualPrice());
            data[i][6] = productSet.get(i).getDescription();
        }
        return data;
    }
    public Product getById(Long id){
        for (Product product:productSet) {
            if (product.getId() == id){
                return product;
            }
        }
        return null;
    }
    public boolean removeById(Long id){
        return productSet.remove(getById(id));
    }

    public List<Product> getProductList(){
        return productSet;
    }

    public List<Product> getListByCategory(Category category){
        List<Product> listByCategory = new ArrayList<>();
        for (Product product:productSet) {
            if (product.getCategory() == category){
                listByCategory.add(product);
            }
        }
        return listByCategory;
    }
    public boolean changePrice(Long id, BigDecimal newPrice){
        if (getById(id) != null) {
            getById(id).setPrice(newPrice);
            return true;
        }
        return false;
    }
    public boolean changeDiscount(Long id, BigDecimal newDiscount){
        if (getById(id) != null) {
            getById(id).setDiscount(newDiscount);
            return true;
        }
        return false;
    }
    public boolean changeDescription(Long id, String newDescription){
        if (getById(id) != null) {
            getById(id).setDescription(newDescription);
            return true;
        }
        return false;
    }

    public void setDiscountForCategory(Category category, BigDecimal newDiscount){
        for (Product product:getListByCategory(category)) {
            product.setDiscount(newDiscount);
        }
    }


    @Override
    public String toString() {
        String text = "";
        for (Product product:productSet) {
            text += product + "\n";
        }
        return text;
    }
}
