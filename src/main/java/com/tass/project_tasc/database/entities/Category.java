package com.tass.project_tasc.database.entities;

import com.tass.project_tasc.database.entities.base.BaseEntity;
import com.tass.project_tasc.database.entities.myenums.CategoryStatus;
import javax.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String icon;
    @Enumerated(EnumType.ORDINAL)
    private CategoryStatus status;
}
