package com.restoran.controller;

import com.restoran.entity.Stok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stok")
public class StokController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createStok(@RequestBody Stok stok) {
        String sql = "INSERT INTO stok (miktar, yemek_id, icecek_id, tatli_id, tedarikci_id) VALUES (?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql, stok.getMiktar(), stok.getYemekId(), stok.getIcecekId(), stok.getTatliId(), stok.getTedarikciId());
        if (rows > 0) {
            return ResponseEntity.ok("Stok başarıyla eklendi.");
        } else {
            return ResponseEntity.status(500).body("Stok eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Stok>> getAllStoklar() {
        String sql = "SELECT * FROM stok";
        List<Stok> stoklar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Stok stok = new Stok();
            stok.setId(rs.getInt("id"));
            stok.setMiktar(rs.getInt("miktar"));
            stok.setYemekId(rs.getInt("yemek_id"));
            stok.setIcecekId(rs.getInt("icecek_id"));
            stok.setTatliId(rs.getInt("tatli_id"));
            stok.setTedarikciId(rs.getInt("tedarikci_id"));
            return stok;
        });
        return ResponseEntity.ok(stoklar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStokById(@PathVariable int id) {
        String sql = "SELECT * FROM stok WHERE id = ?";
        List<Stok> stoklar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Stok stok = new Stok();
            stok.setId(rs.getInt("id"));
            stok.setMiktar(rs.getInt("miktar"));
            stok.setYemekId(rs.getInt("yemek_id"));
            stok.setIcecekId(rs.getInt("icecek_id"));
            stok.setTatliId(rs.getInt("tatli_id"));
            stok.setTedarikciId(rs.getInt("tedarikci_id"));
            return stok;
        }, id);

        if (!stoklar.isEmpty()) {
            return ResponseEntity.ok(stoklar.get(0));
        } else {
            return ResponseEntity.status(404).body("Stok bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStok(@PathVariable int id, @RequestBody Stok updatedStok) {
        String sql = "UPDATE stok SET miktar = ?, yemek_id = ?, icecek_id = ?, tatli_id = ?, tedarikci_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedStok.getMiktar(), updatedStok.getYemekId(), updatedStok.getIcecekId(), updatedStok.getTatliId(), updatedStok.getTedarikciId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Stok başarıyla güncellendi.");
        } else {
            return ResponseEntity.status(404).body("Stok bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStok(@PathVariable int id) {
        String sql = "DELETE FROM stok WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Stok başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Stok bulunamadı!");
        }
    }
}
