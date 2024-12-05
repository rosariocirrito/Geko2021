package it.sicilia.regione.gekoddd.geko.valutazione.infrastructure;

import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;


public interface ValutazioneCompartoCmdService {

	// Find by id
	public ValutazioneComparto findById(Integer id);
	
	// Insert or update 
	public ValutazioneComparto save(ValutazioneComparto arg0);
	
	// Delete 
	public void delete(ValutazioneComparto arg0);
	
}
