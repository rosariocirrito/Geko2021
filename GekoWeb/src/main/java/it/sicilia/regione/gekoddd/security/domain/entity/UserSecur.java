package it.sicilia.regione.gekoddd.security.domain.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

//import it.sicilia.regione.gekoddd.security.acl.model.PersonaFisicaSecur;

@Entity
@Table(name="users")
public class UserSecur  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idusers;

	private String email;

	private Boolean enabled;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="expiry_date")
	private Date expiryDate;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="insert_date")
	private Date insertDate;

	private String password;
	transient private String password2;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="renewal_date")
	private Date renewalDate;

	private String username;
	
	@Column(name="persona_idpersona")
	private Integer pfID;
	/*
	//bi-directional many-to-many association to AuthorityType
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="authorities"
		, joinColumns={
			@JoinColumn(name="idusers")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idauthority_type")
			}
		)
	private List<AuthorityType> authorityTypes;
	*/
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private List<AuthoritySecur> authorities = new ArrayList<AuthoritySecur>();

	//transient private PersonaFisicaSecur persona;

    public UserSecur() {
    }

	public Integer getIdusers() {
		return this.idusers;
	}

	public void setIdusers(Integer idusers) {
		this.idusers = idusers;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getPassword() {
		return this.password;
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

	public Date getRenewalDate() {
		return this.renewalDate;
	}

	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public List<AuthoritySecur> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuthoritySecur> authorities) {
		this.authorities = authorities;
	}

	public List<AuthorityTypeSecur> getAuthorityTypes() {
		List<AuthorityTypeSecur> lista = new ArrayList<AuthorityTypeSecur>();
		for(AuthoritySecur auth : this.getAuthorities()){
			if (auth.getAuthorityType().getApplication().equals("geko"))
				lista.add(auth.getAuthorityType());
		}
		return lista;
	}

	
	/*
	public PersonaFisicaSecur getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaFisicaSecur persona) {
		this.persona = persona;
	}
	*/

	public Integer getPfID() {
		return pfID;
	}

	public void setPfID(Integer pfID) {
		this.pfID = pfID;
	}

	@Override
	public boolean equals(Object o) {
		boolean check = false;
		if(o instanceof UserSecur){
			if(((UserSecur)o).idusers.equals(idusers))
				check = true;
		}
		return check;
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.idusers != null ? this.idusers.hashCode() : 0);
        return hash;
    }

	@Override
	public String toString() {
		return "User [idusers=" + idusers + ", username=" + username
				+ ", pfID=" + pfID + "]";
	}
	
    
} // -------------------------------------------------------------

