package com.restoran.controller;

import com.restoran.entity.Icecek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/icecek")
public class IcecekController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createIcecek(@RequestBody Icecek icecek) {
        String sql = "INSERT INTO icecek (name, menu_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, icecek.getName(), icecek.getMenuId());
        if (rows > 0) {
            return ResponseEntity.ok("İçecek başarıyla eklendi: " + icecek.getName());
        } else {
            return ResponseEntity.status(500).body("İçecek eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Icecek>> getAllIcecekler() {
        String sql = "SELECT * FROM icecek";
        List<Icecek> icecekler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Icecek icecek = new Icecek();
            icecek.setId(rs.getInt("id"));
            icecek.setName(rs.getString("name"));
            icecek.setMenuId(rs.getInt("menu_id"));
            return icecek;
        });
        return ResponseEntity.ok(icecekler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIcecekById(@PathVariable int id) {
        String sql = "SELECT * FROM icecek WHERE id = ?";
        List<Icecek> icecekler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Icecek icecek = new Icecek();
            icecek.setId(rs.getInt("id"));
            icecek.setName(rs.getString("name"));
            icecek.setMenuId(rs.getInt("menu_id"));
            return icecek;
        }, id);

        if (!icecekler.isEmpty()) {
            return ResponseEntity.ok(icecekler.get(0));
        } else {
            return ResponseEntity.status(404).body("İçecek bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateIcecek(@PathVariable int id, @RequestBody Icecek updatedIcecek) {
        String sql = "UPDATE icecek SET name = ?, menu_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedIcecek.getName(), updatedIcecek.getMenuId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("İçecek başarıyla güncellendi: " + updatedIcecek.getName());
        } else {
            return ResponseEntity.status(404).body("İçecek bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteIcecek(@PathVariable int id) {
        String sql = "DELETE FROM icecek WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("İçecek başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("İçecek bulunamadı!");
        }
    }
}
