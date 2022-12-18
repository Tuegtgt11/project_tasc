package com.tass.project_tasc.database.entities;

import javax.persistence.*;

import com.tass.project_tasc.database.entities.base.BaseEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table( name = "size")
public class Size extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String weight;
}
