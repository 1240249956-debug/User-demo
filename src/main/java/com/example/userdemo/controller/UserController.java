package com.example.userdemo.controller;

import com.example.userdemo.entity.User;
import com.example.userdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 创建用户
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User savedUser = userService.addUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("创建用户失败: " + e.getMessage());
        }
    }

    // 查询所有用户
    @GetMapping("/list")
    public ResponseEntity<?> listUsers() {
        try {
            List<User> users = userService.listUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("查询用户失败: " + e.getMessage());
        }
    }

    // 根据 ID 查询单个用户
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            List<User> users = userService.listUsers();
            User user = users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                return ResponseEntity.badRequest().body("用户不存在");
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("查询用户失败: " + e.getMessage());
        }
    }

    // 更新用户
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            List<User> users = userService.listUsers();
            User existingUser = users.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (existingUser == null) {
                return ResponseEntity.badRequest().body("用户不存在");
            }

            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());

            User updatedUser = userService.addUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("更新用户失败: " + e.getMessage());
        }
    }

    // 删除用户
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("删除用户失败: " + e.getMessage());
        }
    }
}
