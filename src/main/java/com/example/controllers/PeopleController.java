package com.example.controllers;

import com.example.dao.PersonDao;
import com.example.models.Item;
import com.example.models.Person;
import javax.validation.Valid;

import com.example.services.ItemService;
import com.example.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao; // только для hibernate
    private final PeopleService peopleService;
    private final ItemService itemService;

//    public PeopleController(PersonDao personDao) {
//        this.personDao = personDao;
//    }

    @Autowired
    public PeopleController(final PeopleService peopleService, final ItemService itemService, PersonDao personDao) {
        this.peopleService = peopleService;
        this.itemService = itemService;
        this.personDao = personDao;
    }

//    @GetMapping()
//    public String index(Model model) {
//        model.addAttribute("people", personDao.index());
//        return "people/index";
//    }

        @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        List<Item> items = itemService.findByItemName("airBook");
        itemService.findByOwner(peopleService.findAll().get(3));
        peopleService.test();
        personDao.testNPlus1();
        return "people/index";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//model.addAttribute("person", personDao.show(id));
//        return "people/show";
//    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/show";
    }

//    @GetMapping("/new")
//    public String newPerson(Model model) {
//        model.addAttribute("person", new Person());
//        return "people/new";
//    }

    //можно положить так
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

//    @PostMapping()
//    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "people/new";
//        }
//            personDao.save(person);
//            return "redirect:/people";
//    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("person", personDao.show(id));
//        return "people/edit";
//    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
//        if (bindingResult.hasErrors()) {
//            return "people/edit";
//        }
//            personDao.update(id, person);
//            return "redirect:/people";
//    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        personDao.delete(id);
//        return "redirect:/people";
//    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
