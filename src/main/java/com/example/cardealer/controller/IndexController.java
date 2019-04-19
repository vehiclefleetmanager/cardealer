package com.example.cardealer.controller;

import com.example.cardealer.model.dtos.CarDto;
import com.example.cardealer.model.Car;
import com.example.cardealer.model.enums.Transaction;
import com.example.cardealer.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*Kontroler którego zadaniem jest porzedstawienie
oferty komisu na stronie głownej*/
@Controller
@RequestMapping("/")
public class IndexController {

    private final CarService carService;

    @Autowired
    public IndexController(CarService carService) {
        this.carService = carService;
    }

    @RequestMapping
    public String showCars(@ModelAttribute("carDto") CarDto carDto, Model model) {
        List<Car> cars = carService.findByRenouncementLike(Transaction.RENOUNCEMENT);
        model.addAttribute("cars", cars);

        List<String> markList = carService.findMark();
        model.addAttribute("markList", markList);

        List<String> modelList = carService.findModel();
        model.addAttribute("modelList", modelList);

        List<Integer> years = carService.findProductionYear();
        model.addAttribute("years", years);

        return "index";
    }

    @PostMapping("/filter")
    public String filters(@ModelAttribute("carDto") CarDto carDto,
                          @RequestParam("mark") String carMark,
                          @RequestParam("model") String carModel, Model model) {
        List<Car> cars = carService.findCarByMark(carMark);
        carService.findCarsByModel(carModel);

        model.addAttribute("cars", cars);
        return "index";
    }

}