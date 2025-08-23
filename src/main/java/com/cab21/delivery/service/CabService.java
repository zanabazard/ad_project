package com.cab21.delivery.service;

import com.cab21.delivery.dto.CabDto;
import com.cab21.delivery.dto.request.CreateCabRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface CabService {
    CabDto create(CreateCabRequest req);
    CabDto get(Long id);
    List<CabDto> list();
    List<CabDto> available(LocalDateTime start,LocalDateTime end,Integer minSeats);
}
