package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ObiettivoRepository extends CrudRepository<Obiettivo, Integer> {
	//
	//public List<Obiettivo> findByAnno(int anno);
	
	//public List<Obiettivo> findByStrutturaAndAnno(OpPersonaGiuridica struttura, int anno);
	//public List<Obiettivo> findByStrutturaAndAnnoAndApicale(OpPersonaGiuridica struttura, int anno, boolean apicale);
	
	public List<Obiettivo> findByAnnoAndApicale(int anno, boolean apicale);
	//public List<Obiettivo> findByStrutturaAndAnnoAndIncarico(OpPersonaGiuridica struttura, int anno, Incarico incarico);
	
	public List<Obiettivo> findByIncaricoIDAndAnno(Integer incaricoID , int anno);
	public List<Obiettivo> findByIncaricoIDAndAnnoAndApicale(Integer incaricoID , int anno, boolean apicale);
	public List<Obiettivo> findByCodiceAndAnno(String codice , int anno);
}