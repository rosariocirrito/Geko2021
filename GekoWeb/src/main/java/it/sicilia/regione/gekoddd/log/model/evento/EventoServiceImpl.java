package it.sicilia.regione.gekoddd.log.model.evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// da rimettere import it.sicilia.regione.session.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("eventoService")
@Repository
@Transactional
public class EventoServiceImpl implements EventoService {
	
	private Log log = LogFactory.getLog(EventoServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private EventoRepository repo;
	//@Autowired 
	// da rimettere private UserQryService userServizi;
	
	
	

	@Transactional(readOnly=true)
	public Evento findById(Integer id) {
		return repo.findOne(id);
	}

	public Evento save(Evento arg0) {
		return repo.save(arg0);
	}

	public void delete(Evento arg0) {
		log.info("EvtErr with id: " + arg0.getId() + " deleted successfully");
		repo.delete(arg0);
		
	}

	// -----------------------------------------------------------
	@Transactional(readOnly=true)
	public List<Evento> findByWho(String chi) {
		return repo.findByWho(chi);
	}

	//
	@Transactional(readOnly=true)
	public List<Evento> findByWhoAndCmd(String who, String cmd) {
		return repo.findByWhoAndCmd(who, cmd);
	}

	@Transactional(readOnly=true)
	public List<Evento> findByWhenAfter(Date when) {
		List<Evento> listaTot = repo.findByQuandoGreaterThanOrderByWhoAsc(when);
		List<Evento> lista = new ArrayList();
		for(Evento item : listaTot){
			// da rimettere User user = userServizi.findUserByUsername(item.getWho());			
			lista.add(item);			
		}
		return lista;
	}

}
