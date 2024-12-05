package it.sicilia.regione.gekoddd.log.model.evento;


import java.util.Date;
import java.util.List;



public interface EventoService {
	
	// Find by id
	public Evento findById(Integer id);
	
	// Insert or update 	
	public Evento save(Evento arg0);
	
	// Delete 
	public void delete(Evento arg0);
	
	//-----------------------------------
	public List<Evento> findByWho(String who);
	
	public List<Evento> findByWhoAndCmd(String who, String cmd);	
	
	public List<Evento> findByWhenAfter(Date when);	
	
	
}
