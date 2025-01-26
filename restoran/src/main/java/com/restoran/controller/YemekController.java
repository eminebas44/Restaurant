package com.restoran.controller;

import com.restoran.entity.Yemek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/yemek")
public class YemekController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createYemek(@RequestBody Yemek yemek) {
        String sql = "INSERT INTO yemek (name, menu_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, yemek.getName(), yemek.getMenuId());
        if (rows > 0) {
            return ResponseEntity.ok("Yemek başarıyla eklendi: " + yemek.getName());
        } else {
            return ResponseEntity.status(500).body("Yemek eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Yemek>> getAllYemekler() {
        String sql = "SELECT * FROM yemek";
        List<Yemek> yemekler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Yemek yemek = new Yemek();
            yemek.setId(rs.getInt("id"));
            yemek.setName(rs.getString("name"));
            yemek.setMenuId(rs.getInt("menu_id"));
            return yemek;
        });
        return ResponseEntity.ok(yemekler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getYemekById(@PathVariable int id) {
        String sql = "SELECT * FROM yemek WHERE id = ?";
        List<Yemek> yemekler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Yemek yemek = new Yemek();
            yemek.setId(rs.getInt("id"));
            yemek.setName(rs.getString("name"));
            yemek.setMenuId(rs.getInt("menu_id"));
            return yemek;
        }, id);

        if (!yemekler.isEmpty()) {
            return ResponseEntity.ok(yemekler.get(0));
        } else {
            return ResponseEntity.status(404).body("Yemek bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateYemek(@PathVariable int id, @RequestBody Yemek updatedYemek) {
        String sql = "UPDATE yemek SET name = ?, menu_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedYemek.getName(), updatedYemek.getMenuId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Yemek başarıyla güncellendi: " + updatedYemek.getName());
        } else {
            return ResponseEntity.status(404).body("Yemek bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteYemek(@PathVariable int id) {
        String sql = "DELETE FROM yemek WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Yemek başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Yemek bulunamadı!");
        }
    }
}
