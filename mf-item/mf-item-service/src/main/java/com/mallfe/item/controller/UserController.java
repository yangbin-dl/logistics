package com.mallfe.item.controller;

import com.mallfe.common.json.JsonObject;
import com.mallfe.common.vo.PageResult;
import com.mallfe.item.pojo.Region;
import com.mallfe.item.pojo.Store;
import com.mallfe.item.pojo.User;
import com.mallfe.item.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("page")
    public ResponseEntity<PageResult<User>> queryByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        PageResult<User> result = userService.querySpByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> queryById(@PathVariable(value = "id") Long id) {
        User result = userService.selectById(id);
        return ResponseEntity.ok(result);
    }


    @PostMapping("insert")
    public ResponseEntity<User> insert(@RequestBody User user) {

        User u = userService.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @PostMapping("update")
    public ResponseEntity<User> update(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("query")
    public ResponseEntity<User> login(@RequestParam("username") String username,
                                          @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.verify(username, password));
    }

    @GetMapping("find")
    public JsonObject checkUser(@RequestParam("username") String username,
                                @RequestParam("password") String password) {
        return userService.checkUser(username, password);
    }

    @GetMapping("store")
    public ResponseEntity<List<Store>> getStore(@RequestParam(value = "deptcode",required = false)String deptCode) {
        return ResponseEntity.ok(userService.selectStoreList(deptCode));
    }

    @GetMapping("dept")
    public ResponseEntity<List<Region>> getRegion() {
        return ResponseEntity.ok(userService.selectRegionList());
    }


}
