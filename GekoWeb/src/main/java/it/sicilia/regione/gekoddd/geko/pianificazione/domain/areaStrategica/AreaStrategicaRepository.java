package it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

public interface AreaStrategicaRepository extends CrudRepository<AreaStrategica, Integer> {
	//
	public List<AreaStrategica> findByAnnoInizioLessThanAndAnnoFineGreaterThan(int annoInizio, int annoFine);
	
}
