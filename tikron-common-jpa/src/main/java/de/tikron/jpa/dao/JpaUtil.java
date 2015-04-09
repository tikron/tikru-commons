/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.jpa.dao;

import java.util.Collection;

import javax.persistence.EntityManager;

/**
 * Some utility methods to use with JPA. 
 *
 * @date 09.04.2015
 * @author Titus Kruse
 */
public class JpaUtil {
	
	/**
	 * Checks whether the given object is initialized.
	 * 
	 * @param em The entity manager to use.
	 * @param entitiy The object to check.
	 * @return true, if the eobject is initialized.
	 */
	public static boolean isLoaded(EntityManager em, Object entitiy) {
		return em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(entitiy);
	}
	
	/**
	 * Initializes an entity.
	 * 
	 * @param em The entity manager to use.
	 * @param entity The entity to initialize. Null will be ignored.
	 */
	public static void initialize(EntityManager em, Object entity) {
		if (entity != null) {
			em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
		}
	}
	
	/**
	 * Initializes a collection.
	 * 
	 * @param em The entity manager to use.
	 * @param collection A collection of entities. Null will be ignored.
	 */
	public static void initialize(EntityManager em, Collection<?> collection) {
		if (collection != null) {
			for (Object entity : collection) {
				initialize(em, entity);
			}
		}
	}

}
