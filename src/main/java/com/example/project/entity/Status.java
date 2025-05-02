package com.example.project.entity;

import com.example.project.enumName.StatusName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusName statusName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Status(StatusName statusName) {
        this.statusName = statusName;
    }
}
