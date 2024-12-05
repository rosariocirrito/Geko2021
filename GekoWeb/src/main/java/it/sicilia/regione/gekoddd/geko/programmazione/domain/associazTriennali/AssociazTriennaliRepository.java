package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazTriennali;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale.ObiettivoPluriennale;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;

public interface AssociazTriennaliRepository extends CrudRepository<AssociazTriennali, Integer> {
	public List<AssociazTriennali> findByApicale(Obiettivo apicale);
	public List<AssociazTriennali> findByTriennale(ObiettivoPluriennale triennale);
}
