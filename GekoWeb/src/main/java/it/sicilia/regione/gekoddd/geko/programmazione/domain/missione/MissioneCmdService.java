package it.sicilia.regione.gekoddd.geko.programmazione.domain.missione;


import java.util.List;

public interface MissioneCmdService {
		
	// Find by id
	Missione findById(Integer id);
	
	// Insert or update 
	Missione save(Missione missione);
	
	// Delete 
	void delete(Missione missione);
	
}
