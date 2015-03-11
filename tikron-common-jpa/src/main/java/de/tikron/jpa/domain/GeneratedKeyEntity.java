/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.jpa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Stellt eine Basis-Entität für Tabelle mit generiertem Primärschlüssel zur Verfügung.
 *
 * @date 25.03.2009
 * @author Titus Kruse
 */
@MappedSuperclass
public abstract class GeneratedKeyEntity implements Entity<Long> {

	@Id
	@Column
	@GeneratedValue
	protected Long id;

	@Column
	@Version
	protected Integer version;

	@Column(name = "created_on")
	protected Date createdOn;

	/**
	 * Erstellt eine Entität. Dabei wird der Erstellungszeitpunkt auf die aktuelle Zeit gesetzt.
	 */
	public GeneratedKeyEntity() {
		this.createdOn = new Date();
	}
	
	protected GeneratedKeyEntity(GeneratedKeyEntity e) {
		this.id = e.id;
		this.version = e.version;
		this.createdOn = e.createdOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.freakworm.data.Entity#getId()
	 */
	@Override
	public Long getId() {
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
		return other instanceof GeneratedKeyEntity && (id != null) ? id.equals(((GeneratedKeyEntity) other).id)
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
