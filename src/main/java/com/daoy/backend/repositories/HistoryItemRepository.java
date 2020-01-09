package com.daoy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daoy.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
