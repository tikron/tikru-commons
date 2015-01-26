/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikron.jpa.dao;

import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.FetchParent;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import de.tikron.jpa.domain.Entity;

/**
 * Implementiert die Basismethoden für den Zugriff per JPA.
 * 
 * @author Titus Kruse
 */
public abstract class BaseJpaDao<T extends Entity> implements Dao<T> {

	protected EntityManager entityManager;

	private Class<T> clazz;

	public BaseJpaDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public BaseJpaDao(Class<T> clazz, EntityManager entityManager) {
		this.clazz = clazz;
		this.entityManager = entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public T findById(Object id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public List<T> findAll() {
		return entityManager.createNamedQuery(clazz.getSimpleName() + ".findAll", clazz).getResultList();
	}

	@Override
	public T getReference(Object id) {
		return entityManager.getReference(clazz, id);
	}

	@Override
	public T insert(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		return entityManager.merge(entity);
	}

	@Override
	public void delete(T entity) {
		// entityManager.remove(entityManager.merge(entity));
		entityManager.remove(getReference(getId(entity)));
	}

	@Override
	public T findWithDepth(Object id, String... fetchRelations) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		// for (String relation : fetchRelations) {
		// FetchParent<T, T> fetch = root;
		// for (String pathSegment : relation.split(quote("."))) {
		// fetch = fetch.fetch(pathSegment, JoinType.LEFT);
		// }
		// }

		for (String relation : fetchRelations) {
			FetchParent<T, T> fetch = root;
			StringTokenizer token = new StringTokenizer(relation, ".");
			for (; token.hasMoreTokens();) {
				String pathSegment = token.nextToken();
				fetch = fetch.fetch(pathSegment, JoinType.LEFT);
			}
		}

		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);

		return singleResultOrNull(query);
	}

	/**
	 * Returns the Object (primary key) of the given entity.
	 * 
	 * @param entity The entity.
	 * @return The Object.
	 */
	protected Object getId(T entity) {
		return entity.getId();
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
	protected T singleResultOrNull(TypedQuery<T> query) {
		List<T> result = query.getResultList();
		if (result.isEmpty())
			return null;
		else if (result.size() == 1)
			return result.get(0);
		else
			throw new NonUniqueResultException();
	}

	/**
	 * Returns the first entity of a query result. Additionaly this method is a helper method to transform a list result
	 * with at least one element to a single entity.
	 * 
	 * @param query The typed query to execute.
	 * @return The first entity of the query result or null, if the result is empty.
	 */
	protected T firstResult(TypedQuery<T> query) {
		List<T> result = query.getResultList();
		return (result.isEmpty() ? null : result.get(0));
	}

	/**
	 * Returns the last entity of a query result.
	 * 
	 * @param query The typed query to execute.
	 * @return The last entity of the query result or null, if the result is empty.
	 */
	protected T lastResult(TypedQuery<T> query) {
		List<T> result = query.getResultList();
		return (result.isEmpty() ? null : result.get(result.size() - 1));
	}
}