package com.restoran.controller;

import com.restoran.entity.Teslimat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teslimat")
public class TeslimatController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createTeslimat(@RequestBody Teslimat teslimat) {
        String sql = "INSERT INTO teslimat (address, durum, restaurant_id, siparis_id) VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql, teslimat.getAddress(), teslimat.getDurum(), teslimat.getRestaurantId(), teslimat.getSiparisId());
        if (rows > 0) {
            return ResponseEntity.ok("Teslimat başarıyla eklendi.");
        } else {
            return ResponseEntity.status(500).body("Teslimat eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Teslimat>> getAllTeslimatlar() {
        String sql = "SELECT * FROM teslimat";
        List<Teslimat> teslimatlar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Teslimat teslimat = new Teslimat();
            teslimat.setId(rs.getInt("id"));
            teslimat.setAddress(rs.getString("address"));
            teslimat.setDurum(rs.getString("durum"));
            teslimat.setRestaurantId(rs.getInt("restaurant_id"));
            teslimat.setSiparisId(rs.getInt("siparis_id"));
            return teslimat;
        });
        return ResponseEntity.ok(teslimatlar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeslimatById(@PathVariable int id) {
        String sql = "SELECT * FROM teslimat WHERE id = ?";
        List<Teslimat> teslimatlar = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Teslimat teslimat = new Teslimat();
            teslimat.setId(rs.getInt("id"));
            teslimat.setAddress(rs.getString("address"));
            teslimat.setDurum(rs.getString("durum"));
            teslimat.setRestaurantId(rs.getInt("restaurant_id"));
            teslimat.setSiparisId(rs.getInt("siparis_id"));
            return teslimat;
        }, id);

        if (!teslimatlar.isEmpty()) {
            return ResponseEntity.ok(teslimatlar.get(0));
        } else {
            return ResponseEntity.status(404).body("Teslimat bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTeslimat(@PathVariable int id, @RequestBody Teslimat updatedTeslimat) {
        String sql = "UPDATE teslimat SET address = ?, durum = ?, restaurant_id = ?, siparis_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedTeslimat.getAddress(), updatedTeslimat.getDurum(), updatedTeslimat.getRestaurantId(), updatedTeslimat.getSiparisId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Teslimat başarıyla güncellendi.");
        } else {
            return ResponseEntity.status(404).body("Teslimat bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeslimat(@PathVariable int id) {
        String sql = "DELETE FROM teslimat WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Teslimat başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Teslimat bulunamadı!");
        }
    }
}
