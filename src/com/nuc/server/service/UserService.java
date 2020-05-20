package com.nuc.server.service;

import java.util.List;
import java.util.Map;

import com.nuc.server.bean.User;
import org.springframework.transaction.annotation.Transactional;


public interface UserService {

    public Integer count();
    public User login_android(String userName,String password);
    public void register_android(Integer id,String userName,String password,String num,Integer sid,String role);
    public void updateMessage_android(String userName,String num,Integer id);
    public void updatePsd_android(String password,Integer id);
    public User select(String userName);

    public User login(User user);

    public List<User> findUser(Map<String, Object> map);

    public List<User> findAllUsers();

    public Long getTotalUser(Map<String, Object> map);

    public int updateUser(User user);

    public int addUser(User user);

    public int deleteUser(Integer id);
}
