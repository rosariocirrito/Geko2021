package it.sicilia.regione.gekoddd.geko.valutazione.application;

import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValut;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;

/**
 *
 * @author Cirrito
 */
public interface ControllerValutazioneService {       
    //
    void controllerCreateValutazione(Valutazione valutazione);
    void controllerUpdateValutazione(Valutazione valutazione);
    void controllerDeleteValutazione(Valutazione valutazione);
    
    //
    void controllerCreateCriticValut(CriticValut criticita);
    void controllerUpdateCriticValut(CriticValut criticita);
    void controllerDeleteCriticValut(CriticValut criticita);
    
 // per i comportamenti organizzativi
    void controllerCreateValutazioneComparto(ValutazioneComparto valutazioneComparto);
    void controllerUpdateValutazioneComparto(ValutazioneComparto valutazioneComparto);
    void controllerDeleteValutazioneComparto(ValutazioneComparto valutazioneComparto);
    
}
