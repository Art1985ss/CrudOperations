package Database;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Product {
    private static final AtomicLong count = new AtomicLong(-1);
    private String name;
    private Long id;
    private BigDecimal price;
    private Category category;

    private BigDecimal discount;
    private String description;

    public Product(String name, BigDecimal price, Category category, BigDecimal discount, String description) {
        this.name = name;
        this.id = count.incrementAndGet();
        this.price = price;
        this.category = category;
        this.discount = discount;
        this.description = description;
    }

    public static AtomicLong getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId(){
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getActualPrice(){
        return price.subtract(price.multiply(discount));
    }

    @Override
    public String toString() {
        return String.format("Product {name = %s, id = %d, price = %.2f, category = %s, discount = %.0f%%, actual price = %.2f}",name, id, price, category, discount.multiply(new BigDecimal(100)), getActualPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) &&
                id.equals(product.id) &&
                price.equals(product.price) &&
                category == product.category &&
                discount.equals(product.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, price, category, discount);
    }
}
