package ua.org.ubts.applications.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applications.entity.BooleanPropertyEntity;
import ua.org.ubts.applications.exception.ClosedRegistrationException;
import ua.org.ubts.applications.repository.BooleanPropertyRepository;
import ua.org.ubts.applications.service.StudentRegistrationService;

@Service
public class StudentRegistrationServiceImpl implements StudentRegistrationService {

    private static final String REGISTRATION_PROPERTY = "is_registration_open";

    @Autowired
    private BooleanPropertyRepository booleanPropertyRepository;

    @Override
    public void checkStudentRegistrationStatus() {
        booleanPropertyRepository.findByKey(REGISTRATION_PROPERTY)
                .map(BooleanPropertyEntity::getValue)
                .filter(Boolean.TRUE::equals)
                .orElseThrow(ClosedRegistrationException::new);
    }

    @Override
    public void openStudentRegistration() {
        changeRegistrationState(true);
    }

    @Override
    public void closeStudentRegistration() {
        changeRegistrationState(false);
    }

    private void changeRegistrationState(boolean openRegistration) {
        BooleanPropertyEntity registrationProperty = booleanPropertyRepository.findByKey(REGISTRATION_PROPERTY)
                .orElseGet(() -> new BooleanPropertyEntity(REGISTRATION_PROPERTY));
        registrationProperty.setValue(openRegistration);
        booleanPropertyRepository.save(registrationProperty);
    }

}
