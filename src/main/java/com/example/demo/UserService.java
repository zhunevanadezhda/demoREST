package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class UserService implements IUserService{
    static WorkWithBD bdClass = new WorkWithBD();

    @Override
    public boolean create(User user) {
        int id=bdClass.Create(user);
        if (id>0)
        return true;
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (bdClass.Delete(id))
            return true;
        else return false;
    }

    @Override
    public boolean update(User new_user) {
        if (bdClass.Update(new_user))
            return true;
        else return false;
    }

    @Override
    public ArrayList<User> read() {
        return bdClass.Read();
    }

    @Override
    public User FindById(int id) {
        return bdClass.FindById(id);
    }


}
