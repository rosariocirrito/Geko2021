/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.programmazione.web.oiv;

import it.sicilia.regione.gekoddd.geko.programmazione.application.ControllerProgrammazioneService;
import it.sicilia.regione.gekoddd.geko.programmazione.application.OivService;
import it.sicilia.regione.gekoddd.session.domain.Menu;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneValidator;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Cirrito
 *
 */

@Controller
@SessionAttributes(types = Azione.class)
@RequestMapping("/oivAct")
public class OivAzioniController  {
    
	@Autowired
    private OivService oivServizi;
	@Autowired
    private AzioneCmdService actCmdServizi;
	@Autowired
	private Menu menu;
	
    public OivAzioniController() { }
    
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("idAzione");
    }
    
    
    // --------------------------- ok sepicos
    @RequestMapping(value ="{idAzione}/editCompletamento/{idIncaricoApicale}" , method = RequestMethod.GET )
    public String editCompletamentoGet(@PathVariable("idAzione") int idAzione,
    		@PathVariable("idIncaricoApicale") int idIncaricoApicale,
    		Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        azione.setIdIncaricoApicale(idIncaricoApicale);
            model.addAttribute("azione", azione);
            return "geko/valutazione/oiv/formAzioneCompletamentoOiv";
	}
    //
    @RequestMapping(value ="{idAzione}/editCompletamento/{idIncaricoApicale}" , params = "update", method =  RequestMethod.PUT )
    public String UpdateRisultatoSubmit(@ModelAttribute Azione azioneApicale, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerMenuController.processUpdateSubmit() obj="+obiettivo.getDenominazione());
        new AzioneValidator().validate(azioneApicale, result);
		if (result.hasErrors()) {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() has errors="+obiettivo.getDenominazione());
			return "geko/valutazione/oiv/formAzioneCompletamentoOiv";
		}
		else {
	            //System.out.println("ManagerMenuController.processUpdateSubmit() no errors="+obiettivo.getDenominazione());
	            this.oivServizi.sepicosUpdateCompletamentoAzione(azioneApicale);
	            status.setComplete();
	            return "redirect:/oivValut/modifyValutazioneIncaricoApicaleOiv/"+
	            menu.getAnno()+"/"+azioneApicale.getIdIncaricoApicale();
		}
    }
    //
    @RequestMapping(value ="{idAzione}/editCompletamento/{idIncaricoApicale}" , params = "cancel", method =  RequestMethod.PUT )
    public String CancelRisultatoSubmit() {
        return "redirect:/ROLE_OIV";
    }
    
} // -------------------------------------------------------------
