package it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento;

public interface DocumentoCmdService {
	
	// Find by id
	public Documento findById(Integer id);
	
	// Insert or update 
	public Documento save(Documento arg0);
	
	// Delete 
	public void delete(Documento arg0);
	
	//-----------------------------------
		
}
