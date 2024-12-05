package it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica;




public interface AreaStrategicaCmdService {
		
	// Find by id
	AreaStrategica findById(Integer id);
	
	// Insert or update 
	AreaStrategica save(AreaStrategica areaStrategica);
	
	// Delete 
	void delete(AreaStrategica areaStrategica);
	
}
