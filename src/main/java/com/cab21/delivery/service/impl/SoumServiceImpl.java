package com.cab21.delivery.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cab21.delivery.model.Soum;
import com.cab21.delivery.repository.AimagRepository;
import com.cab21.delivery.repository.SoumRepository;
import com.cab21.delivery.service.SoumService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SoumServiceImpl implements SoumService {

    private final SoumRepository repo;
    private final AimagRepository aimagRepo;

    public SoumServiceImpl(SoumRepository repo, AimagRepository aimagRepo) {
        this.repo = repo;
        this.aimagRepo = aimagRepo;
    }

    @Override
    public List<Soum> list(String parent) {
        if (parent != null && !parent.isEmpty()) {
            return repo.findByParentId(parent);
        }
        return repo.findAll();
    }

    @Override
    public Optional<Soum> get(Integer id) {
        return repo.findById(id);
    }

    @Override
    public Soum create(Soum soum) {
        if (!aimagRepo.existsByValue(soum.getParentId())) {
            throw new EntityNotFoundException("Parent aimag (value=" + soum.getParentId() + ") not found");
        }
        return repo.save(soum);
    }

    @Override
    public Soum update(Integer id, Soum patch) {
        Soum existing = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Soum not found: " + id));

        if (patch.getName() != null) existing.setName(patch.getName());

        if (patch.getValue() != null && !patch.getValue().equals(existing.getValue())) {
            existing.setValue(patch.getValue());
        }

        if (patch.getParentId() != null && !patch.getParentId().equals(existing.getParentId())) {
            if (!aimagRepo.existsByValue(patch.getParentId())) {
                throw new EntityNotFoundException("Parent aimag (value=" + patch.getParentId() + ") not found");
            }
            existing.setParentId(patch.getParentId());
        }

        return repo.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("Soum not found: " + id);
        }
        repo.deleteById(id);
    }
}
