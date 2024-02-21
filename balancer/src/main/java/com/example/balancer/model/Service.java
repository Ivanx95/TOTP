package com.example.balancer.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "Service")
@Data
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "service",cascade = CascadeType.ALL)
    private List<ServiceInstance> instances;
}
