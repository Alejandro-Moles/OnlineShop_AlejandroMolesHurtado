package com.javaschool.onlineshop.mapper;

import com.javaschool.onlineshop.dto.RegisterRequestDTO;
import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.models.ShopUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ShopUserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ShopUserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public ShopUserRequestDTO createShopUserDTO(ShopUser shopUser){
        ShopUserRequestDTO shopUserDTO = new ShopUserRequestDTO();
        shopUserDTO.setUuid(shopUser.getUserUuid());
        shopUserDTO.setName(shopUser.getName());
        shopUserDTO.setSurname(shopUser.getSurname());
        shopUserDTO.setMail(shopUser.getMail());
        shopUserDTO.setPassword(shopUser.getPassword());
        shopUserDTO.setBirth(shopUser.getDate());
        shopUserDTO.setIsDeleted(shopUser.isDeleted());
        return shopUserDTO;
    }


    public ShopUser createShopUserEntity(RegisterRequestDTO registerDTO, ShopUser shopUser){
        shopUser.setMail(registerDTO.getMail());
        shopUser.setName(registerDTO.getName());
        shopUser.setSurname(registerDTO.getSurname());
        shopUser.setDate(registerDTO.getBirth());
        shopUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        return shopUser;
    }
}


