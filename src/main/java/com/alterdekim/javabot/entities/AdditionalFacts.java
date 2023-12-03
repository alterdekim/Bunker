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
@Table(name = "addfacts")
public class AdditionalFacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private Float violence;

    @Column(nullable=false)
    private Float power;

    @Column(nullable=false)
    private Float food;

    @Column(nullable=false)
    private Float heal;

    @Column(nullable = false)
    private Float health_index;

    @Column(nullable = false)
    private Long textDescId;
}
