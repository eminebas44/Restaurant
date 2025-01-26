package com.restoran.controller;

import com.restoran.entity.Siparis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/siparis")
public class SiparisController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createSiparis(@RequestBody Siparis siparis) {
        String sql = "INSERT INTO siparis (tarih, amount, user_id, masa_id) VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql, siparis.getTarih(), siparis.getAmount(), siparis.getUserId(), siparis.getMasaId());
        if (rows > 0) {
            return ResponseEntity.ok("Sipariş başarıyla eklendi.");
        } else {
            return ResponseEntity.status(500).body("Sipariş eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Siparis>> getAllSiparisler() {
        String sql = "SELECT * FROM siparis";
        List<Siparis> siparisler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Siparis siparis = new Siparis();
            siparis.setId(rs.getInt("id"));
            siparis.setTarih(rs.getDate("tarih").toString());
            siparis.setAmount(rs.getFloat("amount"));
            siparis.setUserId(rs.getInt("user_id"));
            siparis.setMasaId(rs.getInt("masa_id"));
            return siparis;
        });
        return ResponseEntity.ok(siparisler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSiparisById(@PathVariable int id) {
        String sql = "SELECT * FROM siparis WHERE id = ?";
        List<Siparis> siparisler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Siparis siparis = new Siparis();
            siparis.setId(rs.getInt("id"));
            siparis.setTarih(rs.getDate("tarih").toString());
            siparis.setAmount(rs.getFloat("amount"));
            siparis.setUserId(rs.getInt("user_id"));
            siparis.setMasaId(rs.getInt("masa_id"));
            return siparis;
        }, id);

        if (!siparisler.isEmpty()) {
            return ResponseEntity.ok(siparisler.get(0));
        } else {
            return ResponseEntity.status(404).body("Sipariş bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSiparis(@PathVariable int id, @RequestBody Siparis updatedSiparis) {
        String sql = "UPDATE siparis SET tarih = ?, amount = ?, user_id = ?, masa_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedSiparis.getTarih(), updatedSiparis.getAmount(), updatedSiparis.getUserId(), updatedSiparis.getMasaId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Sipariş başarıyla güncellendi.");
        } else {
            return ResponseEntity.status(404).body("Sipariş bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSiparis(@PathVariable int id) {
        String sql = "DELETE FROM siparis WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Sipariş başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Sipariş bulunamadı!");
        }
    }
}
