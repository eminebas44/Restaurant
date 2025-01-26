package com.restoran.controller;

import com.restoran.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Users user) {
        String sql = "INSERT INTO users (username, password, address, phone) VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getAddress(), user.getPhone());
        if (rows > 0) {
            return ResponseEntity.ok("Kullanıcı başarıyla eklendi: " + user.getUsername());
        } else {
            return ResponseEntity.status(500).body("Kullanıcı eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<Users> usersList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Users user = new Users();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAddress(rs.getString("address"));
            user.setPhone(rs.getString("phone"));
            return user;
        });
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<Users> usersList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Users user = new Users();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAddress(rs.getString("address"));
            user.setPhone(rs.getString("phone"));
            return user;
        }, id);

        if (!usersList.isEmpty()) {
            return ResponseEntity.ok(usersList.get(0));
        } else {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody Users updatedUser) {
        String sql = "UPDATE users SET username = ?, password = ?, address = ?, phone = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getAddress(), updatedUser.getPhone(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Kullanıcı başarıyla güncellendi: " + updatedUser.getUsername());
        } else {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Kullanıcı başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Kullanıcı bulunamadı!");
        }
    }
}
