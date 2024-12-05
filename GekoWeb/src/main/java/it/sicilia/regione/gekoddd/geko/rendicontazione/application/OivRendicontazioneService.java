/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sicilia.regione.gekoddd.geko.rendicontazione.application;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivazione.OivAzione;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.oivobiettivo.OivObiettivo;

/**
 *
 * @author Cirrito
 */
public interface OivRendicontazioneService {
	   
    //
    void oivCreateOivAzione(OivAzione oivAzione);
    void oivUpdateOivAzione(OivAzione oivAzione);
    void oivDeleteOivAzione(OivAzione oivAzione);

    
    //
    void oivCreateOivObiettivo(OivObiettivo oivObiettivo);
    void oivUpdateOivObiettivo(OivObiettivo oivObiettivo);
    void oivDeleteOivObiettivo(OivObiettivo oivObiettivo);
    
}
