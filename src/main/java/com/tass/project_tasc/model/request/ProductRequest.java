package com.tass.project_tasc.model.request;

import com.tass.project_tasc.database.entities.Brand;
import com.tass.project_tasc.database.entities.Category;
import com.tass.project_tasc.database.entities.Size;
import com.tass.project_tasc.database.entities.myenums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotEmpty(message = "ProductName missing!")
    private String name;
    @Column(columnDefinition = "text")
    @NotEmpty(message = "description missing!")
    private String description;
    @NotEmpty(message = "barcode missing!")
    private String barcode;
    @NotEmpty(message = "images missing!")
    private String images;
    @Column(columnDefinition = "text")
    @NotEmpty(message = "detail missing!")
    private String detail;
    @NotEmpty(message = "sold missing!")
    private int sold;
    @NotEmpty(message = "sizes missing!")
    private Size size;
    @NotEmpty(message = "quantity missing!")
    private int quantity;
    @NotEmpty(message = "price missing!")
    private BigDecimal price;
    private ProductStatus status;
    @NotEmpty(message = "brand missing!")
    private Brand brand;
    @NotEmpty(message = "category missing!")
    private Category category;
}
