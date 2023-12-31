package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.PostalCodeRequestDTO;
import com.javaschool.onlineshop.models.PostalCodeModel;
import org.springframework.stereotype.Service;

@Service
public class PostalCodeMapper {
    public PostalCodeRequestDTO createPostalCodeDTO(PostalCodeModel postalCode){
        PostalCodeRequestDTO postalDTO = new PostalCodeRequestDTO();
        postalDTO.setUuid(postalCode.getPostalCodeUuid());
        postalDTO.setContent(postalCode.getContent());
        postalDTO.setIsDeleted(postalCode.isDeleted());
        postalDTO.setCityName(postalCode.getCity().getName());
        postalDTO.setCountryName(postalCode.getCity().getCountry().getName());

        return postalDTO;
    }
}
