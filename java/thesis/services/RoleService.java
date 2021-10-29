package thesis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thesis.entities.RoleEntity;
import thesis.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public List<RoleEntity> getRoles() {
		return (List<RoleEntity>) roleRepository.findAll();
	}

	public RoleEntity findByRoleCode(String roleCode) {
		return roleRepository.findByRoleCodeLike(roleCode);
	}
}
