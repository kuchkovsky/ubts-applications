package ua.org.ubts.applicationssystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.ubts.applicationssystem.entity.User;
import ua.org.ubts.applicationssystem.repository.UserRepository;
import ua.org.ubts.applicationssystem.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User element) {
        userRepository.save(element);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public User findByData(User user) {
        return userRepository.findByLogin(user.getLogin());
    }

    @Override
    public boolean isExist(User user) {
        return findByData(user) != null;
    }

}
