package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;

public interface ObiettivoPluriennaleRepository extends CrudRepository<ObiettivoPluriennale, Integer> {
	//public ObiettivoStrategico findByDescrizione(String descrizione); 
	List<ObiettivoPluriennale> findByIncaricoID(Integer incaricoID); 
}