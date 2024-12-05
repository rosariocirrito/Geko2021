package it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;

public interface AzioneAssegnazioneRepository extends CrudRepository<AzioneAssegnazione, Integer> {
	public List<AzioneAssegnazione> findByPfID(Integer pfID);
	public List<AzioneAssegnazione> findByAzione(Azione azione);
}
