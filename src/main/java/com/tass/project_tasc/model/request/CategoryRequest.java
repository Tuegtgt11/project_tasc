package com.tass.project_tasc.model.request;

import com.tass.project_tasc.database.entities.myenums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotEmpty(message = "name missing!")
    private String name;
    @NotEmpty(message = "icon missing")
    @Column(columnDefinition = "text")
    private String icon;
    @NotEmpty(message = "status missing")
    private CategoryStatus status;
}
