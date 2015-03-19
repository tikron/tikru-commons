/**
 * Copyright (c) 2012 by Titus Kruse.
 */
package de.tikron.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * An entity identified by a natural key.
 *
 * @date 21.03.2012
 * @author Titus Kruse
 */
@MappedSuperclass
public abstract class CharacterKeyEntity implements Entity<String>, Versioned, Historical {

	@Id
	@Column(columnDefinition = "char(64)")
	protected String id;

	@Column
	@Version
	protected Integer version;

	@Column(name = "created_on")
	protected Date createdOn;

	// Required for Hibernate
	public CharacterKeyEntity() {
	}

	public CharacterKeyEntity(String id) {
		this.id = id;
		this.createdOn = new Date();
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
	public Date getCreatedOn() {
		return this.createdOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals()
	 */
	@Override
	public boolean equals(Object other) {
		return other instanceof CharacterKeyEntity && (id != null) ? id.equals(((CharacterKeyEntity) other).id)
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
