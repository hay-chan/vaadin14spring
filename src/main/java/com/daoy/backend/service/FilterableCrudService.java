package com.daoy.backend.service;

import java.util.Optional;

import com.daoy.backend.data.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterableCrudService<T extends AbstractEntity> extends CrudService<T> {

	Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

	long countAnyMatching(Optional<String> filter);

}
