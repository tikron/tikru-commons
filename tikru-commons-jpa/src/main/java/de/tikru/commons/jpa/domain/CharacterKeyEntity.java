/**
 * Copyright (c) 2012 by Titus Kruse.
 */
package de.tikru.commons.jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * An entity identified by a natural key.
 *
 * @author Titus Kruse
 * @since 21.03.2012
 */
@MappedSuperclass
public abstract class CharacterKeyEntity implements Entity<String>, Versioned, Historical {

	private static final long serialVersionUID = 3323662471048577644L;

	@Id
	@Column(columnDefinition = "char(64)")
	protected String id;

	@Column
	@Version
	protected Integer version;

	@Column(name = "created_on")
	protected LocalDateTime createdOn;

	// Required for Hibernate
	public CharacterKeyEntity() {
	}

	public CharacterKeyEntity(String id) {
		this.id = id;
		this.createdOn = LocalDateTime.now();
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param e The CharacterKeyEntity to copy from.
	 */
	protected CharacterKeyEntity(CharacterKeyEntity e) {
		this.id = e.id;
		this.version = e.version;
		this.createdOn = e.createdOn;
	}

	@Override
	public String getId() {
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
	 * instance of type CharacterKeyEntity and the IDs of both entities are equal.
	 * 
	 * @param other The {@link CharacterKeyEntity} to compare with this entity.
	 * @return true, if the given entity is equal to this entity.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof CharacterKeyEntity) {
			if (((CharacterKeyEntity) other).id.equals(this.id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a hash code for this {@link CharacterKeyEntity}.
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
