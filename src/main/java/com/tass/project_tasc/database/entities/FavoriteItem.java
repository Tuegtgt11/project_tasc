package com.tass.project_tasc.database.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table( name = "favorite_item")
public class FavoriteItem {
    @EmbeddedId
    private FavoriteItemId favoriteItemId;
    @ManyToOne
    @JoinColumn(name = "favoriteId", referencedColumnName = "id")
    @JsonBackReference
    private Favorite favorite;
}
