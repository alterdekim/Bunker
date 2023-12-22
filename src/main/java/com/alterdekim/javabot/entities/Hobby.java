package com.alterdekim.javabot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinFormula;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hobby")
public class Hobby {

    public Hobby(Float foodstuffs, Float power, Float violence, Float asocial, Long textDescId) {
        this.foodstuffs = foodstuffs;
        this.power = power;
        this.violence = violence;
        this.asocial = asocial;
        this.textDescId = textDescId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float asocial;

    @Column(nullable=false)
    private Float power;

    @Column(nullable=false)
    private Float violence;

    @Column(nullable=false)
    private Float foodstuffs;

    @Column(nullable = false)
    private Long textDescId;
}
