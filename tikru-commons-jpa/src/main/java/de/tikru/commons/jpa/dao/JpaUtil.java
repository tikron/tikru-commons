/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

/**
 * Some utility methods to use with JPA. 
 *
 * @author Titus Kruse
 * @since 09.04.2015
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
	 * @deprecated PersistenceUnitUtil.getIdentifier() does not always initialize the entity. 
	 * 
	 * @param em The entity manager to use.
	 * @param entity The entity to initialize. Null will be ignored.
	 */
	@Deprecated
	public static void initialize(EntityManager em, Object entity) {
		if (entity != null) {
			em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
		}
	}
	
	/**
	 * Initializes a collection.
	 * 
	 * @deprecated PersistenceUnitUtil.getIdentifier() does not always initialize the entity. 
	 * 
	 * @param em The entity manager to use.
	 * @param collection A collection of entities. Null will be ignored.
	 */
	@Deprecated
	public static void initialize(EntityManager em, Collection<?> collection) {
		if (collection != null) {
			for (Object entity : collection) {
				initialize(em, entity);
			}
		}
	}
	
	/**
	 * Executes the given query and returns the single result or null.
	 * 
	 * This helper method is a replacement for Query.getSingleResult() as it returns null instead of throwing
	 * NoResultException. The method should be used for access by a unique key.
	 * 
	 * @param query The typed query to execute.
	 * @return The single result or null, if no matching entity found.
	 * @throws NonUniqueResultException Thrown, if the query result has more than one entity.
	 */
	public static <E extends Object> E singleResultOrNull(TypedQuery<E> query) {
		List<E> result = query.getResultList();
		if (result.isEmpty())
			return null;
		else if (result.size() == 1)
			return result.get(0);
		else
			throw new NonUniqueResultException();
	}

}
