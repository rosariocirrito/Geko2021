package it.sicilia.regione.gekoddd.geko.programmazione.application;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;

public interface RespPopProgrammazioneService {
	// 1.
	Evento cmdPopCreateObiettivo(Obiettivo obiettivo);
    // 2.
	void popUpdateObiettivo(Obiettivo obiettivo);
	//void managerUpdateIncaricoObiettivo(Obiettivo obiettivo);
    // 3.
	Evento cmdPopDeleteObiettivo(Obiettivo obiettivo);
    // 4.
	Evento cmdPopProponeObiettivo(Obiettivo obiettivo);
    // 5.
	Evento cmdPopAccettaDefinitivamenteObiettivo(Obiettivo obiettivo);
	// 6.
	Evento cmdPopRendiInterlocutorioObiettivo(Obiettivo obiettivo);
	
	Evento cmdPopDefPopObiettivo(Obiettivo obiettivo);
    
	// 1.
    Evento cmdPopCreateAzione(Azione azione);
   
    void popUpdateAzione(Azione azione);
    
    // 3.
 	Evento cmdPopDeleteAzione(Azione azione);
    
    void popUpdateRisultatoAzione(Azione azione);
    //
    
    //
    
}
