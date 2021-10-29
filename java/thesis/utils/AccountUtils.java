package thesis.utils;

import java.util.List;

import thesis.configs.models.structures.AccountInfo;
import thesis.entities.AccountEntity;
import thesis.entities.RoleEntity;

public class AccountUtils {

	public static RoleEntity _getRoleByRoleId(List<RoleEntity> roleEntities, int roleId) {
		for (RoleEntity roleEntity : roleEntities) {
			if (roleEntity.getId().intValue() == roleId) {
				return roleEntity;
			}
		}
		return null;
	}

	public static AccountInfo _mappingAttributeFromEntities(AccountEntity accountEntity, RoleEntity roleEntity) {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setId(accountEntity.getId());
		accountInfo.setEmail(accountEntity.getEmail());
		accountInfo.setEncrytedPassword(accountEntity.getEncrytedPassword());
		accountInfo.setFullName(accountEntity.getFullName());
		accountInfo.setRoleCode(roleEntity.getRoleCode());
		accountInfo.setRoleName(roleEntity.getRoleName());

		return accountInfo;
	}

	public static AccountEntity _mappingAttributeFromAccountMode(AccountInfo accountInfo) {
		AccountEntity accountEntity = new AccountEntity();
		if (accountInfo.getId() > 0) {
			accountEntity.setId(accountInfo.getId());
		}

		accountEntity.setFullName(accountInfo.getFullName());
		accountEntity.setEmail(accountInfo.getEmail());
		accountEntity.setEncrytedPassword(EncrytedPasswordUtils.encrytedPassword(accountInfo.getEncrytedPassword()));

		return accountEntity;
	}
}
