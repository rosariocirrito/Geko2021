package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione;

import java.util.List;


public interface ObiettivoAssegnazioneQryService {

	//-----------------------------------
		
	public List<ObiettivoAssegnazione> findByIdPersonaAndAnno(int pfID, int anno);
	public List<ObiettivoAssegnazione> findByIdStrutturaAndAnno(int pgID, int anno);
	public List<ObiettivoAssegnazione> findByIdObiettivo(int idObj);
	public List<ObiettivoAssegnazione> findByPfIDAndIncaricoIDAndAnno(int pfID, int idIncarico, int anno);
}
