package thesis.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import thesis.entities.AccountRoleEntity;

public interface AccountRoleRepository extends CrudRepository<AccountRoleEntity, Integer> {
    public List<AccountRoleEntity> findByAccountId(int accountId);
    
    public AccountRoleEntity findByAccountIdAndRoleId(int accountId, int roleId);
}
