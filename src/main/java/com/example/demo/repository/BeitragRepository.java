package com.example.demo.repository;

import com.example.demo.model.Beitrag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeitragRepository extends JpaRepository<Beitrag, Long> {
}
