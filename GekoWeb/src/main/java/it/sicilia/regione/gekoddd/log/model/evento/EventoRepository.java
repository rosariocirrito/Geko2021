package it.sicilia.regione.gekoddd.log.model.evento;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, Integer> {
	//
	public List<Evento> findByWho(String who);
	
	public List<Evento> findByWhoAndCmd(String who, String Cmd);
	
	public List<Evento> findByQuandoGreaterThanOrderByWhoAsc(Date when);	
	
}
