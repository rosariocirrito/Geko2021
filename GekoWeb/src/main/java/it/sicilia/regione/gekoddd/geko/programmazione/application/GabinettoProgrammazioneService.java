package it.sicilia.regione.gekoddd.geko.programmazione.application;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;

/**
 *
 * @author Cirrito
 */
public interface GabinettoProgrammazioneService {
	
	Evento cmdGabinettoConcordaObiettivoApicale(Obiettivo obiettivo);
	Evento cmdGabinettoRendiInterlocutorioObiettivo(Obiettivo obiettivo);
	
	void gabinettoRichiedeObiettivo(Obiettivo obiettivo);
	void gabinettoUpdateObiettivo(Obiettivo obiettivo);
    void gabinettoRespingeObiettivo(Obiettivo obiettivo);   
    void gabinettoDeleteObiettivo(Obiettivo obiettivo);    
   
    //
    void gabinettoCreateAssociazObiettivi(AssociazObiettivi associazObiettivi);
    void gabinettoUpdateAssociazObiettivi(AssociazObiettivi associazObiettivi);
    void gabinettoDeleteAssociazObiettivi(AssociazObiettivi associazObiettivi);
    
    //
    void gabinettoCreateAssociazProgramma(AssociazProgramma associazProgramma);
    void gabinettoUpdateAssociazProgramma(AssociazProgramma associazProgramma);
    void gabinettoDeleteAssociazProgramma(AssociazProgramma associazProgramma);
    
}
