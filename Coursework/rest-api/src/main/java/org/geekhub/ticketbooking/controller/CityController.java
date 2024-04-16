package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.model.City;
import org.geekhub.ticketbooking.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin/cities")
@SuppressWarnings("java:S1192")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public String viewHomePageWithPagination(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "7") int pageSize, Model model) {
        List<City> cities = cityService.getCitiesWithPagination(page, pageSize);
        int rows = cityService.getCitiesRowsCount();
        if(rows == -1 || cities.isEmpty()) {
            model.addAttribute("error", "Can't load cities");
        }
        else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listCities", cities);
        }

        return "cities";
    }

    @GetMapping("/showNewCityForm")
    public String showNewCityForm(Model model) {
        City city = new City();
        model.addAttribute("city", city);
        return "new_city";
    }

    @PostMapping("/saveCity")
    public String saveCity(@ModelAttribute("city") City city, Model model) {
        City addedCity = cityService.addCity(city);
        if (addedCity == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid city parameters", "new_city");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added city", "new_city");
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") int id, Model model) {
        City city = cityService.getCityById(id);
        model.addAttribute("city", city);
        if (city == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get city by this id", "update_city");
        }
        return "update_city";
    }

    @PostMapping("/updateCity")
    public String updateCity(@ModelAttribute("city") City city, Model model) {
        int cityId = city.getId();

        City updatedCity = cityService.updateCityById(city, cityId);

        if (updatedCity == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid city parameters", "update_city");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully updated city", "update_city");
    }

    @GetMapping("/deleteCity/{id}")
    public String deleteCity(@PathVariable(value = "id") int id, Model model) {
        boolean result = cityService.deleteCityById(id);
        if(!result) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete city by this id", "update_city");
        }
        return "redirect:/admin/cities";
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }
}
