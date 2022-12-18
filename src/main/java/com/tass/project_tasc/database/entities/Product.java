package com.tass.project_tasc.database.entities;

import com.tass.project_tasc.database.entities.base.BaseEntity;
import com.tass.project_tasc.database.entities.myenums.ProductStatus;
import lombok.*;

import javax.persistence.*;


import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String barcode;
    @Column(columnDefinition = "text")
    private String description;
    private String images;
    @Column(columnDefinition = "text")
    private String detail;
    private int sold;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private Size size;

    private int quantity;
    private BigDecimal price;
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;


}
