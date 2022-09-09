package com.example.demo.model;

import com.example.demo.enums.KategorieEnum;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "beitrag")
public class Beitrag implements Serializable {
    private String titel;
    private String inhalt;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", insertable = false, updatable = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "benutzer_id", nullable = false)
    private Benutzer benutzer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "beitrag")
    private List<Kommentar> kommentar = new ArrayList<>();

    @Autowired
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "beitrag")
    private List<Bewertung> bewertung = new ArrayList<>();
    @Column
    @Enumerated(EnumType.STRING)
    private KategorieEnum kategorie;

}
