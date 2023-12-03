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
@Table(name = "bio")
public class Bio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Bio(Boolean isMale, Boolean isFemale, Boolean isChildfree, Boolean canDie, Long genderTextId) {
        this.isMale = isMale;
        this.isFemale = isFemale;
        this.isChildfree = isChildfree;
        this.canDie = canDie;
        this.genderTextId = genderTextId;
    }

    @Column(nullable = false)
    private Boolean isMale;

    @Column(nullable = false)
    private Boolean isFemale;

    @Column(nullable = false)
    private Boolean isChildfree;

    @Column(nullable = false)
    private Boolean canDie;

    @Column(nullable = false)
    private Long genderTextId;
}
