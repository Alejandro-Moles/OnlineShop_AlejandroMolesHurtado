package com.javaschool.onlineshop.Mapper;

import com.javaschool.onlineshop.DTO.UserAddressRequestDTO;
import com.javaschool.onlineshop.Models.UserAddress;
import org.springframework.stereotype.Service;

@Service
public class UserAddressMapper {
    public UserAddressRequestDTO createUserAddressDTO(UserAddress userAddress){
        UserAddressRequestDTO userAddressDTO = new UserAddressRequestDTO();
        userAddressDTO.setApartament(userAddress.getApartament());
        userAddressDTO.setHome(userAddress.getHome());
        userAddressDTO.setStreet(userAddress.getStreet());
        userAddressDTO.setIsDeleted(userAddress.getIsDeleted());
        userAddressDTO.setPostalCode(userAddress.getPostal_code().getContent());
        userAddressDTO.setUserMail(userAddress.getUser().getMail());

        return userAddressDTO;
    }
}
