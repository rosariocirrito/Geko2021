package it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class DocumentoValidator {

	public void validate(Documento documento, Errors errors) {
            if (!StringUtils.hasLength(documento.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		}
            if (!StringUtils.hasLength(documento.getNome())) {
			errors.rejectValue("nome", "required", "required");
		}
            // da completare
            /*
		if (!StringUtils.hasLength(azione.getDenominazione())) {
			errors.rejectValue("denominazione", "required", "required");
		}
		
                if (!StringUtils.hasLength(azione.getProdotti())) {
			errors.rejectValue("prodotti", "required", "required");
		}
                * 
                */
                
	}

 
}