package Main.Database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.*;

public class ProductTest {
    Product product;

    @Before
    public void setUp() throws Exception {
        product = new Product("Apple", new BigDecimal(10), Category.FRUIT, new BigDecimal("0.01"), "");
    }

    @Test
    public void getActualPrice() {
        BigDecimal actualPrice = product.getActualPrice();
        assertEquals(new BigDecimal("9.90"), actualPrice.round(MathContext.DECIMAL32));
    }
}