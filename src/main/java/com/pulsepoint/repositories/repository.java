package com.pulsepoint.repositories;

import java.util.List;
import java.util.Optional;

/**
 * Generic Repository Interface — PulsePoint
 *
 * Defines standard CRUD operations for all domain entities.
 * Using generics avoids code duplication across entity-specific repositories.
 *
 * T  = The entity type (e.g. Patient, Doctor, Appointment)
 * ID = The type of the entity's unique identifier (e.g. String)
 *
 * This interface abstracts storage details — implementations can use
 * in-memory HashMap, filesystem JSON, SQL database, or REST API
 * without changing any business logic code.
 */
public interface Repository<T, ID> {

    /**
     * Saves an entity. Creates it if it doesn't exist, updates it if it does.
     * @param entity The entity to save
     */
    void save(T entity);

    /**
     * Finds an entity by its unique identifier.
     * @param id The unique identifier of the entity
     * @return An Optional containing the entity if found, or empty if not found
     */
    Optional<T> findById(ID id);

    /**
     * Returns all entities of this type from the storage.
     * @return A list of all entities — empty list if none exist
     */
    List<T> findAll();

    /**
     * Deletes an entity by its unique identifier.
     * @param id The unique identifier of the entity to delete
     */
    void delete(ID id);

    /**
     * Returns the total number of entities in storage.
     * @return Count of stored entities
     */
    int count();

    /**
     * Checks if an entity with the given ID exists in storage.
     * @param id The unique identifier to check
     * @return true if the entity exists, false otherwise
     */
    boolean existsById(ID id);
}