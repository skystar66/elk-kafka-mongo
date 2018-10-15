package com.xcar360.service;

import com.xcar360.es.Entity;

import java.util.List;

public interface CityESService {

    void saveEntity(Entity entity);

    void saveEntity(List<Entity> entityList);

    List<Entity> searchEntity(String searchContent);



}
