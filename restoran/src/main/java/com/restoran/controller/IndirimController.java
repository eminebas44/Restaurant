package com.restoran.controller;

import com.restoran.entity.Indirim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indirim")
public class IndirimController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createIndirim(@RequestBody Indirim indirim) {
        String sql = "INSERT INTO indirim (amount, kullanici_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, indirim.getAmount(), indirim.getKullaniciId());
        if (rows > 0) {
            return ResponseEntity.ok("İndirim başarıyla eklendi: " + indirim.getAmount());
        } else {
            return ResponseEntity.status(500).body("İndirim eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Indirim>> getAllIndirimler() {
        String sql = "SELECT * FROM indirim";
        List<Indirim> indirimler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Indirim indirim = new Indirim();
            indirim.setId(rs.getInt("id"));
            indirim.setAmount(rs.getString("amount"));
            indirim.setKullaniciId(rs.getInt("kullanici_id"));
            return indirim;
        });
        return ResponseEntity.ok(indirimler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIndirimById(@PathVariable int id) {
        String sql = "SELECT * FROM indirim WHERE id = ?";
        List<Indirim> indirimler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Indirim indirim = new Indirim();
            indirim.setId(rs.getInt("id"));
            indirim.setAmount(rs.getString("amount"));
            indirim.setKullaniciId(rs.getInt("kullanici_id"));
            return indirim;
        }, id);

        if (!indirimler.isEmpty()) {
            return ResponseEntity.ok(indirimler.get(0));
        } else {
            return ResponseEntity.status(404).body("İndirim bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateIndirim(@PathVariable int id, @RequestBody Indirim updatedIndirim) {
        String sql = "UPDATE indirim SET amount = ?, kullanici_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedIndirim.getAmount(), updatedIndirim.getKullaniciId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("İndirim başarıyla güncellendi: " + updatedIndirim.getAmount());
        } else {
            return ResponseEntity.status(404).body("İndirim bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteIndirim(@PathVariable int id) {
        String sql = "DELETE FROM indirim WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("İndirim başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("İndirim bulunamadı!");
        }
    }
}
