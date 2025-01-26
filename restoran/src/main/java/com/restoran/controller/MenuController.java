package com.restoran.controller;

import com.restoran.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createMenu(@RequestBody Menu menu) {
        String sql = "INSERT INTO menu (name, restaurant_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, menu.getName(), menu.getRestaurantId());
        if (rows > 0) {
            return ResponseEntity.ok("Menü başarıyla eklendi: " + menu.getName());
        } else {
            return ResponseEntity.status(500).body("Menü eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenuler() {
        String sql = "SELECT * FROM menu";
        List<Menu> menuler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Menu menu = new Menu();
            menu.setId(rs.getInt("id"));
            menu.setName(rs.getString("name"));
            menu.setRestaurantId(rs.getInt("restaurant_id"));
            return menu;
        });
        return ResponseEntity.ok(menuler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable int id) {
        String sql = "SELECT * FROM menu WHERE id = ?";
        List<Menu> menuler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Menu menu = new Menu();
            menu.setId(rs.getInt("id"));
            menu.setName(rs.getString("name"));
            menu.setRestaurantId(rs.getInt("restaurant_id"));
            return menu;
        }, id);

        if (!menuler.isEmpty()) {
            return ResponseEntity.ok(menuler.get(0));
        } else {
            return ResponseEntity.status(404).body("Menü bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMenu(@PathVariable int id, @RequestBody Menu updatedMenu) {
        String sql = "UPDATE menu SET name = ?, restaurant_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedMenu.getName(), updatedMenu.getRestaurantId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Menü başarıyla güncellendi: " + updatedMenu.getName());
        } else {
            return ResponseEntity.status(404).body("Menü bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable int id) {
        String sql = "DELETE FROM menu WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Menü başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Menü bulunamadı!");
        }
    }
}
