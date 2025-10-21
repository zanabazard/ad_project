package com.cab21.delivery.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.cab21.delivery.model.Aimags;
import com.cab21.delivery.repository.AimagRepository;
import com.cab21.delivery.service.AimagService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AimagServiceImpl implements AimagService {

    private final AimagRepository repo;

    public AimagServiceImpl(AimagRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Aimags> list() {
        return repo.findAll();
    }

    @Override
    public Optional<Aimags> get(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Aimags create(Aimags aimag) {
        if (repo.existsByValue(aimag.getValue())) {
            throw new DuplicateKeyException("value must be unique: " + aimag.getValue());
        }
        // default active if null
        if (aimag.getIsActive() == null) {
            aimag.setIsActive(1);
        }
        return repo.save(aimag);
    }

    @Override
    public Aimags update(Integer id, Aimags patch) {
        Aimags existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aimag not found: " + id));

        if (patch.getName() != null) existing.setName(patch.getName());
        if (patch.getValue() != null) {
            // keep uniqueness
            if (!patch.getValue().equals(existing.getValue()) && repo.existsByValue(patch.getValue())) {
                throw new DuplicateKeyException("value must be unique: " + patch.getValue());
            }
            existing.setValue(patch.getValue());
        }
        if (patch.getIsActive() != null) existing.setIsActive(patch.getIsActive());

        return repo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Aimag not found: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public Aimags setActive(Integer id, boolean active) {
        Aimags existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aimag not found: " + id));
        existing.setIsActive(active ? 1 : 0);
        return repo.save(existing);
    }

}
