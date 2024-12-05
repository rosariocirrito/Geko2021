package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;

public interface AssociazObiettiviRepository extends CrudRepository<AssociazObiettivi, Integer> {
	public List<AssociazObiettivi> findByApicale(Obiettivo apicale);
	public List<AssociazObiettivi> findByStrategico(ObiettivoStrategico strategico);
}
