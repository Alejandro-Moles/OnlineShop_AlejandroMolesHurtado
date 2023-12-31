package com.javaschool.onlineshop.services;

import com.javaschool.onlineshop.dto.RoleRequestDTO;
import com.javaschool.onlineshop.exception.ResourceDuplicate;
import com.javaschool.onlineshop.mapper.RoleMapper;
import com.javaschool.onlineshop.models.RoleModel;
import com.javaschool.onlineshop.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    // Maps a RoleModel to a RoleRequestDTO
    private RoleRequestDTO createRoleDTO(RoleModel role) {
        return roleMapper.createRoleDTO(role);
    }

    // Creates a RoleModel entity from a RoleRequestDTO
    private RoleModel createRoleEntity(RoleRequestDTO roleDTO, RoleModel role) {
        role.setType(roleDTO.getType());
        role.setDeleted(false);
        return role;
    }

    // Retrieves all roles from the database
    @Transactional(readOnly = true)
    public List<RoleRequestDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(this::createRoleDTO).toList();
    }

    // Saves a new role to the database
    @Transactional
    public RoleRequestDTO saveRole(RoleRequestDTO roleDTO) {
        RoleModel role = createRoleEntity(roleDTO, new RoleModel());
        if (roleRepository.existsByType(role.getType())) {
            throw new ResourceDuplicate("Role already exists");
        }
        roleRepository.save(role);
        return createRoleDTO(role);
    }
}
