package thesis.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thesis.configs.models.structures.AccountInfo;
import thesis.entities.AccountEntity;
import thesis.entities.AccountRoleEntity;
import thesis.entities.RoleEntity;
import thesis.services.AccountEntityService;
import thesis.services.AccountRoleService;
import thesis.services.RoleService;
import thesis.utils.AccountUtils;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AccountEntityService accountService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AccountRoleService accountRoleService;

	@GetMapping(value = "/dashboard")
	public String dashboard(Model model) {
		return "layouts/admin/pages/dashboard";
	}

	@GetMapping(value = "/show-account")
	public String showAccount(Model model) {
		List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();

		List<AccountEntity> accounts = accountService.getAccounts();
		List<RoleEntity> roleEntities = roleService.getRoles();

		if (!CollectionUtils.isEmpty(accounts)) {
			for (AccountEntity accountEntity : accounts) {
				List<AccountRoleEntity> accountRoleEntities = accountRoleService.findByAccountId(accountEntity.getId());
				RoleEntity roleEntity = new RoleEntity();
				int accountRoleId = 0;
				for (AccountRoleEntity accountRoleEntity : accountRoleEntities) {
					roleEntity = AccountUtils._getRoleByRoleId(roleEntities, accountRoleEntity.getRoleId());
					accountRoleId = accountRoleEntity.getId();
				}
				AccountInfo accountInfo = AccountUtils._mappingAttributeFromEntities(accountEntity, roleEntity);
				accountInfo.setAccountRoleId(accountRoleId);
				accountInfos.add(accountInfo);
			}
		}

		model.addAttribute("accountInfos", accountInfos);
		return "layouts/admin/pages/show-account";
	}

	@GetMapping(value = "/add-account")
	public String addAccount(Model model) {
		model.addAttribute("account", new AccountInfo());
		List<RoleEntity> roleEntities = roleService.getRoles();

		model.addAttribute("roles", roleEntities);
		model.addAttribute("action", "add");
		return "layouts/admin/pages/account-form";
	}

	@PostMapping(value = "/result")
	public String confirmadd(Model model, @ModelAttribute("account") AccountInfo account) {

		AccountEntity accountEntity = AccountUtils._mappingAttributeFromAccountMode(account);
		accountEntity = accountService.save(accountEntity);
		RoleEntity roleEntity = roleService.findByRoleCode(account.getRoleCode());
		if (accountEntity.getId() > 0 && roleEntity != null && roleEntity.getId() > 0) {
			AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
			if (account.getAccountRoleId() > 0) {
				Optional<AccountRoleEntity> accountRole = accountRoleService.findById(account.getAccountRoleId());
				if (accountRole.isPresent()) {
					accountRoleEntity.setId(accountRole.get().getId());
				}
			}
			accountRoleEntity.setAccountId(accountEntity.getId());
			accountRoleEntity.setRoleId(roleEntity.getId());
			accountRoleService.save(accountRoleEntity);
		}

		return "redirect:/admin/show-account";
	}

	@GetMapping(value = "/update")
	public String addAccount(Model model, @RequestParam("accId") int accId) {
		AccountInfo accountInfo = new AccountInfo();
		AccountEntity accountEntity = accountService.findAccountById(accId);
		int accountRoleId = 0;
		List<RoleEntity> roleEntities = roleService.getRoles();

		List<AccountRoleEntity> accountRoleEntities = accountRoleService.findByAccountId(accountEntity.getId());
		RoleEntity roleEntity = new RoleEntity();
		for (AccountRoleEntity accountRoleEntity : accountRoleEntities) {
			roleEntity = AccountUtils._getRoleByRoleId(roleEntities, accountRoleEntity.getRoleId());
			accountRoleId = accountRoleEntity.getId();
		}
		accountInfo = AccountUtils._mappingAttributeFromEntities(accountEntity, roleEntity);
		accountInfo.setAccountRoleId(accountRoleId);

		model.addAttribute("account", accountInfo);
		model.addAttribute("roles", roleEntities);
		model.addAttribute("action", "update");
		return "layouts/admin/pages/account-form";
	}

	@GetMapping(value = "/delete")
	public String delete(Model model, @RequestParam("accId") int accId,
			@RequestParam("accountRoleId") int accountRoleId) {
		AccountEntity accountEntity = accountService.findAccountById(accId);
		accountService.delete(accountEntity);
		Optional<AccountRoleEntity> accountRoleEntity = accountRoleService.findById(accountRoleId);

		if (accountRoleEntity.isPresent()) {
			accountRoleService.delete(accountRoleEntity.get());
		}
		return "redirect:/admin/show-account";
	}

	@ModelAttribute("name")
	public String modelDisplayname() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return accountService.findOneByEmail(auth.getName()).getFullName();
		}
		return "Long Pham";
	}
}
