/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikru.commons.jpa.dao;

import java.io.Serializable;
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

import de.tikru.commons.jpa.domain.Entity;

/**
 * Implementiert die Basismethoden für den Zugriff per JPA.
 * 
 * @author Titus Kruse
 */
public abstract class GenericJpaDao<T extends Entity<ID>, ID extends Serializable> implements GenericDao<T, ID> {

	protected EntityManager entityManager;

	private Class<T> clazz;

	public GenericJpaDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public GenericJpaDao(Class<T> clazz, EntityManager entityManager) {
		this.clazz = clazz;
		this.entityManager = entityManager;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public T findById(ID id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public List<T> findAll() {
		return entityManager.createNamedQuery(clazz.getSimpleName() + ".findAll", clazz).getResultList();
	}

	@Override
	public T getReference(ID id) {
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
	public T save(T entity) {
		if (entity.getId() == null)
			return insert(entity);
		else {
			return update(entity);
		}
	}

	@Override
	public void delete(T entity) {
		// entityManager.remove(entityManager.merge(entity));
		entityManager.remove(getReference(getId(entity)));
	}

	@Override
	public T findWithDepth(ID id, String... fetchRelations) {
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
	 * Returns the ID (primary key) of the given entity.
	 * 
	 * @param entity The entity.
	 * @return The ID.
	 */
	protected ID getId(T entity) {
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
		return JpaUtil.singleResultOrNull(query);
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