package com.restoran.controller;

import com.restoran.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createRestaurant(@RequestBody Restaurant restaurant) {
        String sql = "INSERT INTO restaurant (name, address) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, restaurant.getName(), restaurant.getAddress());
        if (rows > 0) {
            return ResponseEntity.ok("Restaurant başarıyla eklendi: " + restaurant.getName());
        } else {
            return ResponseEntity.status(500).body("Restaurant eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        String sql = "SELECT * FROM restaurant";
        List<Restaurant> restaurants = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(rs.getInt("id"));
            restaurant.setName(rs.getString("name"));
            restaurant.setAddress(rs.getString("address"));
            return restaurant;
        });
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable int id) {
        String sql = "SELECT * FROM restaurant WHERE id = ?";
        List<Restaurant> restaurants = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(rs.getInt("id"));
            restaurant.setName(rs.getString("name"));
            restaurant.setAddress(rs.getString("address"));
            return restaurant;
        }, id);

        if (!restaurants.isEmpty()) {
            return ResponseEntity.ok(restaurants.get(0));
        } else {
            return ResponseEntity.status(404).body("Restaurant bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable int id, @RequestBody Restaurant updatedRestaurant) {
        String sql = "UPDATE restaurant SET name = ?, address = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedRestaurant.getName(), updatedRestaurant.getAddress(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Restaurant başarıyla güncellendi: " + updatedRestaurant.getName());
        } else {
            return ResponseEntity.status(404).body("Restaurant bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable int id) {
        String sql = "DELETE FROM restaurant WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Restaurant başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Restaurant bulunamadı!");
        }
    }
}
