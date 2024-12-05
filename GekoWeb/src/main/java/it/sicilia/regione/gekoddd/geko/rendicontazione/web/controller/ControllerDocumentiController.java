/**
 * 
 */
package it.sicilia.regione.gekoddd.geko.rendicontazione.web.controller;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.application.ControllerRendicontazioneService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.application.ManagerRendicontazioneService;
import it.sicilia.regione.gekoddd.session.domain.Menu;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoValidator;

import org.springframework.validation.ObjectError;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@SessionAttributes(types = Documento.class)
@RequestMapping("/controllerDocumenti")
public class ControllerDocumentiController  {
	
	private Log log = LogFactory.getLog(ControllerDocumentiController.class);
	
    //
	@Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private DocumentoCmdService docCmdServizi;
    @Autowired
    private ControllerRendicontazioneService controllerServizi;
    
    
    @Autowired
    private Menu menu;
    
    //
    public ControllerDocumentiController() { }
    
    
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
    
    /*
    // display a single  information (cfr. Pro Spring 3 chp 17 p.621)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") int id, Model model) {
        Documento documento = docCmdServizi.findById(id);  
        model.addAttribute(documento);
	return "controller/formDocumentoController";
    }
    */
    
    
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
        Documento documento = docCmdServizi.findById(id);
        //System.out.println("ControllerDocumentiController.updateForm() per id= "+id);
        //
        log.info("{id}/edit documento.getIncaricoID():"+documento.getIncaricoID()+" / menu.getIncaricoApicaleDept().getIdIncarico() "+menu.getIncaricoApicaleDept().getIdIncarico());
		if (documento.getIncaricoID().equals(menu.getIncaricoApicaleDept().getIdIncarico())){
	            model.addAttribute("documento", documento);
	            return "geko/rendicontazione/controller/formDocumentoController";
	        }
	        else return "redirect:/ROLE_CONTROLLER";
	    }
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.PUT )
    public String processUpdateSubmit(@ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerDocumentiController.processUpdateSubmit() obj="+documento.getNomefile());
        new DocumentoValidator().validate(documento, result);
		if (result.hasErrors()) {
            //System.out.println("ControllerDocumentiController.processUpdateSubmit() has errors="+documento.getNomefile());
            return "geko/rendicontazione/controller/formDocumentoController";
		}
		else {
            //System.out.println("ControllerDocumentiController.processUpdateSubmit() no errors="+documento.getNomefile());
            try {
            	documento.setNomefile(documento.getFileData().getOriginalFilename());
            	documento.setTipocontenuto(documento.getFileData().getContentType());
                documento.setContenuto(documento.getFileData().getBytes());
			} catch (IOException e) {
	                e.printStackTrace();
	        }
            this.controllerServizi.controllerUpdateDocumento(documento);
            status.setComplete();
            return "redirect:/controllerRend/modifyRendicontazioneIncaricoApicaleController/"+ documento.getAzione().getObiettivo().getAnno()+
            		"/"+documento.getAzione().getObiettivo().getIncaricoID();  
		}
    }
    
    @RequestMapping(value ="{id}/edit" , params = "delete", method =  RequestMethod.PUT )
    public String processDeleteSubmit(@ModelAttribute Documento documento,  
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteDocumentoApicale(documento);
        // non funziona vedi manager o più avanti editDescrizione
        //
        return "redirect:/controllerRend/modifyRendicontazioneIncaricoApicaleController/"+ documento.getAzione().getObiettivo().getAnno()+
        		"/"+documento.getAzione().getObiettivo().getIncaricoID();
    }
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.POST )
    public String processCancelSubmit(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
       return "redirect:/ROLE_CONTROLLER";
    }

    // -------------------------------------------------------------------------------
    // crea documento su azione per rendicontazione apicale
    @RequestMapping(value="new/{idAzione}", method = RequestMethod.GET)
    public String createDocumentoController(@PathVariable("idAzione") int idAzione,Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        Documento documento = new Documento();
        //System.out.println("ControllerDocumentiController.createDocumentoController() documento.nuovo= "+documento.isNuovo());
        documento.setAzione(azione);
        List<Documento> docEsistenti = new ArrayList(azione.getDocumenti());//servizi.findDocumentiOfAzione(azione);
        //
        azione.addDocumentoToAzione(documento);
        model.addAttribute("documento", documento);
        model.addAttribute("listaDocEsistenti",docEsistenti);
        
        return "geko/rendicontazione/controller/formDocumentoCreateController";
    }
    //
    @RequestMapping(value="new/{idAzione}", params = "add", method = RequestMethod.POST)
    public String processSubmitDocumentoController(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
        //
        new DocumentoValidator().validate(documento, result);
        if (result.hasErrors()) {
            for(ObjectError error : result.getAllErrors()) {
                System.out.println("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
            }
            return "geko/rendicontazione/controller/formDocumentoCreateController";
        }
        else {
            //
        	
            
            try {
            	documento.setNomefile(documento.getFileData().getOriginalFilename());
            	documento.setTipocontenuto(documento.getFileData().getContentType());
                documento.setContenuto(documento.getFileData().getBytes());
			} catch (IOException e) {
	                e.printStackTrace();
	        }
	           
            //
            this.controllerServizi.controllerCreateDocumento(documento);
            //
            status.setComplete();
            return "redirect:/controllerRend/modifyRendicontazioneIncaricoApicaleController/"+ documento.getAzione().getObiettivo().getAnno()+
            		"/"+documento.getAzione().getObiettivo().getIncaricoID();
        }
    }
    //
    @RequestMapping(value="new/{idAzione}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_CONTROLLER";
    }
    
   
    // ----------------------------------------------------------------------------------------
    @RequestMapping(value = "/download/{documentId}", method = RequestMethod.GET)
	public void downloadDocumento(@PathVariable("documentId") Integer documentId, HttpServletResponse response) {
    	Documento doc = docCmdServizi.findById(documentId);
        
        if (doc.getContenuto() != null) {
    		//logger.info("Downloading photo for id: {} with size: {}", contact.getId(), contact.getPhoto().length);
        	response.setHeader("Content-Disposition", "inline;filename=\"" +doc.getNomefile()+ "\"");
        	response.setContentType(doc.getTipocontenuto());
        	try {
				OutputStream out = response.getOutputStream();
				out.write(doc.getContenuto());
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
    
    
    // -------------------------------
    @RequestMapping(value ="{id}/elimina" , method = RequestMethod.GET )
    public String eliminaFormGet(@PathVariable("id") int id, Model model) {
        Documento documento = docCmdServizi.findById(id);
        //log.info("documento.getIncaricoID()"+documento.getIncaricoID()+" / "+"menu.getIdIncaricoDeptScelta"+menu.getIdIncaricoDeptScelta());
        //
        //if (documento.getIncaricoID() == menu.getIdIncaricoDeptScelta()){
        	
	            model.addAttribute("documento", documento);
	            return "geko/rendicontazione/controller/formDocumentoEliminaController";
	    //    }
	    //    else return "redirect:/ROLE_CONTROLLER";
	    }
    //
   
    
    @RequestMapping(value ="{id}/elimina" , params = "elimina", method =  RequestMethod.POST )
    public String eliminaFormDelete(@PathVariable("id") int id, @ModelAttribute Documento documento,  
                                BindingResult result, 
                                SessionStatus status) {
    	//int id = documento.getId();
    	//System.out.println("ControllerDocumentiController.eliminaFormDelete() filename= "+documento.getNomefile());
    	//System.out.println("ControllerDocumentiController.eliminaFormDelete() id= "+id);
        if (id>0){
	        Documento doc = docCmdServizi.findById(id);
	    	this.controllerServizi.controllerDeleteDocumento(doc);
        }
        //
        return "redirect:/ROLE_CONTROLLER";
    }
    
    @RequestMapping(value ="{id}/elimina" , params = "cancel", method =  RequestMethod.POST )
    public String eliminaFormCancel(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
       return "redirect:/ROLE_CONTROLLER";
    }
    
    
    // ----------------------------------------------------------------

    @RequestMapping(value ="{id}/editDescrizione" , method = RequestMethod.GET )
    public String editDescrizioneGet(@PathVariable("id") int id, Model model) {
        Documento documento = docCmdServizi.findById(id);
        //System.out.println("ControllerDocumentiController.updateForm() per id= "+id);
        //
        //log.info("{id}/edit documento.getIncaricoID():"+documento.getIncaricoID()+" / menu.getIncaricoApicaleDept().getIdIncarico() "+menu.getIncaricoApicaleDept().getIdIncarico());
		model.addAttribute("documento", documento);
	    return "geko/rendicontazione/controller/formDocumentoEditDescrizioneController";
	        
	    }
    //
    @RequestMapping(value ="{id}/editDescrizione" , params = "update", method =  RequestMethod.PUT )
    public String editDescrizionePut(@ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerDocumentiController.processUpdateSubmit() obj="+documento.getNomefile());
        new DocumentoValidator().validate(documento, result);
		if (result.hasErrors()) {
            //System.out.println("ControllerDocumentiController.processUpdateSubmit() has errors="+documento.getNomefile());
            return "geko/rendicontazione/controller/formDocumentoEditDescrizioneController";
		}
		else {
            //System.out.println("ControllerDocumentiController.processUpdateSubmit() no errors="+documento.getNomefile());
            
            this.controllerServizi.controllerUpdateDocumento(documento);
            status.setComplete();
            if(documento.getAzione().getObiettivo().isApicale()){
            return "redirect:/controllerRend/modifyRendicontazioneIncaricoApicaleController/"+ documento.getAzione().getObiettivo().getAnno()+
            		"/"+documento.getAzione().getObiettivo().getIncaricoID();
            }
            else{
            return "redirect:/controllerRend/verifyRendicontazioneDipartimentaleController/"+ documento.getAzione().getObiettivo().getAnno()+
            		"/"+menu.getIncaricoApicaleDept().getIdIncarico();
			}
		}
    }
    
    @RequestMapping(value ="{id}/editDescrizione" , params = "delete", method =  RequestMethod.PUT )
    public String editDescrizioneDel(@PathVariable("id") int id, @ModelAttribute Documento documento,  
                                BindingResult result, 
                                SessionStatus status) {
        this.controllerServizi.controllerDeleteDocumentoApicale(docCmdServizi.findById(id));
        
        //
        if(documento.getAzione().getObiettivo().isApicale()){
            return "redirect:/controllerRend/modifyRendicontazioneIncaricoApicaleController/"+ documento.getAzione().getObiettivo().getAnno()+
            		"/"+documento.getAzione().getObiettivo().getIncaricoID();
            }
            else{
            return "redirect:/controllerRend/verifyRendicontazioneDipartimentaleController/"+ documento.getAzione().getObiettivo().getAnno()+
            		"/"+menu.getIncaricoApicaleDept().getIdIncarico(); 
			}
    }
    
    @RequestMapping(value ="{id}/editDescrizione" , params = "cancel", method =  RequestMethod.POST )
    public String editDescrizioneCanc(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
       return "redirect:/ROLE_CONTROLLER";
    }


    
    
    
    // -----------------------------------------
    @RequestMapping(value ="{id}/editFile" , method = RequestMethod.GET )
    public String editFileGet(@PathVariable("id") int id, Model model) {
        Documento documento = docCmdServizi.findById(id);
        //System.out.println("ControllerDocumentiController.updateForm() per id= "+id);
        //
        log.info("{id}/edit documento.getIncaricoID():"+documento.getIncaricoID()+" / menu.getIncaricoApicaleDept().getIdIncarico() "+menu.getIncaricoApicaleDept().getIdIncarico());
		
        model.addAttribute("documento", documento);
        return "geko/rendicontazione/controller/formDocumentoEditFileController";
	        
	    }
    //
    @RequestMapping(value ="{id}/editFile" , params = "update", method =  RequestMethod.POST )
    public String editFilePut(@ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ControllerDocumentiController.processUpdateSubmit() obj="+documento.getNomefile());
        new DocumentoValidator().validate(documento, result);
		if (result.hasErrors()) {
            //System.out.println("ControllerDocumentiController.processUpdateSubmit() has errors="+documento.getNomefile());
            return "geko/rendicontazione/controller/formDocumentoEditFileController";
		}
		else {
            //System.out.println("ControllerDocumentiController.processUpdateSubmit() no errors="+documento.getNomefile());
            try {
            	documento.setNomefile(documento.getFileData().getOriginalFilename());
            	documento.setTipocontenuto(documento.getFileData().getContentType());
                documento.setContenuto(documento.getFileData().getBytes());
			} catch (IOException e) {
	                e.printStackTrace();
	        }
            this.controllerServizi.controllerUpdateDocumento(documento);
            status.setComplete();
            return "redirect:/controllerRend/modifyRendicontazioneIncaricoApicaleController/"+ documento.getAzione().getObiettivo().getAnno()+
            		"/"+documento.getAzione().getObiettivo().getIncaricoID();  
		}
    }
    
    @RequestMapping(value ="{id}/editFile" , params = "delete", method =  RequestMethod.POST )
    public String editFileDelete(@PathVariable("id") int id, @ModelAttribute Documento documento,  
                                BindingResult result, 
                                SessionStatus status) {
    	this.controllerServizi.controllerDeleteDocumentoApicale(docCmdServizi.findById(id));
        //
        return "redirect:/controllerRend/modifyRendicontazioneIncaricoApicaleController/"+ documento.getAzione().getObiettivo().getAnno()+
        		"/"+documento.getAzione().getObiettivo().getIncaricoID();
    }
    
    @RequestMapping(value ="{id}/editFile" , params = "cancel", method =  RequestMethod.POST )
    public String editFileCancel(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
       return "redirect:/ROLE_CONTROLLER";
    }


    
    
} // ------------------------------
