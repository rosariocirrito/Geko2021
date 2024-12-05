package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;


public interface ObiettivoAssegnazioneRepository extends CrudRepository<ObiettivoAssegnazione, Integer> {
	public List<ObiettivoAssegnazione> findByIdPersonaAndAnno(int pfID, int anno);
	public List<ObiettivoAssegnazione> findByIdStrutturaAndAnno(int pgID, int anno);
	public List<ObiettivoAssegnazione> findByIdObiettivo(int idObiettivo);
	public List<ObiettivoAssegnazione> findByIdPersonaAndAndAnno(int pfID, int idIncarico, int anno);
}
