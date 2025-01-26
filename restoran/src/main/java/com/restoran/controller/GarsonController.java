package com.restoran.controller;

import com.restoran.entity.Garson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garson")
public class GarsonController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createGarson(@RequestBody Garson garson) {
        String sql = "INSERT INTO garson (name, calisan_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, garson.getName(), garson.getCalisanId());
        if (rows > 0) {
            return ResponseEntity.ok("Garson başarıyla eklendi: " + garson.getName());
        } else {
            return ResponseEntity.status(500).body("Garson eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Garson>> getAllGarsonlar() {
        String sql = "SELECT * FROM garson";
        List<Garson> garsonlar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Garson garson = new Garson();
            garson.setId(rs.getInt("id"));
            garson.setName(rs.getString("name"));
            garson.setCalisanId(rs.getInt("calisan_id"));
            return garson;
        });
        return ResponseEntity.ok(garsonlar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGarsonById(@PathVariable int id) {
        String sql = "SELECT * FROM garson WHERE id = ?";
        List<Garson> garsonlar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Garson garson = new Garson();
            garson.setId(rs.getInt("id"));
            garson.setName(rs.getString("name"));
            garson.setCalisanId(rs.getInt("calisan_id"));
            return garson;
        }, id);

        if (!garsonlar.isEmpty()) {
            return ResponseEntity.ok(garsonlar.get(0));
        } else {
            return ResponseEntity.status(404).body("Garson bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGarson(@PathVariable int id, @RequestBody Garson updatedGarson) {
        String sql = "UPDATE garson SET name = ?, calisan_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedGarson.getName(), updatedGarson.getCalisanId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Garson başarıyla güncellendi: " + updatedGarson.getName());
        } else {
            return ResponseEntity.status(404).body("Garson bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGarson(@PathVariable int id) {
        String sql = "DELETE FROM garson WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Garson başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Garson bulunamadı!");
        }
    }
}
