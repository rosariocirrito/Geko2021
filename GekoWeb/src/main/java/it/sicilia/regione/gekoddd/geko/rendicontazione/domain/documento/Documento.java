package it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento;


import java.sql.Date;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;

@Entity
@Table(name="documento")
public class Documento {
    
    @Id
    @GeneratedValue
    private Integer id;
    //
    private String nome;
    private String descrizione;
    private String nomefile;
    
    private String tipocontenuto;
    
    //@Temporal(TemporalType.TIMESTAMP) // vedi incarico se non funziona
    private Date creato;

    @Column(name="contenuto")
    @Lob
    private byte[] contenuto;
    
    //
    transient private boolean editable;
    transient private boolean nuovo;
    public boolean isEditable() {
    	/*
    	if (this.getAzione().getObiettivo().getTipo().equals(TipoEnum.OBIETTIVO)){
    		LocalDate scadenza = this.getAzione().getScadenza();
    		if(scadenza != null){
	    		Calendar data = Calendar.getInstance();
	        	data.setTime(scadenza);
	        	//
	        	LocalDate datadioggi =LocalDate.now();
	    		//
	        	return datadioggi.compareTo(data.getTime()) < 0;
    		}
    		else {return true;}
    	}
    	else {return true;}
    	*/
    	return true;
	}

	transient private MultipartFile fileData;
    
    
    @ManyToOne
    @JoinColumn(name="idAzione")
    private Azione azione;
    
    // ----------------------------------
    
    public boolean isNuovo() {
	return (this.id == null);
    }
    
    public String getTipocontenuto() {
        return tipocontenuto;
    }

    public void setTipocontenuto(String tipocontenuto) {
        this.tipocontenuto = tipocontenuto;
    }

    public Date getCreato() {
        return creato;
    }

    public void setCreated(Date created) {
        this.creato = created;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNomefile() {
        return nomefile;
    }

    public void setNomefile(String nomefile) {
        this.nomefile = nomefile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    //
  public String getNome()
  {
    return nome;
  }
  public void setNome(String nome)
  {
    this.nome = nome;
  }
 
  public MultipartFile getFileData()  { return fileData;  }
  
  public void setFileData(MultipartFile fileData){ 
      this.fileData = fileData;
  }

    public Azione getAzione() {
        return azione;
    }

    public void setAzione(Azione azione) {
        this.azione = azione;
    }

    public byte[] getContenuto() {
        return contenuto;
    }

    public void setContenuto(byte[] contenuto) {
        this.contenuto = contenuto;
    }
  
    public Integer getIncaricoID() {return this.getAzione().getObiettivo().getIncaricoID();}
  
} //----

