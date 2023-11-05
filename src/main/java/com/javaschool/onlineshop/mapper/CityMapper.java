package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.CityRequestDTO;
import com.javaschool.onlineshop.models.City;
import org.springframework.stereotype.Service;

@Service
public class CityMapper {
    public CityRequestDTO createCityDTO(City city){
        CityRequestDTO cityDTO = new CityRequestDTO();
        cityDTO.setUuid(city.getCityUuid());
        cityDTO.setName(city.getName());
        cityDTO.setIsDeleted(city.isDeleted());
        cityDTO.setCountryName(city.getCountry().getName());

        return cityDTO;
    }
}
