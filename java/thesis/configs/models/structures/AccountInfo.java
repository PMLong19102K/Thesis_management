package thesis.configs.models.structures;

public class AccountInfo {
	private int id;
	private String email;
	private String encrytedPassword;
	private String fullName;
	private String roleCode;
	private String roleName;
	private int accountRoleId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncrytedPassword() {
		return encrytedPassword;
	}

	public void setEncrytedPassword(String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getAccountRoleId() {
		return accountRoleId;
	}

	public void setAccountRoleId(int accountRoleId) {
		this.accountRoleId = accountRoleId;
	}

}
