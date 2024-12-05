package it.sicilia.regione.gekoddd.security.domain.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the authority_type database table.
 * 
 */

@Entity
@Table(name="authority_type")
public class AuthorityTypeSecur {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idauthority_type")
	private Integer idauthorityType;

	private String authority;

	private String descrizione;
	
	private String application;

	/*
	//bi-directional many-to-many association to User
    @ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="authorities"
		, joinColumns={
			@JoinColumn(name="idauthority_type")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idusers")
			}
		)
	private List<User> users;
	*/
	@OneToMany(mappedBy = "authorityType",fetch = FetchType.EAGER)
	private List<AuthoritySecur> authorities = new ArrayList<AuthoritySecur>();
	

    public AuthorityTypeSecur() {
    }

	public Integer getIdauthorityType() {
		return this.idauthorityType;
	}

	public void setIdauthorityType(Integer idauthorityType) {
		this.idauthorityType = idauthorityType;
	}

	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public List<AuthoritySecur> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthoritySecur> authorities) {
		this.authorities = authorities;
	}

	public List<UserSecur> getUsers() {
		List<UserSecur> lista = new ArrayList<UserSecur>();
		for (AuthoritySecur auth : this.getAuthorities()){
			lista.add(auth.getUser());
		}
		return lista;
	}
	
	@Override
	public boolean equals(Object o){
		boolean check = false;
		if(o instanceof AuthorityTypeSecur){
			if(((AuthorityTypeSecur)o).idauthorityType.equals(idauthorityType))
				check = true;
		}
		return check;
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.idauthorityType != null ? this.idauthorityType.hashCode() : 0);
        return hash;
    }
	
}

