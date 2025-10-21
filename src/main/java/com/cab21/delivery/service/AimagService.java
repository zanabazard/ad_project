package com.cab21.delivery.service;

import java.util.List;
import java.util.Optional;

import com.cab21.delivery.model.Aimags;

public interface AimagService {
    List<Aimags> list();
    Optional<Aimags> get(Integer id);
    Aimags create(Aimags aimag);
    Aimags update(Integer id, Aimags aimag);
    void delete(Integer id);              
    Aimags setActive(Integer id, boolean active);
}
