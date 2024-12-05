package it.sicilia.regione.gekoddd.security.domain.entity;


import javax.persistence.*;


/**
 * The persistent class for the op_persona_afferenza database table.
 * 
 */
@Entity
@Table(name="authorities")
public class AuthoritySecur {
	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;
    
    //bi-directional many-to-one association to User
    //@ManyToOne(fetch=FetchType.EAGER) 2015/07/20
    @ManyToOne()
    @JoinColumn(name="idusers")
    private UserSecur user;

    
    //bi-directional many-to-one association to AuthorityType
    // @ManyToOne(fetch=FetchType.EAGER) 2015/07/20
    @ManyToOne()
    @JoinColumn(name="idauthority_type")
    private AuthorityTypeSecur authorityType;
    
    
    transient private int idauthority_type;

    
    
    transient private boolean nuovo;

    public boolean isNuovo() {
        return (this.id == null);
    }

    public AuthoritySecur() {    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

	public UserSecur getUser() {
		return user;
	}

	public void setUser(UserSecur user) {
		this.user = user;
	}

	public AuthorityTypeSecur getAuthorityType() {
		return authorityType;
	}

	public void setAuthorityType(AuthorityTypeSecur authorityType) {
		this.authorityType = authorityType;
	}

	public int getIdauthority_type() {
		return idauthority_type;
	}

	public void setIdauthority_type(int idauthority_type) {
		this.idauthority_type = idauthority_type;
	}

	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthoritySecur other = (AuthoritySecur) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
            
	
}
