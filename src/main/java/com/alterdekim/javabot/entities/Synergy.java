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
@Table(name = "synergy")
public class Synergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long firstEntityId;

    @Enumerated(EnumType.ORDINAL)
    private ColumnType firstType;

    @Column(nullable = false)
    private Long secondEntityId;

    @Enumerated(EnumType.ORDINAL)
    private ColumnType secondType;

    @Column(nullable = false)
    private Float probabilityValue;

    public Synergy(Long firstEntityId, ColumnType firstType, Long secondEntityId, ColumnType secondType, Float probabilityValue) {
        this.firstEntityId = firstEntityId;
        this.firstType = firstType;
        this.secondEntityId = secondEntityId;
        this.secondType = secondType;
        this.probabilityValue = probabilityValue;
    }
}
