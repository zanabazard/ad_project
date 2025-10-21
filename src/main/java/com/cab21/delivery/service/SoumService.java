package com.cab21.delivery.service;

import java.util.List;
import java.util.Optional;

import com.cab21.delivery.model.Soum;

public interface SoumService {
    List<Soum> list(String parent);
    Optional<Soum> get(Integer id);
    Soum create(Soum soum);
    Soum update(Integer id, Soum soum);
    void delete(Integer id);
}
