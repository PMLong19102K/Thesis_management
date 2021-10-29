package thesis.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import thesis.entities.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {
	
	public AccountEntity findOneByEmail(String email);
	@Query(value ="select * from account  where email = ?1",nativeQuery = true)
	public AccountEntity findOneByEmail1(String email);
	
}
