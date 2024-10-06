package com.example.jpademo.persistence.repositories;

import com.example.jpademo.persistence.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    List<PersonEntity> findByNameIgnoreCase(String name);

}
