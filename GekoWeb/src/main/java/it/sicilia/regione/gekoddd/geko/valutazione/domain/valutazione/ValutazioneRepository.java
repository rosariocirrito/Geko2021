package it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ValutazioneRepository extends CrudRepository<Valutazione, Integer> {
	public List<Valutazione> findByIncaricoIDAndAnno(Integer incaricoID, int anno);
}
