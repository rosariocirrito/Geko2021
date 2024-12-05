package it.sicilia.regione.gekoddd.geko.acl.dto;


import java.util.ArrayList;
//import java.time.LocalDate;
import java.util.List;


public class UserDTO {
	
	// ------------------- campi db ---------------------------------------
	private  Integer idusers;
	private  String email;
	private  Boolean enabled;
	private  String username;
	private  String password;
	private  Integer pfID;
	private  List<String> roles = new ArrayList<String>();
	
    public UserDTO(){
    	
    }

	public Integer getIdusers() {
		return idusers;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Integer getPfID() {
		return pfID;
	}

	public List<String> getRoles() {
		return roles;
	}


/*
	public UserDTO(UserSecur user){
    	this.idusers = user.getIdusers();
    	this.email = user.getEmail();
    	this.enabled = user.isEnabled();
    	this.username = user.getUsername();
    	this.password = user.getPassword();
    	this.pfID = user.getPfID();
    	List<AuthoritySecur> listAuths = user.getAuthorities();
    	for (AuthoritySecur auth  : listAuths){
    		this.roles.add(auth.getAuthorityType().getAuthority());
    	}
    }
    */
	
} // ----------------

