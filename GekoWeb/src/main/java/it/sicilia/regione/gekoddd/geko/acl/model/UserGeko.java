package it.sicilia.regione.gekoddd.geko.acl.model;


import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import it.sicilia.regione.gekoddd.geko.acl.dto.UserDTO;




public class UserGeko {
	
	// ------------------- campi db ---------------------------------------
	private Integer idusers;
	private String email;
	private Boolean enabled;
	private String username;
	private String oldPassword;
	private String password;
	private String password2;
	private Integer pfID;
	private List<String> roles;
	
	List<GrantedAuthority> authorities;

	public UserGeko(){}
	
    public UserGeko(UserDTO user){
    	this.idusers = user.getIdusers();
    	this.email = user.getEmail();
    	this.enabled = user.getEnabled();
    	this.username = user.getUsername();
    	this.password = user.getPassword();
    	this.pfID = user.getPfID();
    	this.roles= user.getRoles();
    }

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public Integer getIdusers() {
		return idusers;
	}

	public void setIdusers(Integer idusers) {
		this.idusers = idusers;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPfID() {
		return pfID;
	}

	public void setPfID(Integer pfID) {
		this.pfID = pfID;
	}
    
    
	
} // ----------------

