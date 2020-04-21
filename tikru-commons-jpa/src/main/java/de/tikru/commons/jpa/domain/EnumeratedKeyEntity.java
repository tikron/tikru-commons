/**
 * Copyright (c) 2012 by Titus Kruse.
 */
package de.tikru.commons.jpa.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An entity identified by a natural key. The key is enumerated.
 * 
 * @param ID The type of the primary key. Should be an enumerated class.
 *
 * @date 01.04.2015
 * @author Titus Kruse
 */
@MappedSuperclass
public abstract class EnumeratedKeyEntity<ID extends Serializable> implements Entity<ID>, Versioned, Historical {

	private static final long serialVersionUID = -3786289914951551438L;

	@Id
	@Column(columnDefinition = "char(64)")
	@Enumerated(EnumType.STRING)
	protected ID id;

	@Column
	@Version
	protected Integer version;

	@Column(name = "created_on")
	protected LocalDateTime createdOn;

	// Required for Hibernate
	public EnumeratedKeyEntity() {
	}

	public EnumeratedKeyEntity(ID id) {
		this.id = id;
		this.createdOn = LocalDateTime.now();
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param e The CharacterKeyEntity to copy from.
	 */
	protected EnumeratedKeyEntity(EnumeratedKeyEntity<ID> e) {
		this.id = e.id;
		this.version = e.version;
		this.createdOn = e.createdOn;
	}

	@Override
	public ID getId() {
		return this.id;
	}

	@Override
	public Integer getVersion() {
		return this.version;
	}

	@Override
	public LocalDateTime getCreatedOn() {
		return this.createdOn;
	}

	/**
	 * Checks whether the given entity is equal to this entity. The two entities are equal, if the given entity is an
	 * instance of type EnumeratedKeyEntity<ID> and the IDs of both entities are equal.
	 * 
	 * @param other The {@link EnumeratedKeyEntity<ID>} to compare with this entity.
	 * @return true, if the given entity is equal to this entity.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof EnumeratedKeyEntity<?>) {
			if (((EnumeratedKeyEntity<?>) other).id.equals(this.id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a hash code for this {@link EnumeratedKeyEntity<ID>}.
	 * 
	 * @return The hash code.
	 */
	@Override
	public int hashCode() {
		return id != null ? this.getClass().hashCode() + id.hashCode() : super.hashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).toString();
	}
}
