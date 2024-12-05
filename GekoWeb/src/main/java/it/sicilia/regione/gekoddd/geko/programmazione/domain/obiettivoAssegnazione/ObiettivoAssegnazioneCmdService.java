package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione;


public interface ObiettivoAssegnazioneCmdService {
	
	// Find by id
	public ObiettivoAssegnazione findById(Integer id);
	
	// Insert or update 
	public ObiettivoAssegnazione save(ObiettivoAssegnazione obiettivoAssegnazione);
	
	// Delete 
	public void delete(ObiettivoAssegnazione obiettivoAssegnazione);
	
	
}
