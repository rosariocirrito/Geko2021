/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sicilia.regione.gekoddd.geko.programmazione.application;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValut;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;

/**
 *
 * @author Cirrito
 */
public interface OivService {
	// 1.
    void sepicosProponeObiettivo(Obiettivo obiettivo);
    // 1.b
    void sepicosRichiedeObiettivo(Obiettivo obiettivo);
    // 2.
    void sepicosUpdateObiettivo(Obiettivo obiettivo);
    void sepicosUpdateIncaricoObiettivo(Obiettivo obiettivo);
    // 3.
    void sepicosDeleteObiettivo(Obiettivo obiettivo);
    
    // 4.
    void sepicosValidaObiettivo(Obiettivo obiettivo);
    // 5.
    void sepicosRivedeObiettivo(Obiettivo obiettivo);  
    // 6.
    void sepicosRespingeObiettivo(Obiettivo obiettivo);    
    // 7.
    void sepicosAnnullaObiettivo(Obiettivo obiettivo); 
    // 8.
    void sepicosRendiApicaleObiettivo(Obiettivo obiettivo); 
    // 8.
    void sepicosClonaObiettivo(Obiettivo obiettivo); 
    
    // aggiorna stati
    void sepicosRealizzaObiettivo(Obiettivo obiettivo);
    void sepicosValutaObiettivo(Obiettivo obiettivo);

    //
    void sepicosCreateAzione(Azione azione);
    void sepicosUpdateAzione(Azione azione);
    void sepicosDeleteAzione(Azione azione);
    void sepicosUpdateCompletamentoAzione(Azione azione);
    void sepicosUpdateRisultatoApicaleAzione(Azione azione);
    void sepicosUpdatePesoAzione(Azione azione);
    void sepicosClonaAzione(Azione azione);
    //void sepicosClonaAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione);
    
    //
    void sepicosCreateCriticita(Criticita criticita);
    void sepicosUpdateCriticita(Criticita criticita);
    void sepicosDeleteCriticita(Criticita criticita);
    
    //
   
    
    
    
   
    
    //
    void sepicosCreateCriticValut(CriticValut criticita);
    void sepicosUpdateCriticValut(CriticValut criticita);
    void sepicosDeleteCriticValut(CriticValut criticita);
    
    //
    void sepicosCreateAssociazObiettivi(AssociazObiettivi associazObiettivi);
    void sepicosUpdateAssociazObiettivi(AssociazObiettivi associazObiettivi);
    void sepicosDeleteAssociazObiettivi(AssociazObiettivi associazObiettivi);
    
    void sepicosCreaObiettivoApicale(Obiettivo obiettivo);
    void sepicosUpdateObiettivoApicale(Obiettivo obiettivo);
    
   
}
