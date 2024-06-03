package com.example.dao;

import com.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {


   private final EntityManager entityManager;

   @Autowired
    public PersonDao(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private SessionFactory sessionFactory;

//    @Autowired // это нужно для hibernate, для JPA нужен JpaTransactionManager
//    public PersonDao(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();
        return people;
    }

    @Transactional(readOnly = true)
    public Person show(final int id) {
        final Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        final Session session = sessionFactory.getCurrentSession();
        final Person person = session.get(Person.class, id);
        person.setName(updatedPerson.getName());
        person.setAge(updatedPerson.getAge());
        session.update(person);
    }

    @Transactional
    public void delete(int id) {
        final Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Person.class, id));
    }
    @Transactional(readOnly = true)
    public void testNPlus1() {
        List<Person> people;
        try (Session session = entityManager.unwrap(Session.class)) {
//        //1 запрос
//        List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();
//        // N запросов к БД
//        for(Person person : people) {
//            System.out.println("Person" + person.getName() + " has: " + person.getItems());
//        }
            //Solution
            people = session
                    .createQuery("select distinct p from Person p left join fetch p.items", Person.class)
                    .getResultList();
        }
        for(final Person person : people) {
            System.out.println("Person" + person.getName() + " has: " + person.getItems());
        }
    }

}
