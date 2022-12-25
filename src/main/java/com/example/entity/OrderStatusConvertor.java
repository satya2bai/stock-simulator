package com.example.entity;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConvertor implements AttributeConverter<OrderStatus, String> {

	@Override
	public String convertToDatabaseColumn(OrderStatus status) {
		if (status == null)
			return null;
		return status.getStatus();
	}

	@Override
	public OrderStatus convertToEntityAttribute(String code) {
		if (code == null)
			return null;
		return Stream.of(OrderStatus.values())
				.filter(c -> c.getStatus().equals(code))
				.findFirst().orElseThrow(IllegalArgumentException::new);
	}

}
