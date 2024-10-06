package com.example.jpademo.persistence.repositories;

import com.example.jpademo.persistence.entities.AddressEntity;
import com.example.jpademo.persistence.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findByPerson(PersonEntity person);

}
