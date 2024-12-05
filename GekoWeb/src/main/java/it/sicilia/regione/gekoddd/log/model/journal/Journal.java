package it.sicilia.regione.gekoddd.log.model.journal;


import java.util.Date;

import javax.persistence.*;

//import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="journal")
public class Journal  {
	

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String chi;
    
    private String cosa;
    
    private String dove;
    private int idObj;
    
    private String perche;
    
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    //@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimeWithZone")
    //@DateTimeFormat(iso=ISO.DATE)
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date quando;

    transient int anno;
    transient int giorno;

    public Journal() {    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChi() {
		return chi;
	}

	public void setChi(String chi) {
		this.chi = chi;
	}

	public String getCosa() {
		return cosa;
	}

	public void setCosa(String cosa) {
		this.cosa = cosa;
	}

	public String getDove() {
		return dove;
	}

	public void setDove(String dove) {
		this.dove = dove;
	}

	public String getPerche() {
		return perche;
	}

	public void setPerche(String perche) {
		this.perche = perche;
	}

	public Date getQuando() {
		return quando;
	}

	public void setQuando(Date quando) {
		this.quando = quando;
	}

	public int getIdObj() {
		return idObj;
	}

	public void setIdObj(int idObj) {
		this.idObj = idObj;
	}

	public int getAnno() {
		return this.quando.getYear();
	}

	public int getGiorno() {
		return this.quando.getDay();
	}


} // ------------------------------
