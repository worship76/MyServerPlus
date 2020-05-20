package com.nuc.server.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.nuc.server.bean.User;
import com.nuc.server.dao.UserDao;
import com.nuc.server.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Integer count() {
        return userDao.count();
    }

    @Override
    public User login_android(String userName,String password){
        return userDao.login_android(userName,password);
    }

    @Override
    public void register_android(Integer id, String userName, String password, String num, Integer sid, String role) {
        userDao.register_android(id,userName,password,num,sid,role);
    }

    @Override
    public void updateMessage_android(String userName, String num, Integer id) {
        userDao.updateMessage_android(userName,num,id);
    }

    @Override
    public void updatePsd_android(String password, Integer id) {
        userDao.updatePsd_android(password,id);
    }

    @Override
    public User select(String userName) {
        return userDao.select(userName);
    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public List<User> findUser(Map<String, Object> map) {
        return userDao.findUsers(map);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public Long getTotalUser(Map<String, Object> map) {
        return userDao.getTotalUser(map);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userDao.deleteUser(id);
    }

}
