package thesis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thesis.entities.AccountRoleEntity;
import thesis.repositories.AccountRoleRepository;

@Service
public class AccountRoleService {

	@Autowired
	private AccountRoleRepository accountRoleRepository;

	public List<AccountRoleEntity> findByAccountId(int accId) {
		return accountRoleRepository.findByAccountId(accId);
	}

	public Optional<AccountRoleEntity> findById(int id) {
		return accountRoleRepository.findById(id);
	}

	public AccountRoleEntity save(AccountRoleEntity account) {
		return accountRoleRepository.save(account);
	}

	public void delete(AccountRoleEntity account) {
		accountRoleRepository.delete(account);
	}
}
