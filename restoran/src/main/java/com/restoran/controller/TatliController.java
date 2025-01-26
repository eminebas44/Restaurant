package com.restoran.controller;

import com.restoran.entity.Tatli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tatli")
public class TatliController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createTatli(@RequestBody Tatli tatli) {
        String sql = "INSERT INTO tatli (name, menu_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, tatli.getName(), tatli.getMenuId());
        if (rows > 0) {
            return ResponseEntity.ok("Tatlı başarıyla eklendi: " + tatli.getName());
        } else {
            return ResponseEntity.status(500).body("Tatlı eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Tatli>> getAllTatli() {
        String sql = "SELECT * FROM tatli";
        List<Tatli> tatlilar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tatli tatli = new Tatli();
            tatli.setId(rs.getInt("id"));
            tatli.setName(rs.getString("name"));
            tatli.setMenuId(rs.getInt("menu_id"));
            return tatli;
        });
        return ResponseEntity.ok(tatlilar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTatliById(@PathVariable int id) {
        String sql = "SELECT * FROM tatli WHERE id = ?";
        List<Tatli> tatlilar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tatli tatli = new Tatli();
            tatli.setId(rs.getInt("id"));
            tatli.setName(rs.getString("name"));
            tatli.setMenuId(rs.getInt("menu_id"));
            return tatli;
        }, id);

        if (!tatlilar.isEmpty()) {
            return ResponseEntity.ok(tatlilar.get(0));
        } else {
            return ResponseEntity.status(404).body("Tatlı bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTatli(@PathVariable int id, @RequestBody Tatli updatedTatli) {
        String sql = "UPDATE tatli SET name = ?, menu_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedTatli.getName(), updatedTatli.getMenuId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Tatlı başarıyla güncellendi: " + updatedTatli.getName());
        } else {
            return ResponseEntity.status(404).body("Tatlı bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTatli(@PathVariable int id) {
        String sql = "DELETE FROM tatli WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Tatlı başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Tatlı bulunamadı!");
        }
    }
}
