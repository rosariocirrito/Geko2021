package it.sicilia.regione.gekoddd.log.model.journal;


import java.util.Date;
import java.util.List;

public interface JournalService {

	
	// Find a contact with details by id
	public Journal findById(Integer id);
	
	// Insert or update a contact	
	public Journal save(Journal arg0);
	
	// Delete a contact	
	public void delete(Journal arg0);
	
	//-----------------------------------
	public List<Journal> findByChi(String chi);
	
	public List<Journal> findByChiAndDove(String chi, String dove);	
	
	public List<Journal> findByQuandoAfter(Date quando);	
	
	public List<Journal> findByChiAndQuandoAfter(String chi, Date quando);
	
	//public List<Journal> findByStrutturaAndAnno(OpPersonaGiuridica struttura, int anno);
}
