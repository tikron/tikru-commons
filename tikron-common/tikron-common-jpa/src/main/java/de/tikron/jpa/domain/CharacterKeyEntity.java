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
 * Definiert allen Entitäten gemeinsame Felder.
 *
 * @date 21.03.2012
 * @author Titus Kruse
 */
@MappedSuperclass
public abstract class CharacterKeyEntity implements Entity {

	@Id
	@Column(columnDefinition = "char(64)")
	protected String id;

	@Column
	@Version
	protected Integer version;

	@Column(name = "created_on")
	protected Date createdOn;

	public CharacterKeyEntity() {
		this(null);
	}

	public CharacterKeyEntity(String id) {
		this.id = id;
		this.createdOn = new Date();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.freakworm.data.Entity#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.freakworm.data.Entity#getVersion()
	 */
	@Override
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * Liefert den Zeitpunkt der Erstellung dieser Entität.
	 * 
	 * @return Zeitpunkt der Erstellung.
	 */
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
