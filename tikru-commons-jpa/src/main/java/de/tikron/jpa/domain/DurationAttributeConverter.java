package de.tikron.jpa.domain;

import java.time.Duration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA attribute converter converting between {@link java.lang.Long} and {@link java.time.Duration}. The duration in persistence layer is assumed as seconds instead of nanos, because seconds are more handy. 
 *
 * @date 01.08.2017
 * @author Titus Kruse
 */
@Converter
public class DurationAttributeConverter implements AttributeConverter<Duration, Long> {

	@Override
	public Long convertToDatabaseColumn(Duration localTime) {
		return (localTime == null ? null : localTime.getSeconds());
	}

	@Override
	public Duration convertToEntityAttribute(Long seconds) {
		return (seconds == null ? null : Duration.ofSeconds(seconds));
	}
}
