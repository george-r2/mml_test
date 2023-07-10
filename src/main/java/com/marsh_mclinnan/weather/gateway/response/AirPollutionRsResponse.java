package com.marsh_mclinnan.weather.gateway.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirPollutionRsResponse {

	private List<AirPollutionItemRsResponse> list;
}
