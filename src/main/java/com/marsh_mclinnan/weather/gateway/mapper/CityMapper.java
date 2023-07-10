package com.marsh_mclinnan.weather.gateway.mapper;

import org.mapstruct.Mapper;

import com.marsh_mclinnan.weather.domain.CityDO;
import com.marsh_mclinnan.weather.gateway.response.CityInfoRsResponse;

@Mapper(componentModel = "spring")
public interface CityMapper {
	
	CityDO responseToDomain(CityInfoRsResponse response);
}
