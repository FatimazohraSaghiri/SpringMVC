package com.example.demo.service;

import com.example.demo.enums.ProfessionEnum;
import com.example.demo.model.Beitrag;
import com.example.demo.model.Benutzer;
import com.example.demo.model.Kommentar;
import com.example.demo.repository.BenutzerRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Service
public class BenutzerService {
    private final BenutzerRepository benutzerRepository;

    // Benutzer registration
    public ResponseEntity<Benutzer> registerBenutzer(Benutzer benutzer) {
        benutzer.setEnabled(true);
        this.benutzerRepository.save(benutzer);
        // nach erfolgreiche Registrierung wird Response als Created ausgegeben
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Benutzer anmelden
    public ResponseEntity<String> anmelden(Benutzer benutzer) {
        Benutzer Newbenutzer = benutzerRepository.findByAdresse(benutzer.getAdresse());
        if (Newbenutzer == null) {
            return new ResponseEntity<String>("diese Email existiert nicht", HttpStatus.NOT_FOUND);
        }
        //hier wird überprüft,ob die Adresse oder dass Passwort  falsch sind
        else if (!Newbenutzer.getPasswort().equals(benutzer.getPasswort())) {
            return new ResponseEntity<String>("Passwort ist falsch ", HttpStatus.NOT_ACCEPTABLE);
        }
        // wenn die Adresse und Passwort richtig sind
        return new ResponseEntity<String>("Sie sind erfolgreich angemeldet", HttpStatus.OK);
    }

    // Profil von Benutzer bearbeiten
    public ResponseEntity<Benutzer> updateBenutzer(Benutzer benutzer, Long id) {
        Benutzer Newbenutzer = this.benutzerRepository.findById(id).get();
        if (benutzer.getNachname() != null && !benutzer.getVorname().isBlank()) {
            Newbenutzer.setNachname(benutzer.getNachname());
        }
        if (benutzer.getVorname() != null && !benutzer.getVorname().isBlank()) {
            Newbenutzer.setVorname(benutzer.getVorname());
        }
        if (benutzer.getPasswort() != null && !benutzer.getPasswort().isBlank()) {
            Newbenutzer.setPasswort(benutzer.getPasswort());
        }
        if (benutzer.getAdresse() != null && !benutzer.getAdresse().isBlank()) {
            Newbenutzer.setAdresse(benutzer.getAdresse());
        }
        if (benutzer.getBeschreibung() != null && !benutzer.getBeschreibung().isBlank()) {
            Newbenutzer.setBeschreibung(benutzer.getBeschreibung());
        }
        if (benutzer.getProfessionEnum() != null && !benutzer.getProfessionEnum().name().isBlank()) {
            Newbenutzer.setProfessionEnum(benutzer.getProfessionEnum());
        }
        this.benutzerRepository.save(Newbenutzer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Output für alle Daten von Benutzer
    public Benutzer getBenutzer(String email) {
        Benutzer benutzer = benutzerRepository.findByAdresse(email);
        Benutzer neubenutzer = new Benutzer();
        neubenutzer.setId(benutzer.getId());
        neubenutzer.setNachname(benutzer.getNachname());
        neubenutzer.setVorname(benutzer.getVorname());
        neubenutzer.setAdresse(benutzer.getAdresse());
        neubenutzer.setPasswort(benutzer.getPasswort());
        neubenutzer.setBeschreibung(benutzer.getBeschreibung());
        neubenutzer.setProfessionEnum((benutzer.getProfessionEnum()));
        List<Kommentar> kommentarList = benutzer.getKommentars().stream().map(kommentar ->
                Kommentar.builder()
                        .id(kommentar.getId())
                        .inhalt(kommentar.getInhalt())
                        .erstellt_an(kommentar.getErstellt_an())
                        .build()
        ).collect(Collectors.toList());
        neubenutzer.setKommentars(kommentarList);
        List<Beitrag> beitragList = benutzer.getBeitraege().stream().map(beitrag ->
                Beitrag.builder()
                        .id(beitrag.getId())
                        .inhalt(beitrag.getInhalt())
                        .kategorie(beitrag.getKategorie())
                        .titel(beitrag.getTitel())
                        .build()
        ).collect(Collectors.toList());
        neubenutzer.setBeitraege(beitragList);
        return neubenutzer;
    }

    //Kategorie User
    public List<ProfessionEnum> professionEnumList() {
        List<ProfessionEnum> professionEnums = new ArrayList<>();
        for (ProfessionEnum professionEnum : ProfessionEnum.values()) {
            professionEnums.add(professionEnum);
        }
        return professionEnums;
    }

    //Benutzer sperren
    public ResponseEntity<String> benutzersperren(long id) {
        Benutzer benutzer = benutzerRepository.findById(id).get();
        if (benutzer.isEnabled()) {
            benutzer.setEnabled(false);
            return new ResponseEntity<>("benutzer ist erfolgreich gesperrt", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("benutzer ist schon gesperrt", HttpStatus.NOT_ACCEPTABLE);
        }

    }
}
