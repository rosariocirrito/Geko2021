package it.sicilia.regione.gekoddd.geko.valutazione.infrastructure;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("valutazioneCompartoQryService")
@Repository
@Transactional
public class ValutazioneCompartoQryServiceImpl implements ValutazioneCompartoQryService {
	
	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ValutazioneCompartoRepository repo;
	
	
	@Transactional(readOnly=true)
	public List<ValutazioneComparto> findByPfIDAndAnno(Integer pfID, int anno) {
		List<ValutazioneComparto> lista = new ArrayList<ValutazioneComparto>();
		List<ValutazioneComparto> listaTot = repo.findByPfID(pfID);
		for(ValutazioneComparto aa : listaTot){
			if (aa.getAnno() == anno) lista.add(aa);
		}
		return lista;
	}

	@Transactional(readOnly=true)
	public List<ValutazioneComparto> findByPfIDAndIncaricoIDAndAnno(Integer pfID, Integer incaricoID, int anno) {
		List<ValutazioneComparto> lista = new ArrayList<ValutazioneComparto>();
		List<ValutazioneComparto> listaTot = repo.findByPfID(pfID);
		for(ValutazioneComparto vc : listaTot){
			if (vc.getAnno() == anno && vc.getIncaricoID().equals(incaricoID)) lista.add(vc);
		}
		return lista;
	}
} // -----------------------------------
