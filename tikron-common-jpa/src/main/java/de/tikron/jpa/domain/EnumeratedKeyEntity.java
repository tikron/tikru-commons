/**
 * Copyright (c) 2012 by Titus Kruse.
 */
package de.tikron.jpa.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;

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

	@Id
	@Column(columnDefinition = "char(64)")
	@Enumerated(EnumType.STRING)
	protected ID id;

	@Column
	@Version
	protected Integer version;

	@Column(name = "created_on")
	protected Date createdOn;

	// Required for Hibernate
	public EnumeratedKeyEntity() {
	}

	public EnumeratedKeyEntity(ID id) {
		this.id = id;
		this.createdOn = new Date();
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
	public Date getCreatedOn() {
		return this.createdOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		return other instanceof EnumeratedKeyEntity && (id != null) ? id.equals(((EnumeratedKeyEntity<ID>) other).id)
				: (other == this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id != null ? this.getClass().hashCode() + id.hashCode() : super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).toString();
	}
}
