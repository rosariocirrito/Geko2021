package it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.programma.Programma;

public interface AssociazProgrammaRepository extends CrudRepository<AssociazProgramma, Integer> {
	public List<AssociazProgramma> findByApicale(Obiettivo apicale);
	public List<AssociazProgramma> findByProgramma(Programma programma);
}
