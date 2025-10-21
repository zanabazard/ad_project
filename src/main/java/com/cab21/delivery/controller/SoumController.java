package com.cab21.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cab21.delivery.model.Soum;
import com.cab21.delivery.service.SoumService;

@RestController
@RequestMapping("/api/soums")
public class SoumController {

    private final SoumService service;

    public SoumController(SoumService service) {
        this.service = service;
    }

    /***
     * 	   Сумын жагсаалт авах сервис
     */
    @GetMapping
    public List<Soum> list(@RequestParam(required = false) String parent) {
        return service.list(parent);
    }

    /***
     * 	   Сумын мэдээлэл авах сервис
     */
    @GetMapping("/{id}")
    public ResponseEntity<Soum> get(@PathVariable Integer id) {
        return service.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /***
     * 	   Сумын шинээр үүсгэх сервис
     */
    @PostMapping("create")
    public ResponseEntity<Soum> create( @RequestBody Soum soum) {
        return ResponseEntity.ok(service.create(soum));
    }

    /***
     * 	   Сумын мэдээлэл шинэчлэх сервис
     */
    @PutMapping("/{id}")
    public ResponseEntity<Soum> update(@PathVariable Integer id,
                                       @RequestBody Soum soum) {
        return ResponseEntity.ok(service.update(id, soum));
    }

    /***
     * 	   Сум устгах сервис
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
