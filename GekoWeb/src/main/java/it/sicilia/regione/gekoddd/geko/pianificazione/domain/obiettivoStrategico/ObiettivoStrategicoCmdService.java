package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico;

public interface ObiettivoStrategicoCmdService {

	// Find a contact with details by id
	public ObiettivoStrategico findById(Integer id);
	
	// Insert or update a contact	
	public ObiettivoStrategico save(ObiettivoStrategico obiettivoStrategico);
	
	// Delete a contact	
	public void delete(ObiettivoStrategico obiettivoStrategico);
	
}
