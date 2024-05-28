package com.aplazo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aplazo.model.entity.PurchaseEntity;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}
