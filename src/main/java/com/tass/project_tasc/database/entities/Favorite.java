package com.tass.project_tasc.database.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "favorite", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<FavoriteItem> items;
}
