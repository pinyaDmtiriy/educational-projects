package com.example.project.entity;

import com.example.project.enumName.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleName roleName;

    @ManyToMany(mappedBy = "")
    private Set<User> users = new HashSet<>();

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }
}
