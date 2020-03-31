/**
 * Copyright (c) 2019 by Titus Kruse.
 */
package de.tikru.commons.util.config.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Common abstract base element of all my other XML elements.
 *
 * @date 08.05.2019
 * @author Titus Kruse
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public abstract class BaseElement {

}
