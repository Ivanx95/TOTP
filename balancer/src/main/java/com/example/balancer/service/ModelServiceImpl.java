package com.example.balancer.service;

import com.example.balancer.model.Service;
import com.example.balancer.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@org.springframework.stereotype.Service
public class ModelServiceImpl implements ModelService {
    private final ServiceRepository serviceRepository;

    @Override
    public List<Service> all() {
        return serviceRepository.findAll();
    }

    @Override

    public Service getService(String name) {
        return serviceRepository.findOneByName(name);
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }


}
