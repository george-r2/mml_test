package com.marsh_mclinnan.weather.gateway;

import java.math.BigDecimal;

import com.marsh_mclinnan.weather.domain.AirPollutionDO;

public interface AirPollutionGateway {

	AirPollutionDO getAirPollutionPort(BigDecimal lat, BigDecimal lon) ;
}
