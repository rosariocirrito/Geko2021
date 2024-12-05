package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;

public interface ObiettivoStrategicoRepository extends CrudRepository<ObiettivoStrategico, Integer> {
	//public ObiettivoStrategico findByDescrizione(String descrizione); 
	public List<ObiettivoStrategico> findByAreaStrategicaAndAnno(AreaStrategica area, int anno); 
}
