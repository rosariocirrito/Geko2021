package it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("azioneAssegnazioneQryService")
@Repository
@Transactional
public class AzioneAssegnazioneQryServiceImpl implements AzioneAssegnazioneQryService {
	
	private Log log = LogFactory.getLog(AzioneAssegnazioneQryServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private AzioneAssegnazioneRepository repo;
	
	

	@Transactional(readOnly=true)
	public List<AzioneAssegnazione> findByPfIDAndAnno(Integer pfID, int anno) {
		List<AzioneAssegnazione> lista = new ArrayList();
		List<AzioneAssegnazione> listaTot = repo.findByPfID(pfID);
		for(AzioneAssegnazione aa : listaTot){
			if (aa.getAnno() == anno) lista.add(aa);
		}
		return lista;
	}

	@Transactional(readOnly=true)
	public List<AzioneAssegnazione> findByPfIDAndIncaricoIDAndAnno(Integer pfID, Integer incaricoID, int anno) {
		//log.info("Ricerca assegnazioni per pfID="+pfID+" incarico="+incaricoID+" anno="+anno);
		List<AzioneAssegnazione> lista = new ArrayList();
		List<AzioneAssegnazione> listaTot = repo.findByPfID(pfID);
		for(AzioneAssegnazione aa : listaTot){
			//log.info("IdDip="+aa.getPfID()+" aa.getAnno="+aa.getAnno()+" incaricoID="+aa.getIncaricoID());
			if ((aa.getAnno() == anno) && (aa.getIncaricoID().equals(incaricoID))) { // attnz Integer non int vuole equals
					//log.info("ok IdDip:"+aa.getPfID()+" con aa.getIncaricoID()="+aa.getIncaricoID()+" e con aa.getAnno()="+aa.getAnno());
					lista.add(aa);
			}
		}
		return lista;
	}
	
}
