package com.restoran.controller;

import com.restoran.entity.Tedarikci;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tedarikci")
public class TedarikciController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createTedarikci(@RequestBody Tedarikci tedarikci) {
        String sql = "INSERT INTO tedarikci (name, phone, restaurant_id) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql, tedarikci.getName(), tedarikci.getPhone(), tedarikci.getRestaurantId());
        if (rows > 0) {
            return ResponseEntity.ok("Tedarikçi başarıyla eklendi: " + tedarikci.getName());
        } else {
            return ResponseEntity.status(500).body("Tedarikçi eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Tedarikci>> getAllTedarikciler() {
        String sql = "SELECT * FROM tedarikci";
        List<Tedarikci> tedarikciler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tedarikci tedarikci = new Tedarikci();
            tedarikci.setId(rs.getInt("id"));
            tedarikci.setName(rs.getString("name"));
            tedarikci.setPhone(rs.getString("phone"));
            tedarikci.setRestaurantId(rs.getInt("restaurant_id"));
            return tedarikci;
        });
        return ResponseEntity.ok(tedarikciler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTedarikciById(@PathVariable int id) {
        String sql = "SELECT * FROM tedarikci WHERE id = ?";
        List<Tedarikci> tedarikciler = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Tedarikci tedarikci = new Tedarikci();
            tedarikci.setId(rs.getInt("id"));
            tedarikci.setName(rs.getString("name"));
            tedarikci.setPhone(rs.getString("phone"));
            tedarikci.setRestaurantId(rs.getInt("restaurant_id"));
            return tedarikci;
        }, id);

        if (!tedarikciler.isEmpty()) {
            return ResponseEntity.ok(tedarikciler.get(0));
        } else {
            return ResponseEntity.status(404).body("Tedarikçi bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTedarikci(@PathVariable int id, @RequestBody Tedarikci updatedTedarikci) {
        String sql = "UPDATE tedarikci SET name = ?, phone = ?, restaurant_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedTedarikci.getName(), updatedTedarikci.getPhone(), updatedTedarikci.getRestaurantId(), id);
        if (rows > 0) {
            return ResponseEntity.ok("Tedarikçi başarıyla güncellendi: " + updatedTedarikci.getName());
        } else {
            return ResponseEntity.status(404).body("Tedarikçi bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTedarikci(@PathVariable int id) {
        String sql = "DELETE FROM tedarikci WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("Tedarikçi başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("Tedarikçi bulunamadı!");
        }
    }
}
