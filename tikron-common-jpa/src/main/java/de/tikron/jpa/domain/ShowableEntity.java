package de.tikron.jpa.domain;

import java.io.Serializable;

/**
 * Declares an enity that could be shown to a human person.
 * 
 * @param <ID> The type of primary key.
 *
 * @date 28.04.2015
 * @author Titus Kruse
 */
public interface ShowableEntity<ID extends Serializable> extends Entity<ID>, Showable {

}
