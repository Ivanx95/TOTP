package com.example.balancer.repository;

import com.example.balancer.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository< Service,Long> {
        Service findOneByName(String name);

}
