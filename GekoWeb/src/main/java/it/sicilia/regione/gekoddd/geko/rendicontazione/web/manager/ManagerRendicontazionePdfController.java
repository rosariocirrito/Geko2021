package it.sicilia.regione.gekoddd.geko.rendicontazione.web.manager;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/dirigentePdf")
public class ManagerRendicontazionePdfController  {
	//
    @Autowired
    private ObiettivoQryService objServizi;
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    //
    public ManagerRendicontazionePdfController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 
    
  
    
 
    
 // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfRendicontazioneIncaricoManager/{anno}/{idIncarico}")
    public void pdfRendicontazioneIncaricoManager(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// cfr iText in Action p.285 e 289
    	try {
    		String text = request.getParameter("text");
    		if (text == null || text.trim().length() == 0 ) {
    			text = "You didn't enter any text. ";
    		}
    	Document doc = new Document(PageSize.A3);
	    // L'oggetto baosPDF conterr� i caratteri che costituiscono il file PDF crea il pdf in memoria
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;
	    // Crea l'associazione tra l'oggetto di tipo ByteArrayOutputStream che rappresenta il PDF e il documento
	    // Ritorna un oggetto di tipo PdfWriter
	    docWriter = PdfWriter.getInstance(doc, baos);
		// Apro il documento
		doc.open();
		//
		/*
		 * genero il contenuto del documento     
		 */
		
		// -----------------------    
		//
    	
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko responsabile = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
	    final List<Obiettivo> listObiettivi = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(idIncarico, anno);
    	
        
		// Aggiungo due paragrafi e una riga vuota
	    //doc.add(new Paragraph("Questo documento � stato creato da una classe chiamata: " +
	    //this.getClass().getName()));
	    //doc.add(new Paragraph("Questo documento � stato creato il " + new java.util.Date()));
	    //doc.add(new Paragraph("\n"));  
	    // Riga vuota
	    //doc.add(new Paragraph("\n"));
    	
    	//Font font1 = new Font(FontFamily.HELVETICA, 6, Font.BOLD);
	    
    	// Genero una tabella
	    PdfPTable table = new PdfPTable(7);
	    // define relative columns width p.96
	    table.setWidths(new int[] {3,6,3,2,6,6,6});
	    // first header row
	    PdfPCell cell = new PdfPCell(new Phrase("Rendicontazione Obiettivi per "+incarico.getDenominazioneStruttura()+" - anno:"+anno));
	    cell.setColspan(7);
	    cell.setBackgroundColor(Color.CYAN);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    
	    table.addCell(cell);
	    // aggiunge il 2 header ed il footer
	    table.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);
	    for (int i = 0; i<2; i++){
	    	//PdfPCell cellh = new PdfPCell(new Phrase("Responsabile "+struttura.getResponsabile().getStringa(), font1));
	    	PdfPCell cellh = new PdfPCell(new Phrase("Responsabile "+responsabile.getCognomeNome()));
	    	cellh.setColspan(7);
	    	cellh.setBackgroundColor(Color.LIGHT_GRAY);
		    cellh.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	table.addCell(cellh);
	    }
	    //
	    table.getDefaultCell().setBackgroundColor(null);
	    table.setHeaderRows(3); // 3 header rows
	    table.setFooterRows(1); // di cui 1 footer
	    //
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
	    // vedi p 113 per header ripetuti
	    
	    //
	    for (Obiettivo obj : listObiettivi){
	    	String apicale ="";
	    	if (obj.isApicale()) apicale = "APICALE - ";
	    	PdfPCell cellObj = new PdfPCell(new Phrase(
	    			apicale 
	    			+" - "+ obj.getCodice() 
	    			+" - "+ obj.getDescrizione() 
	    			+" - "+ obj.getNote()
	    			+" - "+ obj.getPeso()
	    			));
	    	cellObj.setColspan(7);
	    	cellObj.setBackgroundColor(Color.YELLOW);
	    	cellObj.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	table.addCell(cellObj);
	    	for (Azione act : obj.getAzioni()){
	    		PdfPCell cellAct = new PdfPCell(new Phrase("Azione: "+ act.getDenominazione() +" - " +act.getDescrizione()));
	    		cellAct.setColspan(7);
	    		cellAct.setBackgroundColor(Color.ORANGE);
	    		cellAct.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	table.addCell(cellAct);
		    	//
		    	PdfPCell cellx = new PdfPCell(new Phrase("Indicatore"));
		    	cellx.setBackgroundColor(Color.CYAN);
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase("Valore Obiettivo"));
		    	cellx.setBackgroundColor(Color.CYAN);
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase("Scad.za"));
		    	cellx.setBackgroundColor(Color.CYAN);
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase("Peso"));
		    	cellx.setBackgroundColor(Color.CYAN);
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase("Risultato"));
		    	cellx.setBackgroundColor(Color.CYAN);
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase("Documenti"));
		    	cellx.setBackgroundColor(Color.CYAN);
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase("Indicazioni"));
		    	cellx.setBackgroundColor(Color.CYAN);
		    	table.addCell(cellx);
		    	//
		    	cellx = new PdfPCell(new Phrase(""+act.getIndicatore()));
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase(act.getProdotti()));
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase(sdf.format(act.getScadenza())));
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase(""+act.getPeso()));
		    	table.addCell(cellx);
		    	cellx = new PdfPCell(new Phrase(""+act.getRisultato()));
		    	table.addCell(cellx);
		    	//
		    	String docs = "";
		    	for (Documento docum : act.getDocumenti()){
		    		docs += docum.getDescrizione() + " \n";
		    	}
		    	cellx = new PdfPCell(new Phrase(docs));
		    	table.addCell(cellx);
		    	String indics = "";
		    	for (Criticita crit : act.getCriticita()){
		    		indics += crit.getIndicazioni() + " \n";
		    	}
		    	cellx = new PdfPCell(new Phrase(indics));
		    	table.addCell(cellx);
	    	}
	    }
	    
	    doc.add(table);
		// ------------------------------
	    
	    /*
	     * Chiudo il documento
	     */
	    //  e l'oggetto docWriter
		doc.close();
		//docWriter.close();
		// add extra response headers
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	    response.setHeader("Pragma", "public") ;
	    // set content type
	    response.setContentType("application/pdf");
	    // set content length
	    response.setContentLength(baos.size());
	    OutputStream os = response.getOutputStream();
		baos.writeTo(os);
	    os.flush();
		os.close(); 
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			throw new IOException(e.getMessage());
		}    	
    } // fine metodo
    
    
} // ---------------------------------------------
