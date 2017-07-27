package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.Country;
import ua.org.ubts.applicationssystem.repository.CountryRepository;
import ua.org.ubts.applicationssystem.service.CountryService;

import javax.transaction.Transactional;
import java.util.List;

@Service("countryService")
@Transactional
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country findById(Integer id) {
        return countryRepository.findOne(id);
    }

    @Override
    public Country findByName(String name) {
        return countryRepository.findByName(name);
    }

    @Override
    public Country findByData(Country country) {
        return findByName(country.getName());
    }

    @Override
    public void save(Country country) {
        countryRepository.save(country);
    }

    @Override
    public void deleteById(Integer id) {
        countryRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        countryRepository.deleteAll();
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public boolean isExist(Country country) {
        return findByData(country) != null;
    }

}
