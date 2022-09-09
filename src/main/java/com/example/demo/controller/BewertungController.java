package com.example.demo.controller;

import com.example.demo.model.Bewertung;
import com.example.demo.service.BewertungService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BewertungController {
    private final BewertungService bewertungService;

    @PostMapping("/bewertung/{idBeitrag}/{idBenutzer}")
    public ResponseEntity<String> addBewertung(@PathVariable Long idBeitrag, @PathVariable Long idBenutzer, @RequestBody Bewertung bewertung) {
        return bewertungService.beitragBewerten(idBeitrag, idBenutzer, bewertung);
    }

    @DeleteMapping("/bewertung/loeschen/{id}")
    public ResponseEntity<Bewertung> bewertungLoeschen(@PathVariable Long id) {
        return bewertungService.bewertungLoeschen(id);
    }
}
