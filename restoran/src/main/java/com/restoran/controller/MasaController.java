package com.restoran.controller;

import com.restoran.entity.Masa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masa")
public class MasaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createMasa(@RequestBody Masa masa) {
        String sql = "INSERT INTO masa (numara, kapasite, restaurant_id) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql, masa.getNumara(), masa.getKapasite(), masa.getRestaurantId());
        if (rows > 0) {
            return ResponseEntity.ok("Masa başarıyla eklendi: " + masa.getNumara());
        } else {
            return ResponseEntity.status(500).body("Masa eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Masa>> getAllMasalar() {
        String sql = "SELECT * FROM masa";
        List<Masa> masalar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Masa masa = new Masa();
            masa.setId(rs.getInt("id"));
            masa.setNumara(rs.getInt("numara"));
            masa.setKapasite(rs.getInt("kapasite"));
            masa.setRestaurantId(rs.getInt("restaurant_id"));
            return masa;
        });
        return ResponseEntity.ok(masalar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMasaById(@PathVariable int id) {
        String sql = "SELECT * FROM masa WHERE id = ?";
        List<Masa> masalar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Masa masa = new Masa();
            masa.setId(rs.getInt("id"));
            masa.setNumara(rs.getInt("numara"));
            masa.setKapasite(rs.getInt("kapasite"));
            masa.setRestaurantId(rs.getInt("restaurant_id"));
            return masa;
        }, id);

        if (!masalar.isEmpty()) {
            return ResponseEntity.ok(masalar.get(0));
        } else {
            return ResponseEntity.status(404).body("Masa bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMasa(@PathVariable int id, @RequestBody Masa updatedMasa) {
        String sql = "UPDATE masa SET numara = ?, kapasite = ?, restaurant_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedMasa.getNumara(), updatedMasa.getKapasite(), updatedMasa.getRestaurantId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Masa başarıyla güncellendi: " + updatedMasa.getNumara());
        } else {
            return ResponseEntity.status(404).body("Masa bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMasa(@PathVariable int id) {
        String sql = "DELETE FROM masa WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Masa başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Masa bulunamadı!");
        }
    }
}
