/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sicilia.regione.gekoddd.geko.pianificazione.application;

import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome.Outcome;
import it.sicilia.regione.gekoddd.log.model.evento.Evento;

/**
 *
 * @author Cirrito
 */
public interface SuperGabinettoPianificazioneService {
	
	Evento cmdSuperGabinettoCreateAreaStrategica(AreaStrategica areaStrategica);
	Evento cmdSuperGabinettoUpdateAreaStrategica(AreaStrategica areaStrategica);
	Evento cmdSuperGabinettoDeleteAreaStrategica(AreaStrategica areaStrategica);
	
	
	Evento cmdSuperGabinettoCreateObiettivoStrategico(ObiettivoStrategico obiettivoStrategico);
	Evento cmdSuperGabinettoUpdateObiettivoStrategico(ObiettivoStrategico obiettivoStrategico);
	Evento cmdSuperGabinettoDeleteObiettivoStrategico(ObiettivoStrategico obiettivoStrategico);
		
	Evento cmdSuperGabinettoCreateOutcome(Outcome outcome);
	Evento cmdSuperGabinettoUpdateOutcome(Outcome outcome);
	Evento cmdSuperGabinettoDeleteOutcome(Outcome outcome);
}
