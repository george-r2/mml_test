package com.marsh_mclinnan.weather.gateway.response;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityInfoRsResponse implements Serializable{

	private static final long serialVersionUID = 3762781798467142456L;
	private String name;
	private BigDecimal lat;
	private BigDecimal lon;
	private String country;
	private String state;
}
