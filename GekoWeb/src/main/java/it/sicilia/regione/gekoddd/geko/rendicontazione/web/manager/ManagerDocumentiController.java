package it.sicilia.regione.gekoddd.geko.rendicontazione.web.manager;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.application.ManagerRendicontazioneService;
import it.sicilia.regione.gekoddd.session.domain.Menu;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoValidator;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Cirrito
 *
 */

@Controller
@SessionAttributes(types = Documento.class)
@RequestMapping("/dirigenteDocumenti")
public class ManagerDocumentiController  {
	
	private Log log = LogFactory.getLog(ManagerDocumentiController.class);
	
    //
	@Autowired
    private AzioneCmdService actCmdServizi;
    @Autowired
    private DocumentoCmdService docCmdServizi;
    @Autowired
    private ManagerRendicontazioneService managerServizi;
    
    @Autowired
    private Menu menu;
    
    //
    public ManagerDocumentiController() { }
    
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
    
    //
    @RequestMapping(value ="{id}/edit" , method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model model) {
        Documento documento = docCmdServizi.findById(id);
        //System.out.println("ManagerDocumentiController.updateForm() per id= "+id);
        //
		if (menu.getIdIncaricoScelta() == documento.getIncaricoID()){
	            model.addAttribute("documento", documento);
	            return "geko/rendicontazione/manager/formDocumentoEditManager";
	        }
	        else return "redirect:/ROLE_MANAGER";
	    }
    //
    @RequestMapping(value ="{id}/edit" , params = "update", method =  RequestMethod.POST )
    public String processUpdateSubmit(@ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerDocumentiController.processUpdateSubmit() obj="+documento.getNomefile());
        new DocumentoValidator().validate(documento, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerDocumentiController.processUpdateSubmit() has errors="+documento.getNomefile());
            return "geko/rendicontazione/manager/formDocumentoEditManager";
		}
		else {
            //System.out.println("ManagerDocumentiController.processUpdateSubmit() no errors="+documento.getNomefile());
            try {
            	documento.setNomefile(documento.getFileData().getOriginalFilename());
            	documento.setTipocontenuto(documento.getFileData().getContentType());
                documento.setContenuto(documento.getFileData().getBytes());
			} catch (IOException e) {
	                e.printStackTrace();
	        }
            this.managerServizi.managerUpdateDocumento(documento);
            status.setComplete();
            return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"
            + documento.getAzione().getObiettivo().getAnno()+"/"
            + documento.getAzione().getObiettivo().getIncaricoID();  
		}
    }
    
    @RequestMapping(value ="{id}/edit" , params = "delete", method =  RequestMethod.POST )
    public String processDeleteSubmit(@PathVariable("id") int id, @ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
    	System.out.println("ManagerDocumentiController delete documento id: "+id+" idDoc: "+documento.getId());
        this.managerServizi.managerDeleteDocumento(docCmdServizi.findById(id));
        //
        return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"
        + documento.getAzione().getObiettivo().getAnno()+"/"
        + documento.getAzione().getObiettivo().getIncaricoID();  
    }
    
    @RequestMapping(value ="{id}/edit" , params = "cancel", method =  RequestMethod.POST )
    public String processCancelSubmit(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
       return "redirect:/ROLE_MANAGER";
    }
    

    // crea documento su azione
    @RequestMapping(value="new/{idAzione}", method = RequestMethod.GET)
    public String createDocumentoManager(@PathVariable("idAzione") int idAzione,Model model) {
        Azione azione = actCmdServizi.findById(idAzione);
        Documento documento = new Documento();
        log.info("ManagerDocumentiController.createDocumentoManager() documento.nuovo= "+documento.isNuovo());
        documento.setAzione(azione);
        List<Documento> docEsistenti = new ArrayList(azione.getDocumenti()); //servizi.findDocumentiOfAzione(azione);
        //
        azione.addDocumentoToAzione(documento);
        model.addAttribute("documento", documento);
        model.addAttribute("listaDocEsistenti",docEsistenti);
        
        return "geko/rendicontazione/manager/formDocumentoCreateManager";
    }
    //
    @RequestMapping(value="new/{idAzione}", params = "add", method = RequestMethod.POST)
    public String processSubmitDocumentoManager(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
        //
        new DocumentoValidator().validate(documento, result);
        if (result.hasErrors()) {
            for(ObjectError error : result.getAllErrors()) {
                log.error("Error: " + error.getCode() +  " - " + error.getDefaultMessage());
            }
            return "geko/rendicontazione/manager/formDocumentoCreateManager";
        }
        else {
            //
        	/*
        	System.out.println("ManagerDocumentiController.processSubmitDocumentoManager() ");
            System.out.println("Name:" + documento.getNome());
            System.out.println("Desc:" + documento.getDescrizione());
            System.out.println("File:" + documento.getFileData().getOriginalFilename());
            System.out.println("ContentType:" + documento.getFileData().getContentType());
            //
            * 
            */
            try {
            	log.info("ManagerDocumentiController.createDocumentoManager() try ");
            	documento.setNomefile(documento.getFileData().getOriginalFilename());
            	documento.setTipocontenuto(documento.getFileData().getContentType());
                documento.setContenuto(documento.getFileData().getBytes());
			} catch (IOException e) {
	                e.printStackTrace();
	        }
            //
            this.managerServizi.managerCreateDocumento(documento);
            //
            status.setComplete();
            return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"
            + documento.getAzione().getObiettivo().getAnno()+"/"
            + documento.getAzione().getObiettivo().getIncaricoID();  
        }
    }
    
    //
    @RequestMapping(value="new/{idAzione}", params = "cancel",method = RequestMethod.POST)
    public String newCancel() {
    	return "redirect:/ROLE_MANAGER";
    }
   
    //
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
    
    
    //
    @RequestMapping(value ="{id}/editDescrizione" , method = RequestMethod.GET )
    public String editDescrizioneGet(@PathVariable("id") int id, Model model) {
        Documento documento = docCmdServizi.findById(id);
        //System.out.println("ManagerDocumentiController.updateForm() per id= "+id);
        //
		if (menu.getIdIncaricoScelta() == documento.getIncaricoID()){
	            model.addAttribute("documento", documento);
	            return "geko/rendicontazione/manager/formDocumentoEditDescrizioneManager";
	        }
	        else return "redirect:/ROLE_MANAGER";
	    }
    //
    @RequestMapping(value ="{id}/editDescrizione" , params = "update", method =  RequestMethod.POST )
    public String editDescrizionePost(@ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerDocumentiController.processUpdateSubmit() obj="+documento.getNomefile());
        new DocumentoValidator().validate(documento, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerDocumentiController.processUpdateSubmit() has errors="+documento.getNomefile());
            return "geko/rendicontazione/manager/formDocumentoEditDescrizioneManager";
		}
		else {
            //System.out.println("ManagerDocumentiController.processUpdateSubmit() no errors="+documento.getNomefile());
            
            this.managerServizi.managerUpdateDocumento(documento);
            status.setComplete();
            return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"
            + documento.getAzione().getObiettivo().getAnno()+"/"
            + documento.getAzione().getObiettivo().getIncaricoID();  
		}
    }
    
    @RequestMapping(value ="{id}/editDescrizione" , params = "delete", method =  RequestMethod.POST )
    public String editDescrizioneDelete(@PathVariable("id") int id, @ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
    	System.out.println("ManagerDocumentiController delete documento id: "+id+" idDoc: "+documento.getId());
        this.managerServizi.managerDeleteDocumento(docCmdServizi.findById(id));
        //
        return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"
        + documento.getAzione().getObiettivo().getAnno()+"/"
        + documento.getAzione().getObiettivo().getIncaricoID();  
    }
    
    @RequestMapping(value ="{id}/editDescrizione" , params = "cancel", method =  RequestMethod.POST )
    public String editDescrizioneCancel(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
       return "redirect:/ROLE_MANAGER";
    }
    
    
    //
    @RequestMapping(value ="{id}/editFile" , method = RequestMethod.GET )
    public String editFileGet(@PathVariable("id") int id, Model model) {
        Documento documento = docCmdServizi.findById(id);
        //System.out.println("ManagerDocumentiController.updateForm() per id= "+id);
        //
		if (menu.getIdIncaricoScelta() == documento.getIncaricoID()){
	            model.addAttribute("documento", documento);
	            return "geko/rendicontazione/manager/formDocumentoEditFileManager";
	        }
	        else return "redirect:/ROLE_MANAGER";
	    }
    //
    @RequestMapping(value ="{id}/editFile" , params = "update", method =  RequestMethod.POST )
    public String editFilePost(@ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
        //System.out.println("ManagerDocumentiController.processUpdateSubmit() obj="+documento.getNomefile());
        new DocumentoValidator().validate(documento, result);
		if (result.hasErrors()) {
            //System.out.println("ManagerDocumentiController.processUpdateSubmit() has errors="+documento.getNomefile());
            return "geko/rendicontazione/manager/formDocumentoEditFileManager";
		}
		else {
            //System.out.println("ManagerDocumentiController.processUpdateSubmit() no errors="+documento.getNomefile());
            try {
            	documento.setNomefile(documento.getFileData().getOriginalFilename());
            	documento.setTipocontenuto(documento.getFileData().getContentType());
                documento.setContenuto(documento.getFileData().getBytes());
			} catch (IOException e) {
	                e.printStackTrace();
	        }
            this.managerServizi.managerUpdateDocumento(documento);
            status.setComplete();
            return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"
            + documento.getAzione().getObiettivo().getAnno()+"/"
            + documento.getAzione().getObiettivo().getIncaricoID();  
		}
    }
    
    @RequestMapping(value ="{id}/editFile" , params = "delete", method =  RequestMethod.POST )
    public String editFileDelete(@PathVariable("id") int id, @ModelAttribute Documento documento, 
                                BindingResult result, 
                                SessionStatus status) {
    	System.out.println("ManagerDocumentiController delete documento id: "+id+" idDoc: "+documento.getId());
        this.managerServizi.managerDeleteDocumento(docCmdServizi.findById(id));
        //
        return "redirect:/dirigente/modifyRendicontazioneIncaricoManager/"
        + documento.getAzione().getObiettivo().getAnno()+"/"
        + documento.getAzione().getObiettivo().getIncaricoID();  
    }
    
    @RequestMapping(value ="{id}/editFile" , params = "cancel", method =  RequestMethod.POST )
    public String editFileCancel(@ModelAttribute Documento documento,
                                BindingResult result, 
                                SessionStatus status) {
       return "redirect:/ROLE_MANAGER";
    }
    
    
} // ------------------------------
