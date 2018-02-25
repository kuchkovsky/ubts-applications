package ua.org.ubts.applicationssystem.service;

import ua.org.ubts.applicationssystem.entity.Property;
import ua.org.ubts.applicationssystem.service.template.BasicService;
import ua.org.ubts.applicationssystem.service.template.ExtendedElementOperationsService;

public interface PropertyService extends BasicService<Property, Integer>, ExtendedElementOperationsService<Property> {

    Property findByKey(String key);

}
