package com.marsh_mclinnan.weather.gateway;

import java.util.List;

import com.marsh_mclinnan.weather.domain.CityDO;

public interface CitiesPort {

	List<CityDO> getCityInfo(String cityName);
}
