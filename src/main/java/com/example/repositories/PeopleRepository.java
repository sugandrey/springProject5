package com.example.repositories;

import com.example.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    List<Person> findByName(String name);

    List<Person> findByNameOrderByAge(String name);
    List<Person> findByNameStartingWith(String startingWith);
    List<Person> findByNameOrAge(String name, int age);
}
