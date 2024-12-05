package it.sicilia.regione.gekoddd.log.model.evento;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="eventStore")
public class Evento {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String who;
    
    private String cmd;
    
    private String event;
    
    private String classe;
    
    private int idClasse;
    
    public enum TipoEnum{
        SUCCESS,
        ERROR,
        WARNING
    }
    
    @Enumerated
    private TipoEnum tipo;
    /*
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    //@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTimeWithZone")
    //@Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(iso=ISO.DATE)
    private DateTime quando;
      */
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date quando;
    
	// getters
    public Integer getId() {return id;}
	public String getWho() {return who;}
	public TipoEnum getTipo() {return tipo;}
	public String getClasse() {return classe;}
	public int getIdClasse() {return idClasse;}
	
	
	// builder 	
	static public Evento createEvt(String _who, String _cmd, String _event, TipoEnum _tipo, String _classe, int idClasse){
		Evento newEvt = new Evento();
		newEvt.who = _who;
		newEvt.cmd = _cmd;
		newEvt.event = _event;
		newEvt.tipo = _tipo;
		newEvt.quando = new Date();
		newEvt.classe = _classe;
		newEvt.idClasse = idClasse;
		return newEvt;
	}
	@Override
	public String toString() {
		return "Evento [id=" + id + ", who=" + who + ", cmd=" + cmd
				+ ", event=" + event + ", classe=" + classe + ", idClasse="
				+ idClasse + ", tipo=" + tipo + ", quando=" + quando + "]";
	}
	
} // end of class
