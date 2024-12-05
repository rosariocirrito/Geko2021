package it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione;


import java.util.List;

public interface ValutazioneQryService {

	
	//-----------------------------------
	List<Valutazione> findByIncaricoIDAndAnno(Integer incaricoID, int anno);
}
