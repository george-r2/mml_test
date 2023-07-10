package com.marsh_mclinnan.weather.gateway.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.marsh_mclinnan.weather.domain.WeatherCurrentDO;
import com.marsh_mclinnan.weather.domain.WeatherForecastDO;
import com.marsh_mclinnan.weather.domain.WeatherMainCurrentDO;
import com.marsh_mclinnan.weather.domain.WeatherSummaryDO;
import com.marsh_mclinnan.weather.domain.WeatherSysDO;
import com.marsh_mclinnan.weather.domain.WeatherWindDO;
import com.marsh_mclinnan.weather.gateway.response.WeatherCurrentRsResponse;
import com.marsh_mclinnan.weather.gateway.response.WeatherForecastRsResponse;
import com.marsh_mclinnan.weather.gateway.response.WeatherMainRsResponse;
import com.marsh_mclinnan.weather.gateway.response.WeatherSummaryRsResponse;
import com.marsh_mclinnan.weather.gateway.response.WeatherSysRsResponse;
import com.marsh_mclinnan.weather.gateway.response.WeatherWindRsResponse;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
	WeatherForecastDO responseToDomain(WeatherForecastRsResponse response);

	@Mapping(source="pop", target="probabilityOfPrecipitation")
	WeatherCurrentDO responseToDomain(WeatherCurrentRsResponse response);

	WeatherSummaryDO responseToDomain(WeatherSummaryRsResponse response);

	WeatherMainCurrentDO responseToDomain(WeatherMainRsResponse response);

	WeatherWindDO responseToDomain(WeatherWindRsResponse response);
	
	WeatherSysDO responseToDomain(WeatherSysRsResponse sys);
}
