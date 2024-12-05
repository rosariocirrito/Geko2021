package it.sicilia.regione.gekoddd.geko.valutazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneRepository;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("valutazioneQryService")
@Repository
@Transactional
public class ValutazioneQryServiceImpl implements ValutazioneQryService {
	
	private Log log = LogFactory.getLog(ValutazioneQryServiceImpl.class);

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ValutazioneRepository repo;
	
	@Autowired
    private FromOrganikoQryService fromOrganikoServizi;
	
	@Autowired
    private ObiettivoQryService objServizi;
	
	

	@Transactional(readOnly=true)
	public List<Valutazione> findByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Valutazione> listaVal = repo.findByIncaricoIDAndAnno(incaricoID, anno);
		List<Valutazione> listaValUpd = new ArrayList<Valutazione>();
		IncaricoGeko inc = fromOrganikoServizi.findIncaricoById(incaricoID);
		List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(incaricoID, anno);
		inc.setObiettivi(lstObjs);
		for(Valutazione val : listaVal ){
			val.setIncarico(inc);
			listaValUpd.add(val);
		}
		return listaValUpd;
	}

}
