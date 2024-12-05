package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazTriennali;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennale;
import java.util.List;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;

public interface AssociazTriennaliQryService {
	
	//-----------------------------------
		
	public List<AssociazTriennali> findByApicale(Obiettivo apicale);
	
	public List<AssociazTriennali> findByTriennale(ObiettivoPluriennale triennale);
	
}
