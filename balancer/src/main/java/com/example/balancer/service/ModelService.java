package com.example.balancer.service;

import com.example.balancer.model.Service;

import java.util.List;

public interface ModelService  {

     Service  getService(String name);

      Service save(Service service);

      List<Service> all();
}
