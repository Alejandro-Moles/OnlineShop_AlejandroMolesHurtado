package com.javaschool.onlineshop.Repositories;

import com.javaschool.onlineshop.Models.ShopUser;
import com.javaschool.onlineshop.Models.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {
    boolean existsByApartamentAndStreetAndUserAndHome(String apartament,String street, ShopUser user, String home);
}
