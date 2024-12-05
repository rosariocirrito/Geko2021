package it.sicilia.regione.gekoddd.geko.programmazione.application;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;

public interface CompartoProgrammazioneService {
	// 1.
	Evento cmdCompartoCreateObiettivo(Obiettivo obiettivo);
    // 2.
	void compartoUpdateObiettivo(Obiettivo obiettivo);
	//void compartoUpdateIncaricoObiettivo(Obiettivo obiettivo);
    // 3.
	Evento cmdCompartoDeleteObiettivo(Obiettivo obiettivo);
    // 4.
	Evento cmdCompartoProponeObiettivo(Obiettivo obiettivo);
    // 5.
	Evento cmdCompartoAccettaDefinitivamenteObiettivo(Obiettivo obiettivo);
	// 6.
	Evento cmdCompartoRendiInterlocutorioObiettivo(Obiettivo obiettivo);
    
	// 1.
    Evento cmdCompartoCreateAzione(Azione azione);
    void compartoUpdateAzione(Azione azione);
    
    // 3.
    //void compartoDeleteAzione(Azione azione);
 	Evento cmdCompartoDeleteAzione(Azione azione);
    
    void compartoUpdateRisultatoAzione(Azione azione);
    //
    void compartoCreateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    Evento cmdCompartoUpdateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    void compartoDeleteAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    void compartoUpdateAzioneAssegnazioneValutazione(AzioneAssegnazione azioneAssegnazione);
    //
    
}
