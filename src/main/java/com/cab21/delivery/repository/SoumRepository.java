package com.cab21.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cab21.delivery.model.Soum;

public interface SoumRepository extends JpaRepository<Soum, Integer> {
    boolean existsByValue(String value);
    List<Soum> findByParentId(String parent);
}
