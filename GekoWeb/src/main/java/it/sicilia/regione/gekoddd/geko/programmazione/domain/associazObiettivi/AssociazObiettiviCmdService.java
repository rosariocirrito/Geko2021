package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi;

import java.util.List;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;

public interface AssociazObiettiviCmdService {
	
	// Find by id
	public AssociazObiettivi findById(Integer id);
	
	// Insert or update a contact	
	public AssociazObiettivi save(AssociazObiettivi associazObiettivi);
	
	// Delete a contact	
	public void delete(AssociazObiettivi associazObiettivi);
	
	//-----------------------------------
	
}
