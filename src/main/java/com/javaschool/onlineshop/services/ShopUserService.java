package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.NewPasswordDTO;
import com.javaschool.onlineshop.dto.RegisterRequestDTO;
import com.javaschool.onlineshop.dto.ShopUserRequestDTO;
import com.javaschool.onlineshop.dto.UserStatisticsDTO;
import com.javaschool.onlineshop.exception.NoExistData;
import com.javaschool.onlineshop.exception.OldPasswordNotSame;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.ShopUserMapper;
import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.models.ShopUserModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import com.javaschool.onlineshop.repositories.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopUserService {

    private final ShopUserRepository shopUserRepository;
    private final ShopUserMapper shopUserMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private ShopUserRequestDTO createShopUserDTO(ShopUserModel shopUser){
        return shopUserMapper.createShopUserDTO(shopUser);
    }

    public ShopUserModel createShopUserEntity(ShopUserRequestDTO shopUserDTO, ShopUserModel shopUser){
        shopUser.setName(shopUserDTO.getName());
        shopUser.setDeleted(shopUserDTO.getIsDeleted());
        shopUser.setMail(shopUserDTO.getMail());
        shopUser.setDate(shopUserDTO.getBirth());
        shopUser.setSurname(shopUserDTO.getSurname());
        return shopUser;
    }

    public ShopUserRequestDTO getShopUserbyUuid(UUID uuid){
        ShopUserModel shopUser = loadShopUser(uuid);
        return createShopUserDTO(shopUser);
    }

    private boolean checkNewPassword(UUID uuid, NewPasswordDTO passwordDTO){
        ShopUserModel userModel = loadShopUser(uuid);
        if(passwordEncoder.matches(passwordDTO.getOldPassword(), userModel.getPassword())){
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public ShopUserRequestDTO saveShopUser(ShopUserRequestDTO shopUserDTO){
        ShopUserModel shopUser = createShopUserEntity(shopUserDTO, new ShopUserModel());
        if (shopUserRepository.existsByMail(shopUser.getMail())) {
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }
        shopUserRepository.save(shopUser);
        return createShopUserDTO(shopUser);
    }

    @Transactional
    public void updateShopUser(UUID uuid, NewPasswordDTO passwordDTO){
        ShopUserModel shopUser = loadShopUser(uuid);
        if(checkNewPassword(uuid, passwordDTO)){
            String newPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
            shopUser.setPassword(newPassword);
            shopUserRepository.save(shopUser);
        }else{
            throw new OldPasswordNotSame("The old password is not the same");
        }
    }

    @Transactional(readOnly = true)
    public List<ShopUserRequestDTO> getAllShopUser(){
        return shopUserRepository.findAll().stream().map(this::createShopUserDTO).toList();
    }

    @Transactional(readOnly = true)
    private ShopUserModel loadShopUser(UUID uuid){
        return shopUserRepository.findById(uuid).orElseThrow(() -> new NoExistData("Shop user don't exist"));
    }

    @Transactional
    public ShopUserRequestDTO registerShopUser(RegisterRequestDTO registerDTO){
        if(shopUserRepository.existsByMail(registerDTO.getMail())){
            throw new ResourceDuplicate("Shop User already exists with that mail");
        }

        ShopUserModel user = shopUserMapper.createShopUserEntity(registerDTO, new ShopUserModel());
        RoleModel roles = roleRepository.findByType("USER").get();
        user.setRoles(Collections.singletonList(roles));
        shopUserRepository.save(user);
        return createShopUserDTO(user);
    }

    @Transactional(readOnly = true)
    public ShopUserRequestDTO getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new NoExistData("User not found");
        }
        String mail = authentication.getName();
        ShopUserModel user = shopUserRepository.findByMail(mail).orElseThrow(() -> new NoExistData("User not found"));
        return shopUserMapper.createShopUserDTO(user);
    }

    @Transactional(readOnly = true)
    public UserStatisticsDTO getUserStatistic(String userMail){
        Optional<ShopUserModel> shopUserModel = shopUserRepository.findByMail(userMail);
        if (shopUserModel.isPresent()) {
            ShopUserModel userModel = shopUserModel.get();
            if (userModel.getOrders() != null && !userModel.getOrders().isEmpty()) {
                return shopUserRepository.getUserStatistics(userModel.getUserUuid());
            } else {
                return new UserStatisticsDTO(0L, 0.0, 0L);
            }
        } else {
            return null;
        }
    }

    @Transactional
    public void assignRolesToUser(UUID userUuid, List<String> roleTypes) {
        ShopUserModel shopUser = loadShopUser(userUuid);
        List<RoleModel> roles = roleRepository.findByTypeIn(roleTypes);
        if (roles.isEmpty()) {
            throw new NoExistData("No roles found with the provided types");
        }

        shopUser.setRoles(roles);
        shopUserRepository.save(shopUser);

        createShopUserDTO(shopUser);
    }
}
