package com.example.jpademo.service.impl;

import com.example.jpademo.service.PersonService;
import com.example.jpademo.service.dtos.PersonDto;
import com.example.jpademo.persistence.entities.PersonEntity;
import com.example.jpademo.persistence.repositories.PersonRepository;
import com.example.jpademo.service.mapper.PersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Transactional
    @Override
    public void savePerson(PersonDto personDto) {
        PersonEntity entity = PersonEntity.builder()
                .name(personDto.getName())
                .email(personDto.getEmail())
                .age(personDto.getAge())
                .build();
        personRepository.save(entity);
        log.info("createdPerson: {}", entity);
    }

    @Override
    public List<PersonDto> getAllPersons() {
        return personMapper.mapToDto(personRepository.findAll());
    }

    @Override
    public List<PersonDto> getPersonByName(String name) {
        return personMapper.mapToDto(personRepository.findByNameIgnoreCase(name));
    }
}
