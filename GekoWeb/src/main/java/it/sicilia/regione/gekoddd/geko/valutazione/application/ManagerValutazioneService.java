package it.sicilia.regione.gekoddd.geko.valutazione.application;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;

public interface ManagerValutazioneService {
    // per i comportamenti organizzativi
    void managerCreateValutazioneComparto(ValutazioneComparto valutazioneComparto);
    void managerUpdateValutazioneComparto(ValutazioneComparto valutazioneComparto);
    void managerDeleteValutazioneComparto(ValutazioneComparto valutazioneComparto);
    
    
    
 // per i comportamenti organizzativi
    void managerCreateValutazionePop(Valutazione valutazione);
    void managerUpdateValutazionePop(Valutazione valutazione);
    void managerDeleteValutazionePop(Valutazione valutazione);
    
    // per il contributo alle azioni
    void managerUpdateAzioneAssegnazioneValutazione(AzioneAssegnazione azioneAssegnazione);
    void managerUpdateValutazione(Valutazione valutazione);
    
    void managerUpdateCompletamentoAzione(Azione azione);
}
