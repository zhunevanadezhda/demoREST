package com.example.demo;

import java.util.ArrayList;

public interface IUserService {
    boolean create(User user);
    boolean delete(int id);
    boolean update(User new_user);
    ArrayList<User> read();
    User FindById(int id);
}