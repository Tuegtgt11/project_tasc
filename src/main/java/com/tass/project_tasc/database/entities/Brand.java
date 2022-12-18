package com.tass.project_tasc.database.entities;

import javax.persistence.*;

import com.tass.project_tasc.database.entities.base.BaseEntity;
import com.tass.project_tasc.database.entities.myenums.BrandStatus;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brands")
public class Brand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String image;
    @Enumerated(EnumType.ORDINAL)
    private BrandStatus status;
}
