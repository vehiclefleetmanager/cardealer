package com.example.cardealer.controller;

import com.example.cardealer.service.CarService;
import com.example.cardealer.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {


    private final CarService carService;
    private final EventService eventService;

    @Autowired
    public IndexController(CarService carService, EventService eventService) {
        this.carService = carService;
        this.eventService = eventService;
    }

    @RequestMapping
    public String showCars(Model model) {
        model.addAttribute("cars", carService.findAll());
        return "index";
    }
    /*Kontroler którego zadaniem jest porzedstawienie oferty komisu na stronie głownej*/


}