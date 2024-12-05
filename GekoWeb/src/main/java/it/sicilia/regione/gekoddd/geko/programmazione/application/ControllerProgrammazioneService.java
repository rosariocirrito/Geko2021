/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sicilia.regione.gekoddd.geko.programmazione.application;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;

/**
 *
 * @author Cirrito
 */
public interface ControllerProgrammazioneService {
	
	Evento cmdControllerConcordaObiettivoApicale(Obiettivo obiettivo);
	
	// 1.
    void controllerProponeObiettivo(Obiettivo obiettivo);
    // 1.b
    void controllerRichiedeObiettivo(Obiettivo obiettivo);
    // 2.
    void controllerUpdateObiettivo(Obiettivo obiettivo);
    void controllerUpdateIncaricoObiettivo(Obiettivo obiettivo);
    // 3.
    void controllerDeleteObiettivo(Obiettivo obiettivo);
    
    // 4.
    void controllerValidaObiettivo(Obiettivo obiettivo);
    // 5.
    void controllerRivedeObiettivo(Obiettivo obiettivo);  
    // 6.
    void controllerRespingeObiettivo(Obiettivo obiettivo);    
    // 7.
    void controllerAnnullaObiettivo(Obiettivo obiettivo); 
    
    Evento cmdControllerRendiInterlocutorioObiettivo(Obiettivo obiettivo);
    Evento cmdControllerRendiPropostoObiettivo(Obiettivo obiettivo);
    Evento cmdControllerProponeObiettivo(Obiettivo obiettivo);
    Evento cmdControllerCreaObiettivoApicale(Obiettivo obiettivo);
    
    // 8.
    void controllerRendiApicaleObiettivo(Obiettivo obiettivo); 
    // 8.
    void controllerClonaObiettivo(Obiettivo obiettivo); 
    
    // aggiorna stati
    void controllerRealizzaObiettivo(Obiettivo obiettivo);
    void controllerValutaObiettivo(Obiettivo obiettivo);

    //
    void controllerCreateAzione(Azione azione);
    Evento cmdControllerCreaAzioneApicale(Azione azione);
    void controllerUpdateAzione(Azione azione);
    void controllerUpdateAzioneApicale(Azione azione);
    void controllerDeleteAzione(Azione azione);
    void controllerUpdateCompletamentoAzione(Azione azione);
    void controllerUpdateRisultatoApicaleAzione(Azione azione);
    void controllerUpdatePesoAzione(Azione azione);
    void controllerClonaAzione(Azione azione);
    //void controllerClonaAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    void controllerUpdatePesoApicaleAzione(Azione azione);
    void controllerUpdateScadenzaApicaleAzione(Azione azione);
    
  
    //
    void controllerCreateAssociazObiettivi(AssociazObiettivi associazObiettivi);
    void controllerUpdateAssociazObiettivi(AssociazObiettivi associazObiettivi);
    void controllerDeleteAssociazObiettivi(AssociazObiettivi associazObiettivi);
    
    
    void controllerUpdateObiettivoApicale(Obiettivo obiettivo);
    
  
    //
    void controllerCreateAssociazProgramma(AssociazProgramma associazProgramma);
    void controllerUpdateAssociazProgramma(AssociazProgramma associazProgramma);
    void controllerDeleteAssociazProgramma(AssociazProgramma associazProgramma);
    
    //
    void controllerCreateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    void controllerDuplicaAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    Evento cmdControllerUpdateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    void controllerDeleteAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
}
