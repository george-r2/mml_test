package com.marsh_mclinnan.weather.gateway;

import java.util.List;

import com.marsh_mclinnan.weather.domain.CityDO;

public interface CitiesGateway {

	List<CityDO> getCityInfo(String cityName);
}
