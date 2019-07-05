package com.mallfe.user.controller;

import com.mallfe.user.pojo.User;
import com.mallfe.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 描述
 *
 * @author Yangbin
 * @since 2019/07/05
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("queryuser")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password){
        return ResponseEntity.ok(userService.verifyUser(username, password));
    }

    @PostMapping("insert")
    public ResponseEntity<User> addUser(@RequestBody User user){

        User u = userService.insertUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }


}
