package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma;

import java.util.List;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;

public interface AssociazProgrammaQryService {
	
	//-----------------------------------
		
	public List<AssociazProgramma> findByApicale(Obiettivo apicale);
	
	public List<AssociazProgramma> findByProgramma(Programma programma);
	
}
