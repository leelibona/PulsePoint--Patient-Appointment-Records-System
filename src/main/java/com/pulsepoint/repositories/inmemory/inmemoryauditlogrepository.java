package com.pulsepoint.repositories.inmemory;

import com.pulsepoint.models.AuditLog;
import com.pulsepoint.repositories.AuditLogRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * In-Memory implementation of AuditLogRepository.
 * AuditLogs are immutable — delete throws UnsupportedOperationException.
 */
public class InMemoryAuditLogRepository implements AuditLogRepository {

    private final Map<String, AuditLog> storage = new HashMap<>();

    @Override
    public void save(AuditLog log) {
        if (log == null || log.getLogId() == null) {
            throw new IllegalArgumentException("AuditLog and Log ID cannot be null.");
        }
        storage.put(log.getLogId(), log);
        System.out.println("Audit log saved: " + log.getLogId());
    }

    @Override
    public Optional<AuditLog> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<AuditLog> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int count() { return storage.size(); }

    @Override
    public boolean existsById(String id) { return storage.containsKey(id); }

    @Override
    public List<AuditLog> findByUserId(String userId) {
        return storage.values().stream()
            .filter(l -> l.getUserId().equals(userId))
            .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByAffectedRecordId(String recordId) {
        return storage.values().stream()
            .filter(l -> l.getAffectedRecordId().equals(recordId))
            .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByStatus(String status) {
        return storage.values().stream()
            .filter(l -> l.getStatus().equalsIgnoreCase(status))
            .collect(Collectors.toList());
    }
}