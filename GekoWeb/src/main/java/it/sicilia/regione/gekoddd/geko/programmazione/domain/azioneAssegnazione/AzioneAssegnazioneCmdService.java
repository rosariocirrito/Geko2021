package it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione;

import java.util.List;

public interface AzioneAssegnazioneCmdService {
	
	// Find by id
	public AzioneAssegnazione findById(Integer id);
	
	// Insert or update 
	public AzioneAssegnazione save(AzioneAssegnazione azioneAssegnazione);
	
	// Delete 
	public void delete(AzioneAssegnazione azioneAssegnazione);
	
	
}
