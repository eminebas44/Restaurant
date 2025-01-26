package com.restoran.controller;

import com.restoran.entity.Odeme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odeme")
public class OdemeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createOdeme(@RequestBody Odeme odeme) {
        String sql = "INSERT INTO odeme (tarih, amount, siparis_id) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql, odeme.getTarih(), odeme.getAmount(), odeme.getSiparisId());
        if (rows > 0) {
            return ResponseEntity.ok("Ödeme başarıyla eklendi.");
        } else {
            return ResponseEntity.status(500).body("Ödeme eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Odeme>> getAllOdemeler() {
        String sql = "SELECT * FROM odeme";
        List<Odeme> odemeler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Odeme odeme = new Odeme();
            odeme.setId(rs.getInt("id"));
            odeme.setTarih(rs.getDate("tarih").toString());
            odeme.setAmount(rs.getFloat("amount"));
            odeme.setSiparisId(rs.getInt("siparis_id"));
            return odeme;
        });
        return ResponseEntity.ok(odemeler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOdemeById(@PathVariable int id) {
        String sql = "SELECT * FROM odeme WHERE id = ?";
        List<Odeme> odemeler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Odeme odeme = new Odeme();
            odeme.setId(rs.getInt("id"));
            odeme.setTarih(rs.getDate("tarih").toString());
            odeme.setAmount(rs.getFloat("amount"));
            odeme.setSiparisId(rs.getInt("siparis_id"));
            return odeme;
        }, id);

        if (!odemeler.isEmpty()) {
            return ResponseEntity.ok(odemeler.get(0));
        } else {
            return ResponseEntity.status(404).body("Ödeme bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOdeme(@PathVariable int id, @RequestBody Odeme updatedOdeme) {
        String sql = "UPDATE odeme SET tarih = ?, amount = ?, siparis_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedOdeme.getTarih(), updatedOdeme.getAmount(), updatedOdeme.getSiparisId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Ödeme başarıyla güncellendi.");
        } else {
            return ResponseEntity.status(404).body("Ödeme bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOdeme(@PathVariable int id) {
        String sql = "DELETE FROM odeme WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Ödeme başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Ödeme bulunamadı!");
        }
    }
}
