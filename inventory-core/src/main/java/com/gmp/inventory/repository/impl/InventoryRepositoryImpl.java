package com.gmp.inventory.repository.impl;

import com.gmp.inventory.persistence.model.Inventory;
import com.gmp.inventory.repository.interfaces.InventoryCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom repository implementation for Inventory with complex queries
 * 
 * @author Mrigank Tandon
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Inventory> findInventoriesWithFilters(Integer companyId, 
                                                       Integer branchId, 
                                                       String category, 
                                                       String status, 
                                                       String tenant) {
        log.debug("Finding inventories with filters - companyId: {}, branchId: {}, category: {}, status: {}, tenant: {}", 
                  companyId, branchId, category, status, tenant);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Inventory> query = cb.createQuery(Inventory.class);
        Root<Inventory> root = query.from(Inventory.class);

        List<Predicate> predicates = new ArrayList<>();

        // Always filter by tenant and not deleted
        predicates.add(cb.equal(root.get("tenant"), tenant));
        predicates.add(cb.equal(root.get("deleted"), 0));

        // Add optional filters
        if (companyId != null) {
            predicates.add(cb.equal(root.get("companyId"), companyId));
        }

        if (branchId != null) {
            predicates.add(cb.equal(root.get("branchId"), branchId));
        }

        if (category != null && !category.isEmpty()) {
            predicates.add(cb.equal(root.get("category"), category));
        }

        if (status != null && !status.isEmpty()) {
            predicates.add(cb.equal(root.get("inventoryStatus"), status));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("createdAt")));

        TypedQuery<Inventory> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
