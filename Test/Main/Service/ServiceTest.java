package Main.Service;

import Main.Database.Category;
import Main.Database.Product;
import com.sun.source.tree.AssertTree;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ServiceTest {
    Service service;
    Product product;

    @Before
    public void setUp(){
        service = new Service();
        product = new Product("Carrot", new BigDecimal("0.30"), Category.VEGETABLE, new BigDecimal("0.01"), "");
        service.addProduct(product);
    }

    @Test
    public void addProduct() {
        Map<String, String> map = new HashMap<>();
        map.put("Name", "Apple");
        map.put("Price", "0.90");
        map.put("Discount", "0.01");
        map.put("Description", "");
        assertTrue(service.addProduct(Category.FRUIT, map));
    }

    @Test
    public void addProduct1() {
        Product product = new Product("Potato", new BigDecimal("0.60"), Category.VEGETABLE, new BigDecimal("0.01"), "");
        assertTrue(service.addProduct(product));
    }

    @Test
    public void findById() {
        service.findById(Long.parseLong("0"));
        assertEquals(service.findById(Long.parseLong("0")), product);
    }
}