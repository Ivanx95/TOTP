package com.example.balancer.repository;

import com.example.balancer.model.ServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance,Long> {


   List<ServiceInstance> findByService_Name(String name);
}
