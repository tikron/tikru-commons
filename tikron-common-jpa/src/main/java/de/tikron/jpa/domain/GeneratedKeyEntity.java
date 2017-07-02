/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.jpa.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * An entity identified by a unique numeric key. 
 *
 * @date 25.03.2009
 * @author Titus Kruse
 */
@MappedSuperclass
public abstract class GeneratedKeyEntity<ID extends Number> implements Entity<ID>, Versioned, Historical {

	@Id
	@Column
	@GeneratedValue
	protected ID id;

	@Column
	@Version
	protected Integer version;

	@Column(name = "created_on")
	protected LocalDateTime createdOn;

	public GeneratedKeyEntity() {
		this.createdOn = LocalDateTime.now();
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param e The GeneratedKeyEntity to copy from.
	 */
	protected GeneratedKeyEntity(GeneratedKeyEntity<ID> e) {
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
	 * instance of type GeneratedKeyEntity<ID> and the IDs of both entities are equal.
	 * 
	 * @param other The {@link GeneratedKeyEntity<ID>} to compare with this entity.
	 * @return true, if the given entity is equal to this entity.
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof GeneratedKeyEntity<?> && (id != null)) {
			return id.equals(((GeneratedKeyEntity<?>) other).id);
		} else {
			return other == this;
		}
	}

	/**
	 * Returns a hash code for this {@link GeneratedKeyEntity<ID>}.
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
