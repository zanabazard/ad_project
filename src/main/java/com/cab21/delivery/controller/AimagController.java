package com.cab21.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cab21.delivery.model.Aimags;
import com.cab21.delivery.service.AimagService;

@RestController
@RequestMapping("/api/aimags")
public class AimagController {

    private final AimagService service;

    public AimagController(AimagService service) {
        this.service = service;
    }

    
    /***
     * 	   Аймгийн мэдээлэл авах сервис
     * 
     */
    @GetMapping
    public List<Aimags> list() {
        return service.list();
    }

    
    /***
     * 	   Аймгийн дэлгэрэнгүй мэдээлэл авах сервис
     */
    @GetMapping("/{id}")
    public ResponseEntity<Aimags> get(@PathVariable Integer id) {
        return service.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    /***
     * 	   Аймаг шинээр нэмэх сервис
     */
    @PostMapping("/create")
    public ResponseEntity<Aimags> create(@RequestBody Aimags aimag) {
        return ResponseEntity.ok(service.create(aimag));
    }

    
    /***
     * 	   Аймаг мэдээлэл шинэчлэх сервис
     */
    @PutMapping("/{id}")
    public ResponseEntity<Aimags> update(@PathVariable Integer id,
                                        @RequestBody Aimags aimag) {
        return ResponseEntity.ok(service.update(id, aimag));
    }

    
    /***
     * 	   Аймаг устгах сервис
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    
    /***
     * 	   Аймаг идэвхжүүлэх/идэвхгүйжүүлэх сервис
     */
    @PatchMapping("/{id}/active")
    public ResponseEntity<Aimags> setActive(@PathVariable Integer id,
                                           @RequestParam("value") boolean active) {
        return ResponseEntity.ok(service.setActive(id, active));
    }
}
