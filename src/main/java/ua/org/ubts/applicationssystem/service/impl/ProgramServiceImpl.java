package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.Program;
import ua.org.ubts.applicationssystem.repository.ProgramRepository;
import ua.org.ubts.applicationssystem.service.ProgramService;

import javax.transaction.Transactional;
import java.util.List;

@Service("programService")
@Transactional
public class ProgramServiceImpl implements ProgramService {

    @Autowired
    ProgramRepository programRepository;

    @Override
    public Program findById(Long id) {
        return programRepository.findOne(id);
    }

    @Override
    public Program findByNameAndInfo(String name, String info) {
        return programRepository.findByNameAndInfo(name, info);
    }

    @Override
    public Program findByData(Program program) {
        return findByNameAndInfo(program.getName(), program.getInfo());
    }

    @Override
    public void save(Program program) {
        programRepository.save(program);
    }

    @Override
    public void deleteById(Long id) {
        programRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        programRepository.deleteAll();
    }

    @Override
    public List<Program> findAll() {
        return programRepository.findAll();
    }

    @Override
    public boolean isExist(Program program) {
        return findByData(program) != null;
    }

}
