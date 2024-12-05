package it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class AreaStrategicaValidator {
	public void validate(AreaStrategica areaStrategica, Errors errors) {
		if (!StringUtils.hasLength(areaStrategica.getCodice())) {
			errors.rejectValue("codice", "required", "required");
		}
		if (areaStrategica.getCodice().length()>8) {
			errors.rejectValue("codice", "lunghezza max = 2", "max 8 caratteri!");
		}
		if (!StringUtils.hasLength(areaStrategica.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		}
		if (areaStrategica.getAnnoInizio()==0) {
			errors.rejectValue("annoInizio", "validare l'anno", "aggiornare l'anno nel menu");
		} 
		if (areaStrategica.getAnnoFine()==0) {
			errors.rejectValue("annoFine", "validare l'anno", "aggiornare l'anno nel menu");
		} 
		//
	}
}
