package it.sicilia.regione.gekoddd.log.model.journal;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface JournalRepository extends CrudRepository<Journal, Integer> {
	//
	public List<Journal> findByChi(String chi);
	
	public List<Journal> findByChiAndDove(String chi, String dove);
	
	public List<Journal> findByQuandoGreaterThanOrderByChiAsc(Date quando);	
	
	public List<Journal> findByChiAndQuandoGreaterThanOrderByChiAsc(String chi,Date quando);	
	
}
