package main.service;

import main.database.Category;
import main.database.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DatabaseService {
    boolean addProduct(Category category, Map<String, String> productData);
    Product findById(Long id);
    boolean removeById(Long id);
    List<Product> getProductList();
    List<Product> getListByCategory(Category category);
    boolean changePrice(Long id, BigDecimal newPrice);
    boolean changeDiscount(Long id, BigDecimal newDiscount);
    boolean changeDescription(Long id, String newDescription);
    void setDiscountForCategory(Category category, BigDecimal newDiscount);

}
