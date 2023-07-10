package com.marsh_mclinnan.weather.gateway;

import java.math.BigDecimal;

import com.marsh_mclinnan.weather.domain.AirPollutionDO;

public interface AirPollutionPort {

	AirPollutionDO getAirPollutionPort(BigDecimal lat, BigDecimal lon);
}
