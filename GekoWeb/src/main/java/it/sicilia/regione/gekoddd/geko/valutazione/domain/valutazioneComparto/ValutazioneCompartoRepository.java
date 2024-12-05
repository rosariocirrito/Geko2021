package it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ValutazioneCompartoRepository extends CrudRepository<ValutazioneComparto, Integer> {
	public List<ValutazioneComparto> findByPfID(Integer pfID);
	public List<ValutazioneComparto> findByPfIDAndAnno(Integer pfID, int anno);
}
