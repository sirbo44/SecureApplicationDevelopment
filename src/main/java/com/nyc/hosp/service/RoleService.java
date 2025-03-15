package com.nyc.hosp.service;

import com.nyc.hosp.domain.Hospuser;
import com.nyc.hosp.domain.Role;
import com.nyc.hosp.model.RoleDTO;
import com.nyc.hosp.repos.HospuserRepository;
import com.nyc.hosp.repos.RoleRepository;
import com.nyc.hosp.util.NotFoundException;
import com.nyc.hosp.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final HospuserRepository hospuserRepository;

    public RoleService(final RoleRepository roleRepository,
            final HospuserRepository hospuserRepository) {
        this.roleRepository = roleRepository;
        this.hospuserRepository = hospuserRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("roleId"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Integer roleId) {
        return roleRepository.findById(roleId)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getRoleId();
    }

    public void update(final Integer roleId, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(roleId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Integer roleId) {
        roleRepository.deleteById(roleId);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setRoleId(role.getRoleId());
        roleDTO.setRolename(role.getRolename());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setRolename(roleDTO.getRolename());
        return role;
    }

    public ReferencedWarning getReferencedWarning(final Integer roleId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Role role = roleRepository.findById(roleId)
                .orElseThrow(NotFoundException::new);
        final Hospuser roleHospuser = hospuserRepository.findFirstByRole(role);
        if (roleHospuser != null) {
            referencedWarning.setKey("role.hospuser.role.referenced");
            referencedWarning.addParam(roleHospuser.getUserId());
            return referencedWarning;
        }
        return null;
    }

}
