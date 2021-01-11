/**
 * Copyright (c) 2010 by Titus Kruse.
 */
package de.tikru.commons.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Provides methods to execute a query directly without defining it as named query.
 *
 * @since 06.10.2010
 * @author Titus Kruse
 */
public class DataAccessHelper {

	protected EntityManager entityManager;

	@SuppressWarnings("rawtypes")
	public List executeQuery(String queryStatement) {
		return executeQuery(queryStatement, null);
	}

	@SuppressWarnings("rawtypes")
	public List executeQuery(String queryStatement, Object[] params) {
		Query query = entityManager.createQuery(queryStatement);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
		return query.getResultList();
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
