package com.restoran.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restoran.entity.Geribildirim;

@RestController
@RequestMapping("/geribildirim")
public class GeribildirimController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createGeribildirim(@RequestBody Geribildirim geribildirim) {
        String sql = "INSERT INTO geribildirim (yorum, user_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, geribildirim.getYorum(), geribildirim.getUserId());
        if (rows > 0) {
            return ResponseEntity.ok("Geribildirim başarıyla eklendi: " + geribildirim.getYorum());
        } else {
            return ResponseEntity.status(500).body("Geribildirim eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Geribildirim>> getAllGeribildirimler() {
        String sql = "SELECT * FROM geribildirim";
        List<Geribildirim> geribildirimler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Geribildirim geribildirim = new Geribildirim();
            geribildirim.setId(rs.getInt("id"));
            geribildirim.setYorum(rs.getString("yorum"));
            geribildirim.setUserId(rs.getInt("user_id"));
            return geribildirim;
        });
        return ResponseEntity.ok(geribildirimler);
    }

    @GetMapping("/restoran-geri-bildirim")
    public ResponseEntity<List<Map<String, Object>>> getRestoranGeriBildirimSayisi() {
        String sql = """
        SELECT 
            r.name AS restoran_adi,
            COUNT(g.id) AS geri_bildirim_sayisi
        FROM 
            GeriBildirim g
        JOIN 
            Users u ON g.user_id = u.id
        JOIN 
            Siparis s ON u.id = s.user_id
        JOIN 
            Masa m ON s.masa_id = m.id
        JOIN 
            Restaurant r ON m.restaurant_id = r.id
        GROUP BY 
            r.name
        ORDER BY 
            geri_bildirim_sayisi DESC;
    """;

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGeribildirimById(@PathVariable int id) {
        String sql = "SELECT * FROM geribildirim WHERE id = ?";
        List<Geribildirim> geribildirimler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Geribildirim geribildirim = new Geribildirim();
            geribildirim.setId(rs.getInt("id"));
            geribildirim.setYorum(rs.getString("yorum"));
            geribildirim.setUserId(rs.getInt("user_id"));
            return geribildirim;
        }, id);

        if (!geribildirimler.isEmpty()) {
            return ResponseEntity.ok(geribildirimler.get(0));
        } else {
            return ResponseEntity.status(404).body("Geribildirim bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGeribildirim(@PathVariable int id, @RequestBody Geribildirim updatedGeribildirim) {
        String sql = "UPDATE geribildirim SET yorum = ?, user_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedGeribildirim.getYorum(), updatedGeribildirim.getUserId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Geribildirim başarıyla güncellendi: " + updatedGeribildirim.getYorum());
        } else {
            return ResponseEntity.status(404).body("Geribildirim bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGeribildirim(@PathVariable int id) {
        String sql = "DELETE FROM geribildirim WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Geribildirim başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Geribildirim bulunamadı!");
        }
    }
}