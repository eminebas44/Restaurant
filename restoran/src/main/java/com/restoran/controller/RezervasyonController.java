package com.restoran.controller;

import com.restoran.entity.Rezervasyon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rezervasyon")
public class RezervasyonController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createRezervasyon(@RequestBody Rezervasyon rezervasyon) {
        String sql = "INSERT INTO rezervasyon (tarih, saat, user_id, masa_id) VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql, rezervasyon.getTarih(), rezervasyon.getSaat(), rezervasyon.getUserId(), rezervasyon.getMasaId());
        if (rows > 0) {
            return ResponseEntity.ok("Rezervasyon başarıyla eklendi.");
        } else {
            return ResponseEntity.status(500).body("Rezervasyon eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Rezervasyon>> getAllRezervasyonlar() {
        String sql = "SELECT * FROM rezervasyon";
        List<Rezervasyon> rezervasyonlar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Rezervasyon rezervasyon = new Rezervasyon();
            rezervasyon.setId(rs.getInt("id"));
            rezervasyon.setTarih(rs.getDate("tarih").toString());
            rezervasyon.setSaat(rs.getTime("saat").toString());
            rezervasyon.setUserId(rs.getInt("user_id"));
            rezervasyon.setMasaId(rs.getInt("masa_id"));
            return rezervasyon;
        });
        return ResponseEntity.ok(rezervasyonlar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRezervasyonById(@PathVariable int id) {
        String sql = "SELECT * FROM rezervasyon WHERE id = ?";
        List<Rezervasyon> rezervasyonlar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Rezervasyon rezervasyon = new Rezervasyon();
            rezervasyon.setId(rs.getInt("id"));
            rezervasyon.setTarih(rs.getDate("tarih").toString());
            rezervasyon.setSaat(rs.getTime("saat").toString());
            rezervasyon.setUserId(rs.getInt("user_id"));
            rezervasyon.setMasaId(rs.getInt("masa_id"));
            return rezervasyon;
        }, id);

        if (!rezervasyonlar.isEmpty()) {
            return ResponseEntity.ok(rezervasyonlar.get(0));
        } else {
            return ResponseEntity.status(404).body("Rezervasyon bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRezervasyon(@PathVariable int id, @RequestBody Rezervasyon updatedRezervasyon) {
        String sql = "UPDATE rezervasyon SET tarih = ?, saat = ?, user_id = ?, masa_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedRezervasyon.getTarih(), updatedRezervasyon.getSaat(), updatedRezervasyon.getUserId(), updatedRezervasyon.getMasaId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Rezervasyon başarıyla güncellendi.");
        } else {
            return ResponseEntity.status(404).body("Rezervasyon bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRezervasyon(@PathVariable int id) {
        String sql = "DELETE FROM rezervasyon WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Rezervasyon başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Rezervasyon bulunamadı!");
        }
    }
}
