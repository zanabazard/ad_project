package com.cab21.delivery.service.impl;

import com.cab21.delivery.dto.CabDto;
import com.cab21.delivery.dto.request.CreateCabRequest;
import com.cab21.delivery.model.Cab;
import com.cab21.delivery.repository.CabRepository;
import com.cab21.delivery.service.CabService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CabServiceImpl implements CabService {
    private final CabRepository cabRepo;

    @Override
    public CabDto create(CreateCabRequest req) {
        Cab c = new Cab();
        c.setPlate(req.getPlate());
        c.setModel(req.getModel());
        c.setDriverName(req.getDriverName());
        c.setDriverId(req.getDriverId());
        c.setPassengerSeats(req.getPassengerSeats() == null ? 4 : req.getPassengerSeats());
        c.setStatus(1); // active
        cabRepo.save(c);
        return CabDto.from(c);
    }

    @Override
    public CabDto get(Long id) {
        Cab c = cabRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cab not found"));
        return CabDto.from(c);
    }

    @Override
    public List<CabDto> list() {
        return cabRepo.findAll().stream().map(CabDto::from).toList();
    }

    @Override
    public List<CabDto> available(LocalDateTime start, LocalDateTime end, Integer minSeats) {
        return cabRepo.findAvailableCabs(start, end, minSeats)
                .stream()
                .map(CabDto::from)
                .toList();
    }
}
