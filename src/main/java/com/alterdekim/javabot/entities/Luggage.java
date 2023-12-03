package com.alterdekim.javabot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "luggage")
public class Luggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Luggage(Float violence, Float power, Float heal, Float food, Boolean garbage, Long textNameId, Long textDescId) {
        this.violence = violence;
        this.power = power;
        this.heal = heal;
        this.food = food;
        this.garbage = garbage;
        this.textNameId = textNameId;
        this.textDescId = textDescId;
    }

    @Column(nullable=false)
    private Float violence;

    @Column(nullable=false)
    private Float power;

    @Column(nullable=false)
    private Float heal;

    @Column(nullable=false)
    private Float food;

    @Column(nullable=false)
    private Boolean garbage;

    @Column(nullable = false)
    private Long textNameId;

    @Column(nullable = false)
    private Long textDescId;
}
