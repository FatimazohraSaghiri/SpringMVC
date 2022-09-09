package com.example.demo.repository;

import com.example.demo.model.Kommentar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KommentarRepository extends JpaRepository<Kommentar, Long> {
}

