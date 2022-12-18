package com.tass.project_tasc.model.dto;

import com.tass.project_tasc.database.entities.Brand;
import com.tass.project_tasc.database.entities.Category;
import com.tass.project_tasc.database.entities.Product;
import com.tass.project_tasc.database.entities.Size;
import com.tass.project_tasc.database.entities.myenums.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductInfo {
    private String name;
    private String description;

    private String barcode;
    private String images;
    private String detail;
    private int sold;
    private Size size;
    private int quantity;
    private BigDecimal price;
    private ProductStatus status;
    private Brand brand;
    private Category category;

    public ProductInfo(Product entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.barcode = entity.getBarcode();
        this.images = entity.getImages();
        this.detail = entity.getDetail();
        this.sold = entity.getSold();
        this.size = entity.getSize();
        this.quantity = entity.getQuantity();
        this.price = entity.getPrice();
        this.status = entity.getStatus();
        this.brand = entity.getBrand();
        this.category = entity.getCategory();
    }
}
