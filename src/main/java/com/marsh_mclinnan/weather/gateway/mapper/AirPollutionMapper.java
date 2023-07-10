package com.marsh_mclinnan.weather.gateway.mapper;

import org.mapstruct.Mapper;

import com.marsh_mclinnan.weather.domain.AirPollutionComponentsDO;
import com.marsh_mclinnan.weather.domain.AirPollutionDO;
import com.marsh_mclinnan.weather.domain.AirPollutionItemDO;
import com.marsh_mclinnan.weather.domain.AirPollutionMainDO;
import com.marsh_mclinnan.weather.gateway.response.AirPollutionComponentsRsResponse;
import com.marsh_mclinnan.weather.gateway.response.AirPollutionItemRsResponse;
import com.marsh_mclinnan.weather.gateway.response.AirPollutionMainRsResponse;
import com.marsh_mclinnan.weather.gateway.response.AirPollutionRsResponse;

@Mapper(componentModel = "spring")
public interface AirPollutionMapper {
	
	AirPollutionDO responseToDomain(AirPollutionRsResponse response);

	AirPollutionItemDO responseToDomain(AirPollutionItemRsResponse response);

	AirPollutionMainDO responseToDomain(AirPollutionMainRsResponse response);
	
	AirPollutionComponentsDO responseToDomain(AirPollutionComponentsRsResponse response);
}
