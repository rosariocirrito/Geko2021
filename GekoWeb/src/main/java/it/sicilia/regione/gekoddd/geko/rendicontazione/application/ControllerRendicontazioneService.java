/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sicilia.regione.gekoddd.geko.rendicontazione.application;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;

/**
 *
 * @author Cirrito
 */
public interface ControllerRendicontazioneService {
	   
    //
    void controllerCreateCriticita(Criticita criticita);
    void controllerUpdateCriticita(Criticita criticita);
    void controllerDeleteCriticita(Criticita criticita);

    void controllerEditaRisultato(Azione azione);
    //
    void controllerCreateDocumento(Documento documento);
    void controllerUpdateDocumento(Documento documento);
    void controllerDeleteDocumento(Documento documento);
    void controllerDeleteDocumentoApicale(Documento documento);
}
