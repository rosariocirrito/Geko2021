package it.sicilia.regione.gekoddd.geko.programmazione.domain.programma;


import java.util.List;

public interface ProgrammaCmdService {
		
	// Find by id
	Programma findById(Integer id);
	
	// Insert or update 
	Programma save(Programma missione);
	
	// Delete 
	void delete(Programma missione);
	
}
