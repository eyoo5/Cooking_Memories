package com.estheryoo.yoo_esther_cookingmemories_casestudy.entity;


import jakarta.persistence.*;
import lombok.*;


/*
Role table has a 1:1 relationship with User
It has 3 roles: Admin, User, and With_Link
 */

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="role_name")
    private String name;


    public Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
