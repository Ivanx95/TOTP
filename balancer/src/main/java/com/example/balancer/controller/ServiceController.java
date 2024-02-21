package com.example.balancer.controller;

import com.example.balancer.model.Service;
import com.example.balancer.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceController {

    private final ModelService modelService;

    @PostMapping(value = "/service")
    public ResponseEntity<Service> saveService(@RequestBody  Service service) throws URISyntaxException {
        Optional<Service> foundService = Optional.ofNullable(modelService.getService(service.getName()));

//        if(foundService.isPresent()){
//            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
//        }
        Service savedService = modelService.save(service);

        URI uri = new URI("/service/" + service.getName());
        return ResponseEntity.created(uri).body(savedService);
    }

    @GetMapping(value = "/service/{name}")
    public ResponseEntity<List<Service>> saveService(@PathVariable(name = "name") Optional<String> name) {


        List<Service> services = name.map(modelService::getService).map(Arrays::asList).orElseGet(modelService::all);

        return ResponseEntity.ok(services);
    }
}
