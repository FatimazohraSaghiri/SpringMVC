package com.example.demo.controller;

import com.example.demo.enums.ProfessionEnum;
import com.example.demo.model.Benutzer;
import com.example.demo.service.BenutzerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BenutzerController {

    private final BenutzerService benutzerService;

    @PostMapping("/registerbenutzer")
    public ResponseEntity<Benutzer> registerBenutzer(@RequestBody Benutzer benutzer) {
        return benutzerService.registerBenutzer(benutzer);
    }

    @PutMapping("/benutzer/{idBenutzer}")
    public ResponseEntity<Benutzer> updateBenutzer(@PathVariable long idBenutzer, @RequestBody Benutzer benutzer) {
        return benutzerService.updateBenutzer(benutzer, idBenutzer);
    }

    @PostMapping("/anmelden")
    public ResponseEntity<String> anmelden(@RequestBody Benutzer benutzer) {
        return benutzerService.anmelden(benutzer);
    }

    @GetMapping("/{email}")
    public Benutzer getBenutzer(@PathVariable String email) {
        return benutzerService.getBenutzer(email);
    }

    @GetMapping("/profession")
    public List<ProfessionEnum> professionEnumList() {
        return benutzerService.professionEnumList();
    }

    @GetMapping("/sperren/{id}")
    public ResponseEntity<String> benutzersperren(@PathVariable Long id) {
        return benutzerService.benutzersperren(id);
    }

}
