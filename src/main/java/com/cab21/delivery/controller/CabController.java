package com.cab21.delivery.controller;

import com.cab21.delivery.dto.CabDto;
import com.cab21.delivery.dto.request.CreateCabRequest;
import com.cab21.delivery.service.CabService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cabs")
@RequiredArgsConstructor
public class CabController {
    private final CabService cabService;

    @PostMapping("create")
    public ResponseEntity<CabDto> create(@RequestBody CreateCabRequest req) {
        return ResponseEntity.ok(cabService.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(cabService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<CabDto>> list() {
        return ResponseEntity.ok(cabService.list());
    }

    @GetMapping("/available")
    public ResponseEntity<List<CabDto>> available(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(value="minSeats", defaultValue = "1") Integer minSeats) {
        return ResponseEntity.ok(cabService.available(start, end, minSeats));
    }
}
