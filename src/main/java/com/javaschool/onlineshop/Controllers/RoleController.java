package com.javaschool.onlineshop.Controllers;

import com.javaschool.onlineshop.DTO.RoleRequestDTO;
import com.javaschool.onlineshop.Services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody RoleRequestDTO roleDTO) {
        RoleRequestDTO result = roleService.saveRole(roleDTO);
        return ResponseEntity.ok("Role created : " + result.getType());
    }
}
