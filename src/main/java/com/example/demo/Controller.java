package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@RestController
public class Controller {
    private final IUserService userService;
    private static final Logger log= LoggerFactory.getLogger(Controller.class);

    @Autowired
    public Controller(IUserService userService) {
        this.userService = userService;
    }
    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        log.info("Creating user");
        if (userService.create(user))
        {
            log.info("User was created");
            return new ResponseEntity<>("User was created",HttpStatus.CREATED);
        }
        else{
            log.info("User couldn't be created");
            return new ResponseEntity<>("User wasn't created",HttpStatus.NOT_MODIFIED);
        }
    }
    @GetMapping(value = "/users")
    public ResponseEntity<ArrayList<User>> read() {
        log.info("Reading all users");
        final ArrayList<User> users = userService.read();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") @Positive int id) {
        log.info("Finding user (id="+id+")");
        User user = userService.FindById(id);

        if (user!=null)
        {
            log.info("User (id="+id+") was found");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else
        {
            log.info("User (id="+id+") doesn't exist");
            return new ResponseEntity<>("This user doesn't exist",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user) {
            log.info("Updating user (id="+user.getId()+")");
            User user2= userService.FindById(user.getId());
            if (user2==null) return new ResponseEntity<>("This user doesn't exist",HttpStatus.NOT_FOUND);
            final boolean updated = userService.update(user);
            if (updated)
            {
                log.info("User (id="+user.getId()+") was updated");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                log.info("User (id="+user.getId()+") wasn't updated");
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") @Positive int id) {
        log.info("Deleting user id="+id);
        final boolean deleted = userService.delete(id);
        if (deleted)
        {
            log.info("User (id="+id+") was deleted");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            log.info("User (id="+id+") wasn't deleted");
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
