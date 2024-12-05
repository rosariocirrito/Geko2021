package it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione;

import java.util.List;

public interface AzioneAssegnazioneQryService {

	//-----------------------------------
		
	public List<AzioneAssegnazione> findByPfIDAndAnno(Integer pfID, int anno);
	public List<AzioneAssegnazione> findByPfIDAndIncaricoIDAndAnno(Integer pfID, Integer incaricoID, int anno);
	
	
}
