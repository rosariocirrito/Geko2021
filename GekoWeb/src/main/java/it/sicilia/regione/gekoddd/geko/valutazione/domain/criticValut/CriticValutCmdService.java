package it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut;


import java.util.List;

public interface CriticValutCmdService {
	
	// Find by id
	public CriticValut findById(Integer id);
	
	// Insert or update
	public CriticValut save(CriticValut arg0);
	
	// Delete
	public void delete(CriticValut arg0);
	
	//-----------------------------------
		
}
