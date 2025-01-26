package com.restoran.controller;

import com.restoran.entity.Vardiya;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vardiya")
public class VardiyaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createVardiya(@RequestBody Vardiya vardiya) {
        String sql = "INSERT INTO vardiya (saatler, calisan_id) VALUES (?, ?)";
        int rows = jdbcTemplate.update(sql, vardiya.getSaatler(), vardiya.getCalisanId());
        if (rows > 0) {
            return ResponseEntity.ok("Vardiya başarıyla eklendi.");
        } else {
            return ResponseEntity.status(500).body("Vardiya eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Vardiya>> getAllVardiyalar() {
        String sql = "SELECT * FROM vardiya";
        List<Vardiya> vardiyalar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vardiya vardiya = new Vardiya();
            vardiya.setId(rs.getInt("id"));
            vardiya.setSaatler(rs.getString("saatler"));
            vardiya.setCalisanId(rs.getInt("calisan_id"));
            return vardiya;
        });
        return ResponseEntity.ok(vardiyalar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVardiyaById(@PathVariable int id) {
        String sql = "SELECT * FROM vardiya WHERE id = ?";
        List<Vardiya> vardiyalar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vardiya vardiya = new Vardiya();
            vardiya.setId(rs.getInt("id"));
            vardiya.setSaatler(rs.getString("saatler"));
            vardiya.setCalisanId(rs.getInt("calisan_id"));
            return vardiya;
        }, id);

        if (!vardiyalar.isEmpty()) {
            return ResponseEntity.ok(vardiyalar.get(0));
        } else {
            return ResponseEntity.status(404).body("Vardiya bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVardiya(@PathVariable int id, @RequestBody Vardiya updatedVardiya) {
        String sql = "UPDATE vardiya SET saatler = ?, calisan_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedVardiya.getSaatler(), updatedVardiya.getCalisanId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Vardiya başarıyla güncellendi.");
        } else {
            return ResponseEntity.status(404).body("Vardiya bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVardiya(@PathVariable int id) {
        String sql = "DELETE FROM vardiya WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Vardiya başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Vardiya bulunamadı!");
        }
    }
}
