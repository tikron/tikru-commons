/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikron.jpa.dao;

import java.util.List;

import de.tikron.jpa.domain.Entity;

/**
 * Deklariert nützliche Methoden, die für alle DAOs verwendet werden können.
 * 
 * @author Titus Kruse
 */
public interface Dao<T extends Entity> {

	/**
	 * Eine Entität auf Basis des Primärschlüssel holen.
	 * 
	 * @param id Der Primärschlüssel.
	 * @return Die Entität.
	 */
	public T findById(Object id);

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
	public T findWithDepth(Object id, String... fetchRelations);

	/**
	 * Eine Entität auf Basis des Primärschlüssel holen.
	 * 
	 * @param id Der Primärschlüssel.
	 * @return Die Entität.
	 */
	public T getReference(Object id);

	/**
	 * Eine Entität speichern.
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
	 * Eine Entität löschen.
	 * 
	 * @param entity Die Entität.
	 */
	public void delete(T entity);
}
