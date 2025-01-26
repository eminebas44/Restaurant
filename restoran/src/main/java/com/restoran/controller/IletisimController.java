package com.restoran.controller;

import com.restoran.entity.Iletisim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iletisim")
public class IletisimController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createIletisim(@RequestBody Iletisim iletisim) {
        String sql = "INSERT INTO iletisim (user_id, restaurant_id, mesaj) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql, iletisim.getUserId(), iletisim.getRestaurantId(), iletisim.getMesaj());
        if (rows > 0) {
            return ResponseEntity.ok("İletişim mesajı başarıyla eklendi.");
        } else {
            return ResponseEntity.status(500).body("İletişim mesajı eklenemedi.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Iletisim>> getAllIletisim() {
        String sql = "SELECT * FROM iletisim";
        List<Iletisim> iletisimList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Iletisim iletisim = new Iletisim();
            iletisim.setId(rs.getInt("id"));
            iletisim.setUserId(rs.getInt("user_id"));
            iletisim.setRestaurantId(rs.getInt("restaurant_id"));
            iletisim.setMesaj(rs.getString("mesaj"));
            return iletisim;
        });
        return ResponseEntity.ok(iletisimList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIletisimById(@PathVariable int id) {
        String sql = "SELECT * FROM iletisim WHERE id = ?";
        List<Iletisim> iletisimList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Iletisim iletisim = new Iletisim();
            iletisim.setId(rs.getInt("id"));
            iletisim.setUserId(rs.getInt("user_id"));
            iletisim.setRestaurantId(rs.getInt("restaurant_id"));
            iletisim.setMesaj(rs.getString("mesaj"));
            return iletisim;
        }, id);

        if (!iletisimList.isEmpty()) {
            return ResponseEntity.ok(iletisimList.get(0));
        } else {
            return ResponseEntity.status(404).body("İletişim bulunamadı!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateIletisim(@PathVariable int id, @RequestBody Iletisim updatedIletisim) {
        String sql = "UPDATE iletisim SET user_id = ?, restaurant_id = ?, mesaj = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql, updatedIletisim.getUserId(), updatedIletisim.getRestaurantId(), updatedIletisim.getMesaj(), id);
        if (rows > 0) {
            return ResponseEntity.ok("İletişim başarıyla güncellendi.");
        } else {
            return ResponseEntity.status(404).body("İletişim bulunamadı!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteIletisim(@PathVariable int id) {
        String sql = "DELETE FROM iletisim WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);
        if (rows > 0) {
            return ResponseEntity.ok("İletişim başarıyla silindi!");
        } else {
            return ResponseEntity.status(404).body("İletişim bulunamadı!");
        }
    }
}
