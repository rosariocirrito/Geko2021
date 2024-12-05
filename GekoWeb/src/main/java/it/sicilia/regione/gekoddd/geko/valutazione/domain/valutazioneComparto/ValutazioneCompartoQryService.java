package it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto;

import java.util.List;

public interface ValutazioneCompartoQryService {

	//-----------------------------------
	public List<ValutazioneComparto> findByPfIDAndAnno(Integer pfID, int anno);	
	public List<ValutazioneComparto> findByPfIDAndIncaricoIDAndAnno(Integer pfID, Integer incaricoID, int anno);	
}
