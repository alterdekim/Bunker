package com.alterdekim.javabot.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinFormula;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "health")
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float health_index;

    @Column(nullable = false)
    private Long textNameId;

    @Column(nullable = false)
    private Long textDescId;

    @Column(nullable = false)
    private Boolean isChildfree;

    public Health(Float health_index, Long textNameId, Long textDescId, Boolean isChildfree) {
        this.health_index = health_index;
        this.textNameId = textNameId;
        this.textDescId = textDescId;
        this.isChildfree = isChildfree;
    }
}
