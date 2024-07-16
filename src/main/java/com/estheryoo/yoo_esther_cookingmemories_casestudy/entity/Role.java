package com.estheryoo.yoo_esther_cookingmemories_casestudy.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
