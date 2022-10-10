package ua.com.kalinichev.microservices.lab1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.com.kalinichev.microservices.lab1.exceptions.IncorrectRequestBodyException;
import ua.com.kalinichev.microservices.lab1.models.Specialty;
import ua.com.kalinichev.microservices.lab1.services.interfaces.SpecialtyService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/core/specialties")
public class SpecialtyController {

    private SpecialtyService specialtyService;

    @Autowired
    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }


    @GetMapping
    public Iterable<Specialty> getAllSpecialties(){
        return specialtyService.getAll();
    }


    @GetMapping("/{id}")
    public Specialty getSpecialty(@Min(1) @PathVariable(name = "id") Long id){
        return specialtyService.getSpecialty(id);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Specialty addSpecialty(@RequestBody String json){
        try {
            JSONObject specialty = new JSONObject(json);
            String name = specialty.getString("name");
            int year = specialty.getInt("year");
            JSONArray array = specialty.getJSONArray("subjects");
            return specialtyService.addSpecialty(name,year,array);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new IncorrectRequestBodyException(""+e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Specialty updateSpecialty(@Valid @RequestBody Specialty newSpecialty, @Min(1) @PathVariable(name = "id") Long id) {
        return specialtyService.updateSpecialty(id,newSpecialty.getName(),newSpecialty.getYear());
    }

    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable(name = "id") @Min(1) Long id){
        specialtyService.deleteSpecialty(id);
    }

    @DeleteMapping
    public void deleteAll(){
        specialtyService.deleteAll();
    }

}
