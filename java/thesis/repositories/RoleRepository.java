package thesis.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import thesis.entities.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
	 public RoleEntity findOneById(int id);
	    
	    @Query(value = "Select * from role  where role_name = ?1",nativeQuery = true)
	   	public RoleEntity findOneByName(String role);
	    
	    public RoleEntity findByRoleCodeLike(String roleCode);
}
