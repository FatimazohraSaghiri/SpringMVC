package com.example.demo.service;

import com.example.demo.model.Beitrag;
import com.example.demo.model.Bewertung;
import com.example.demo.repository.BeitragRepository;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.BewertungRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Service
public class BewertungService {
    private final BeitragRepository beitragRepository;
    private final BenutzerRepository benutzerRepository;
    private final BewertungRepository bewertungRepository;

    // Beitrag bewerten
    public ResponseEntity<String> beitragBewerten(Long idBeitrag, Long idBenutzer, Bewertung bewertung) {
        Beitrag beitrag = beitragRepository.findById(idBeitrag).get();
        List<Bewertung> bewertungList = beitrag.getBewertung();
        boolean gefunden = false;
        for (Bewertung bewertung1 : bewertungList) {

            if (bewertung1.getBenutzer() != null && bewertung1.getBenutzer().equals(benutzerRepository.getById(idBenutzer))) {
                gefunden = true;
                break;
            }
        }
        // wenn man zum zweiten mal den Beitrag bewerten möchte
        if (gefunden) {
            return new ResponseEntity<String>("Sie haben schon diesen Beitrag bewertet ", HttpStatus.NOT_ACCEPTABLE);
        } else {
            Bewertung neubewertung = new Bewertung();
            neubewertung.setAnzahlStr(bewertung.getAnzahlStr());
            neubewertung.setDatum(LocalDateTime.now());
            neubewertung.setBeitrag(beitrag);
            neubewertung.setBenutzer(benutzerRepository.findById(idBenutzer).get());
            bewertungRepository.save(neubewertung);
            return new ResponseEntity<String>("Bewertung ist Erfolgreich ", HttpStatus.ACCEPTED);

        }
    }

    //Bewertung löschen
    public ResponseEntity<Bewertung> bewertungLoeschen(Long id) {
        Bewertung bewertung = bewertungRepository.findById(id).get();
        bewertungRepository.delete(bewertung);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}




