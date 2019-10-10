/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikru.commons.jpa.dao;

import java.io.Serializable;
import java.util.List;

import de.tikru.commons.jpa.domain.Entity;

/**
 * Deklariert nützliche Methoden, die für alle DAOs verwendet werden können.
 * 
 * @author Titus Kruse
 */
public interface GenericDao<T extends Entity<ID>, ID extends Serializable> {

	/**
	 * Eine Entität auf Basis des Primärschlüssel holen.
	 * 
	 * @param id Der Primärschlüssel.
	 * @return Die Entität.
	 */
	public T findById(ID id);

	/**
	 * Eine Liste aller Entitäten holen.
	 * 
	 * @return Die Liste der Entitäten.
	 */
	public List<T> findAll();

	/**
	 * Liefert die Entität mit der ID id und den angegebenen abhängigen Entitäten.
	 * 
	 * @param id Das ID Objekt.
	 * @param fetchRelations Name(n) der abhängigen Entitäten.
	 * 
	 * @return Die gefundene Entität oder null, falls sie nicht existiert.
	 * 
	 * @see http://jdevelopment.nl/fetching-arbitrary-object-graphs-jpa-2/
	 */
	public T findWithDepth(ID id, String... fetchRelations);

	/**
	 * Eine Entität auf Basis des Primärschlüssel holen.
	 * 
	 * @param id Der Primärschlüssel.
	 * @return Die Entität.
	 */
	public T getReference(ID id);

	/**
	 * Eine Entität hinzufügen.
	 * 
	 * @param entity Die Entität.
	 */
	public T insert(T entity);

	/**
	 * Eine Entität aktualisieren.
	 * 
	 * @param entity Die Entität.
	 */
	public T update(T entity);

	/**
	 * Eine Entität speichern.
	 * 
	 * @param entity Die Entität.
	 */
	public T save(T entity);

	/**
	 * Eine Entität löschen.
	 * 
	 * @param entity Die Entität.
	 */
	public void delete(T entity);
}
