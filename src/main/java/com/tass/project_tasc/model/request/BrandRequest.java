package com.tass.project_tasc.model.request;

import com.tass.project_tasc.database.entities.myenums.BrandStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BrandRequest {
    private String name;
    @Column(columnDefinition = "text")
    private String image;
    private BrandStatus status;
}
