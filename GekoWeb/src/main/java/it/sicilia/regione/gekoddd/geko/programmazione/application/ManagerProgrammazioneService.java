package it.sicilia.regione.gekoddd.geko.programmazione.application;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;

public interface ManagerProgrammazioneService {
	// 1.
	Evento cmdManagerCreateObiettivo(Obiettivo obiettivo);
	void managerDuplicaObiettivo(Obiettivo obiettivo);
    // 2.
	void managerUpdateObiettivo(Obiettivo obiettivo);
	//void managerUpdateIncaricoObiettivo(Obiettivo obiettivo);
    // 3.
	Evento cmdManagerDeleteObiettivo(Obiettivo obiettivo);
    // 4.
	Evento cmdManagerProponeObiettivo(Obiettivo obiettivo);
    // 5.
	Evento cmdManagerAccettaDefinitivamenteObiettivo(Obiettivo obiettivo);
	// 6.
	Evento cmdManagerRendiInterlocutorioObiettivo(Obiettivo obiettivo);
	
	Evento cmdManagerDefCompartoObiettivo(Obiettivo obiettivo);
	Evento cmdManagerDefPopObiettivo(Obiettivo obiettivo);
    
	// 1.
    Evento cmdManagerCreateAzione(Azione azione);
    void managerDuplicaAzione(Azione azione);
    
    void managerUpdateAzione(Azione azione);
    
    // 3.
    //void managerDeleteAzione(Azione azione);
 	Evento cmdManagerDeleteAzione(Azione azione);
    
    void managerUpdateRisultatoAzione(Azione azione);
    //
    void managerCreateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    void managerDuplicaAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    Evento cmdManagerUpdateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    void managerDeleteAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    
    //
    //
    void managerCreateAssociazObiettivi(AssociazObiettivi associazObiettivi);
    void managerUpdateAssociazObiettivi(AssociazObiettivi associazObiettivi);
    void managerDeleteAssociazObiettivi(AssociazObiettivi associazObiettivi);
    //
    void managerCreateObiettivoAssegnazione(ObiettivoAssegnazione obiettivoAssegnazione);
    void managerDuplicaObiettivoAssegnazione(ObiettivoAssegnazione obiettivoAssegnazione);
    Evento cmdManagerUpdateObiettivoAssegnazione(ObiettivoAssegnazione obiettivoAssegnazione);
    void managerDeleteObiettivoAssegnazione(ObiettivoAssegnazione obiettivoAssegnazione);
    //void managerUpdateObiettivoAssegnazioneValutazione(ObiettivoAssegnazione obiettivoAssegnazione);
    //
    
}
