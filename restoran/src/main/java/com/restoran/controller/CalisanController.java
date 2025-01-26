package com.restoran.controller;

import com.restoran.entity.Calisan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calisan")
public class CalisanController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createCalisan(@RequestBody Calisan calisan) {
        String sql = "INSERT INTO calisan (name, restaurant_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, calisan.getName(), calisan.getRestaurantId());
        if (rows > 0) {
            return ResponseEntity.ok("Çalışan başarıyla eklendi: " + calisan.getName());
        } else {
            return ResponseEntity.status(500).body("Çalışan eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Calisan>> getAllCalisanlar() {
        String sql = "SELECT * FROM calisan";
        List<Calisan> calisanlar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Calisan calisan = new Calisan();
            calisan.setId(rs.getInt("id"));
            calisan.setName(rs.getString("name"));
            calisan.setRestaurantId(rs.getInt("restaurant_id"));
            return calisan;
        });
        return ResponseEntity.ok(calisanlar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCalisanById(@PathVariable int id) {
        String sql = "SELECT * FROM calisan WHERE id = ?";
        List<Calisan> calisanlar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Calisan calisan = new Calisan();
            calisan.setId(rs.getInt("id"));
            calisan.setName(rs.getString("name"));
            calisan.setRestaurantId(rs.getInt("restaurant_id"));
            return calisan;
        }, id);

        if (!calisanlar.isEmpty()) {
            return ResponseEntity.ok(calisanlar.get(0));
        } else {
            return ResponseEntity.status(404).body("Çalışan bulunamadı!");
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCalisan(@PathVariable int id, @RequestBody Calisan updatedCalisan) {
        String sql = "UPDATE calisan SET name = ?, restaurant_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedCalisan.getName(), updatedCalisan.getRestaurantId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Çalışan başarıyla güncellendi: " + updatedCalisan.getName());
        } else {
            return ResponseEntity.status(404).body("Çalışan bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCalisan(@PathVariable int id) {
        String sql = "DELETE FROM calisan WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Çalışan başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Çalışan bulunamadı!");
        }
    }
}
