package de.tikru.commons.jpa.domain;

import java.io.Serializable;

/**
 * Declares an enity that could be shown to a human person. Entity classes implementing this interface shall only select
 * or combine appropriate properties to return and don't do any formatting or translation. Otherwise the MVC pattern
 * will be violeted. For example a cars display name is composed by manufactorer and model name.
 * 
 * @param <ID> The type of primary key.
 *
 * @author Titus Kruse
 * @since 28.04.2015
 */
public interface ShowableEntity<ID extends Serializable> extends Entity<ID>, Showable {

}
