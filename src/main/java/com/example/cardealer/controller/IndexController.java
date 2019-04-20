package com.example.cardealer.controller;

import com.example.cardealer.service.CarService;
import lombok.Data;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*Kontroler którego zadaniem jest porzedstawienie
oferty komisu na stronie głownej*/
@CrossOrigin
@Data
@RestController
public class IndexController {

    private final CarService carService;

    public IndexController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("cars", carService.getCarsDto());
        return "index";
    }


    /*@RequestMapping
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
*/
}