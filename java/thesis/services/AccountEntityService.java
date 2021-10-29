package thesis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thesis.entities.AccountEntity;
import thesis.repositories.AccountRepository;

@Service
public class AccountEntityService {

	@Autowired
	private AccountRepository accountRepository;

	public AccountEntity findOneByEmail(String email) {
		AccountEntity accountEntity = this.accountRepository.findOneByEmail(email);
		return accountEntity != null ? accountEntity : new AccountEntity();
	}

	public List<AccountEntity> getAccounts() {
		return (List<AccountEntity>) accountRepository.findAll();
	}

	public AccountEntity save(AccountEntity account) {
		return accountRepository.save(account);
	}

	public AccountEntity findAccountById(int id) {
		Optional<AccountEntity> accountEntity = accountRepository.findById(id);
		if (accountEntity.isPresent()) {
			return accountEntity.get();
		}

		return new AccountEntity();
	}

	public void delete(AccountEntity account) {
		accountRepository.delete(account);
	}

}
