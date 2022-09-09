package com.example.demo.model;

import com.example.demo.enums.ProfessionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "benutzer", uniqueConstraints = @UniqueConstraint(columnNames = "adresse"))
public class Benutzer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "benutzer_id", insertable = false, updatable = true)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "benutzer")
    @JsonIgnore
    private List<Beitrag> beitraege = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "benutzer")
    @JsonIgnore
    private List<Kommentar> kommentars = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "benutzer")
    @JsonIgnore
    private List<Bewertung> bewertungs = new ArrayList<>();
    private String vorname;

    private String nachname;

    @Column(nullable = false)
    private String passwort;

    @Column(nullable = false, unique = true)
    private String adresse;

    private String beschreibung;

    @Column
    @Enumerated(EnumType.STRING)
    @NonNull
    private ProfessionEnum professionEnum;
    private boolean enabled;
}



