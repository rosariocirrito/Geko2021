package it.sicilia.regione.gekoddd.geko.valutazione.application;

import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;

/**
 *
 * @author Cirrito
 */
public interface OivValutazioneService {
	   
	//
    void oivCreateValutazione(Valutazione valutazione);
	void oivUpdateValutazione(Valutazione valutazione);
	
    void oivCreateValutazioneCompOrg(Valutazione valutazione);
    void oivUpdateValutazioneCompOrg(Valutazione valutazione);
   
}
