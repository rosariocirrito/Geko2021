package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("obiettivoAssegnazioneQryService")
@Repository
@Transactional
public class ObiettivoAssegnazioneQryServiceImpl implements ObiettivoAssegnazioneQryService {
	
	@Autowired
    private ObiettivoCmdService objServizi;
	
	private Log log = LogFactory.getLog(ObiettivoAssegnazioneQryServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ObiettivoAssegnazioneRepository repo;
		

	@Override
	@Transactional(readOnly=true)
	public List<ObiettivoAssegnazione> findByIdObiettivo(int idObj) {
		List<ObiettivoAssegnazione> lista = repo.findByIdObiettivo(idObj);
		return lista;
	}


	@Override
	public List<ObiettivoAssegnazione> findByIdPersonaAndAnno(int pfID, int anno) {
		List<ObiettivoAssegnazione> lista = repo.findByIdPersonaAndAnno(pfID,anno);
		return lista;
	}



	@Override
	public List<ObiettivoAssegnazione> findByIdStrutturaAndAnno(int pgID, int anno) {
		List<ObiettivoAssegnazione> lista = repo.findByIdStrutturaAndAnno(pgID,anno);
		return lista;
	}


	@Override
	public List<ObiettivoAssegnazione> findByPfIDAndIncaricoIDAndAnno(int pfID, int idIncarico, int anno) {
		List<ObiettivoAssegnazione> listaInc = new ArrayList<ObiettivoAssegnazione>();
		List<ObiettivoAssegnazione> lista = repo.findByIdPersonaAndAnno(pfID,anno);
		for (ObiettivoAssegnazione ass : lista){
			Obiettivo obj = objServizi.findById(ass.getIdObiettivo());
			if(obj.getIncaricoID() == idIncarico) listaInc.add(ass); 
		}
		return listaInc;
	}
	
}
