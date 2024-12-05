package it.sicilia.regione.gekoddd.geko.programmazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneComparto;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPCellEvent;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Cirrito
 *
 */

@Controller
@RequestMapping("/controllerPdf")
public class ControllerProgrPdfController  {
	
	private Log log = LogFactory.getLog(ControllerProgrPdfController.class);
	
	//
    @Autowired
    private ObiettivoQryService objServizi;
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private AssociazProgrammaQryService associazProgrammaServizi;
    
    @Autowired
    private ValutazioneQryService valutazioneDirigServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    
    
    /** A table event. */
    public PdfPTableEvent tableBackground;
    /** A cell event. */
    public PdfPCellEvent cellBackground;
    /** A cell event. */
    public PdfPCellEvent roundRectangle;
    /** A cell event. */
    public PdfPCellEvent whiteRectangle;
    
    // imposto i font
    private Font fontTitle1 = new Font();
    private Font fontTitle2 = new Font();
    private Font fontTitle3 = new Font();
    private Font fontObj = new Font();
    private Font fontBold = new Font();
    private Font fontAct = new Font();
   
    private void setFonts(){
        fontTitle1.setFamily("HELVETICA");
        fontTitle1.setStyle("BOLD");
        fontTitle1.setColor(Color.BLUE);
        fontTitle1.setSize(24.0f);
        //
        fontTitle2.setFamily("HELVETICA");
        fontTitle2.setStyle("BOLD");
        fontTitle2.setColor(Color.BLACK);
        fontTitle2.setSize(20.0f);
        //
        fontTitle3.setFamily("HELVETICA");
        fontTitle3.setStyle("BOLD");
        fontTitle3.setColor(Color.BLACK);
        fontTitle3.setSize(16.0f);
        //
        fontObj.setFamily("HELVETICA");
        //fontObj.setStyle("ITALICS");
        fontObj.setColor(Color.BLACK);
        fontObj.setSize(12.0f);
        //
        fontBold.setFamily("HELVETICA");
        fontBold.setColor(Color.BLACK);
        fontBold.setSize(12.0f);
        //
        fontAct.setFamily("CALIBRI");
        //fontObj.setStyle("ITALICS");
        fontAct.setColor(Color.BLACK);
        fontAct.setSize(11.0f);
    }
		

    /**
     * Inner class with a table event that draws a background with rounded corners.
     */
    class TableBackground implements PdfPTableEvent {
 
        public void tableLayout(PdfPTable table, float[][] width, float[] height,
                int headerRows, int rowStart, PdfContentByte[] canvas) {
            PdfContentByte background = canvas[PdfPTable.BASECANVAS];
            background.saveState();
            background.setCMYKColorFill(0x00, 0x00, 0xFF, 0x0F);
            background.roundRectangle(
                width[0][0], height[height.length - 1] - 2,
                width[0][1] - width[0][0] + 6, height[0] - height[height.length - 1] - 4, 4);
            background.fill();
            background.restoreState();
        }
 
    }
 
    /**
     * Inner class with a cell event that draws a background with rounded corners.
     */
    class CellBackground implements PdfPCellEvent {
 
        public void cellLayout(PdfPCell cell, Rectangle rect,
                PdfContentByte[] canvas) {
            PdfContentByte cb = canvas[PdfPTable.BACKGROUNDCANVAS];
            cb.roundRectangle(
                rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3,
                rect.getHeight() - 3, 4);
            cb.setCMYKColorFill(0x00, 0x00, 0x00, 0x00);
            cb.fill();
        }
    }
 
    /**
     * Inner class with a cell event that draws a border with rounded corners.
     */
    class RoundRectangle implements PdfPCellEvent {
        /** the border color described as CMYK values. */
        protected int[] color;
        /** Constructs the event using a certain color. */
        public RoundRectangle(int[] color) {
            this.color = color;
        }
 
        public void cellLayout(PdfPCell cell, Rectangle rect,
                PdfContentByte[] canvas) {
            PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
            cb.roundRectangle(
                rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3,
                rect.getHeight() - 3, 4);
            cb.setLineWidth(1.5f);
            cb.setCMYKColorStrokeF(color[0], color[1], color[2], color[3]);
            cb.stroke();
        }
    }
 
    // ---------------------------- metodi -----------------------------------------
    
    //
    public ControllerProgrPdfController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    
   
    //-----------------------Pianificazione Apicale ---- 2019 ----------------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfPianificazioneIncaricoController/{anno}/{idIncarico}")
    public void pdfPianificazioneIncaricoController(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// estraggo le collections che mi servono     	
    	IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	IncaricoGeko incaricoApic = menu.getIncaricoApicaleDept();
    	List<Obiettivo> listObiettivi = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettivi);
    	//
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(idIncarico, anno); // una sola in realt�
    	if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
    		incarico.setValutazioni(lstValutDirig);
    	}
    	else {
    		List<Valutazione> emptyList = new ArrayList<Valutazione>();
    		incarico.setValutazioni(lstValutDirig);
    	}

        tableBackground = new TableBackground();
        cellBackground = new CellBackground();
        roundRectangle = new RoundRectangle(new int[]{ 0xFF, 0x00, 0xFF, 0x00 });
        whiteRectangle = new RoundRectangle(new int[]{ 0x00, 0x00, 0x00, 0x00 });
        
    	// cfr iText in Action p.285 e 289
    	try {
    		String text = request.getParameter("text");
    		if (text == null || text.trim().length() == 0 ) {
    			text = "You didn't enter any text. ";
    		}
    	//Document doc = new Document(PageSize.A4.rotate());
    	//Document doc = new Document(PageSize.A3);
            Document doc = new Document(PageSize.A4);
            this.setFonts();
	    // L'oggetto baosPDF conterr� i caratteri che costituiscono il file PDF crea il pdf in memoria
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;
	    // Crea l'associazione tra l'oggetto di tipo ByteArrayOutputStream che rappresenta il PDF e il documento
	    // Ritorna un oggetto di tipo PdfWriter
	    docWriter = PdfWriter.getInstance(doc, baos);
	    
            // Apro il documento
            doc.open();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
            PdfContentByte canvas = docWriter.getDirectContent();
            //
            // scrivo sulla prima pagina il titolo del report
            Paragraph titolo = new Paragraph("PROGRAMMAZIONE",fontTitle1);
            titolo.setAlignment("CENTER");
            doc.add(titolo);
            // Riga vuota
	    doc.add(new Paragraph("\n"));
	    // scrivo sulla prima pagina il sottotitolo del report
            Paragraph subTitolo = new Paragraph(incarico.denominazioneStruttura+" - anno "+anno,fontTitle2);
            subTitolo.setAlignment("CENTER");
	    doc.add(subTitolo);
	    	   
	    // titolo 2 responsabile: P:M.
	    Paragraph titolo2;
	    if (null != incarico.responsabile) {
		    titolo2 = new Paragraph(incarico.carica+": " +incarico.responsabile);
	    } else{
	    	titolo2 = new Paragraph("RESPONSABILE DA INSERIRE !!! (manca incarico");
	    }
	    titolo2.setAlignment("CENTER");
     	doc.add(titolo2);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));	   
	    // salto-pagina
            doc.newPage();		
            // verifico se lista vuota
	    if (listObiettivi.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Obiettivo trovato per "+incarico.denominazioneStruttura+" - anno:"+anno);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
    	// scheda 1
	    else {
	    	this.generaSchedaObiettivi(doc, incarico, incaricoApic,anno);
	    	//this.addFirme(doc,incaricoApic,inc);
	    	this.generaSchedeDettaglioObiettivi(doc, incarico, incaricoApic,anno);
		    //this.addFirme(doc,incaricoApic,inc);
	    }
		
		 
            // ------------------------------
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
    	} // fine metodo pdfPianificazioneIncaricoController -----------------------------------------------------------------
   

    
 
  //-----------------------Pianificazione Apicale ---- 2019 ----------------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfPianificazioneApicaleIncaricoController/{anno}/{idIncarico}")
    public void pdfPianificazioneApicaleIncaricoController(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        
    	// estraggo le collections che mi servono 
    	final IncaricoGeko incaricoApic = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incaricoApic.setObiettivi(listObiettiviApicali);
    	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(incaricoApic.idIncarico, anno); // una sola in realt�
    	incaricoApic.setValutazioni(lstValutDirig);

        tableBackground = new TableBackground();
        cellBackground = new CellBackground();
        roundRectangle = new RoundRectangle(new int[]{ 0xFF, 0x00, 0xFF, 0x00 });
        whiteRectangle = new RoundRectangle(new int[]{ 0x00, 0x00, 0x00, 0x00 });
        
    	// cfr iText in Action p.285 e 289
    	try {
    		String text = request.getParameter("text");
    		if (text == null || text.trim().length() == 0 ) {
    			text = "You didn't enter any text. ";
    		}
    	//Document doc = new Document(PageSize.A4.rotate());
    	//Document doc = new Document(PageSize.A3);
		Document doc = new Document(PageSize.A4);
                this.setFonts();
			// L'oggetto baosPDF conterr� i caratteri che costituiscono il file PDF crea il pdf in memoria
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;
	    // Crea l'associazione tra l'oggetto di tipo ByteArrayOutputStream che rappresenta il PDF e il documento
	    // Ritorna un oggetto di tipo PdfWriter
	    docWriter = PdfWriter.getInstance(doc, baos);
	    
            // Apro il documento
            doc.open();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
            PdfContentByte canvas = docWriter.getDirectContent();
            //
		
		
		// scrivo sulla prima pagina il titolo del report
		
		Paragraph titolo = new Paragraph("PROGRAMMAZIONE APICALE",fontTitle1);
		titolo.setAlignment("CENTER");
            doc.add(titolo);
            // Riga vuota
	    doc.add(new Paragraph("\n"));
	    // scrivo sulla prima pagina il sottotitolo del report
	 	Paragraph subTitolo = new Paragraph(incaricoApic.denominazioneStruttura+" - anno "+anno,fontTitle2);
	 	subTitolo.setAlignment("CENTER");
	    doc.add(subTitolo);
	    	   
	    // titolo 2 responsabile: P:M.
	    Paragraph titolo2;
	    if (null != incaricoApic.responsabile) {
            titolo2 = new Paragraph(incaricoApic.carica+": " + incaricoApic.responsabile,fontTitle3);
        } else{
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale",fontTitle3);
	    }
	    titolo2.setAlignment("CENTER");
     	doc.add(titolo2);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	   
	    // salto-pagina
		doc.newPage();
		
		// verifico se lista vuota
	    if (listObiettiviApicali.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Obiettivo Apicale trovato per "+incaricoApic.denominazioneStruttura+" - anno:"+anno, fontTitle3);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
    	// scheda 1
	    else {
	    	this.generaSchedaObiettiviApicali(doc, incaricoApic,anno);
	    	//this.addFirme(doc,incaricoApic,inc);
	    	this.generaSchedeDettaglioObiettiviApicali(doc, incaricoApic,anno);
		    //this.addFirme(doc,incaricoApic,inc);
	    }
		
		 
		// ------------------------------
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
    	} // fine metodo pdfPianificazioneApicaleIncaricoController -----------------------------------------------------------------
   

    
    
    
  //-----------------------Pianificazione Dipartimentale ------------ 2019  --------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfProgrammazioneDirigenzialeController/{anno}/{idIncarico}")
    public void pdfProgrammazioneDirigenzialeController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// estraggo le collections che mi servono     	
    	final IncaricoGeko incaricoApic = fromOrganikoServizi.findIncaricoById(idIncarico); 
    	List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiDirigenzialiByDipartimentoIDAndAnno(incaricoApic.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){            
            List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(inc.idIncarico, anno);
            inc.setObiettivi(lstObjs);
            List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(inc.idIncarico, anno); // una sola in realta'
            if (lstValutDirig!=null && !lstValutDirig.isEmpty()) {
                inc.setValutazioni(lstValutDirig);
            }
            else {
                List<Valutazione> emptyList = new ArrayList<Valutazione>();
                inc.setValutazioni(lstValutDirig);
            }        	
        }
              
        tableBackground = new TableBackground();
        cellBackground = new CellBackground();
        roundRectangle = new RoundRectangle(new int[]{ 0xFF, 0x00, 0xFF, 0x00 });
        whiteRectangle = new RoundRectangle(new int[]{ 0x00, 0x00, 0x00, 0x00 });
        
    	// cfr iText in Action p.285 e 289
    	try {
            String text = request.getParameter("text");
            if (text == null || text.trim().length() == 0 ) {
                    text = "You didn't enter any text. ";
            }
        //Document doc = new Document(PageSize.A3.rotate());
    	//Document doc = new Document(PageSize.A3);
            Document doc = new Document(PageSize.A4);
			// L'oggetto baosPDF conterr� i caratteri che costituiscono il file PDF crea il pdf in memoria
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;
	    // Crea l'associazione tra l'oggetto di tipo ByteArrayOutputStream che rappresenta il PDF e il documento
	    // Ritorna un oggetto di tipo PdfWriter
	    docWriter = PdfWriter.getInstance(doc, baos);
	    
            // Apro il documento
            doc.open();
            PdfContentByte canvas = docWriter.getDirectContent();
            //
            this.setFonts();
        
            // scrivo sulla prima pagina il titolo del report
            Paragraph titolo = new Paragraph("PROGRAMMAZIONE DIPARTIMENTALE DIRIGENZIALE",fontTitle1);
            titolo.setAlignment("CENTER");
            doc.add(titolo);
            // Riga vuota
	    doc.add(new Paragraph("\n"));
	    // scrivo sulla prima pagina il sottotitolo del report
            Paragraph subTitolo = new Paragraph(incaricoApic.denominazioneStruttura+" - anno "+anno,fontTitle1);
            subTitolo.setAlignment("CENTER");
	    doc.add(subTitolo);
	    	   
	    // titolo 2 responsabile: P:M.
	    Paragraph titolo2;
	    if (null != incaricoApic.responsabile) {
		    titolo2 = new Paragraph(incaricoApic.carica+": "+incaricoApic.responsabile,fontTitle3);
	    } else{
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale");
	    }
	    titolo2.setAlignment("CENTER");
            doc.add(titolo2);
            // Riga vuota
	    doc.add(new Paragraph("\n"));
	   
	    
		// verifico se lista vuota
	    if (listIncarichiDept.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Incarico trovato per "+incaricoApic.denominazioneStruttura+" - anno:"+anno);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
    	
	    // itero sulla lista 
	    for (IncaricoGeko inc : listIncarichiDept) {
                // salto-pagina
                doc.newPage();			
	    	// scheda 1
	    	this.generaSchedaObiettivi(doc, inc,incaricoApic,anno);			    	
		// schede 2	    	
	    	// itero sugli obiettivi
                this.generaSchedeDettaglioObiettivi(doc, inc,incaricoApic,anno);                
	    // ------------------------------
	    } // fine loop incarico
	   
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
                throw new IOException(e.getMessage());
            }
    } // fine metodo pdfProgrammazioneDipartimentaleController ---------------------------------------
    
     @RequestMapping(value="pdfProgrammazionePopController/{anno}/{idIncarico}")
    public void pdfProgrammazionePopController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// estraggo le collections che mi servono     	
    	final IncaricoGeko incaricoApic = fromOrganikoServizi.findIncaricoById(idIncarico); 
    	List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiPopByDipartimentoIDAndAnno(incaricoApic.pgID, anno);
        for(IncaricoGeko incPop : listIncarichiDept){
            List<Obiettivo> listObiettivi = objServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incPop.getIdIncarico(), anno);
            if(!listObiettivi.equals(null) && !listObiettivi.isEmpty()) {                           
                for (Obiettivo obj : listObiettivi){
                    obj.setIncarico(incPop);
                    obj.setIncaricoPadreID(idIncarico); 
                }			    	
                incPop.setObiettivi(listObiettivi);
            }
            List<Valutazione> lstValutPop = valutazioneDirigServizi.findByIncaricoIDAndAnno(incPop.idIncarico, anno); // una sola in realta'
            if (lstValutPop!=null && !lstValutPop.isEmpty()) {
                    incPop.setValutazioni(lstValutPop);
                }      	
        }
        //listIncarichi.addAll(listIncarichiDept);
              
        tableBackground = new TableBackground();
        cellBackground = new CellBackground();
        roundRectangle = new RoundRectangle(new int[]{ 0xFF, 0x00, 0xFF, 0x00 });
        whiteRectangle = new RoundRectangle(new int[]{ 0x00, 0x00, 0x00, 0x00 });
        
    	// cfr iText in Action p.285 e 289
    	try {
            String text = request.getParameter("text");
            if (text == null || text.trim().length() == 0 ) {
                    text = "You didn't enter any text. ";
            }
        //Document doc = new Document(PageSize.A3.rotate());
    	//Document doc = new Document(PageSize.A3);
            Document doc = new Document(PageSize.A4);
			// L'oggetto baosPDF conterr� i caratteri che costituiscono il file PDF crea il pdf in memoria
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;
	    // Crea l'associazione tra l'oggetto di tipo ByteArrayOutputStream che rappresenta il PDF e il documento
	    // Ritorna un oggetto di tipo PdfWriter
	    docWriter = PdfWriter.getInstance(doc, baos);
	    
            // Apro il documento
            doc.open();
            PdfContentByte canvas = docWriter.getDirectContent();
            //
            this.setFonts();
        
            // scrivo sulla prima pagina il titolo del report
            Paragraph titolo = new Paragraph("PROGRAMMAZIONE DIPARTIMENTALE POP",fontTitle1);
            titolo.setAlignment("CENTER");
            doc.add(titolo);
            // Riga vuota
	    doc.add(new Paragraph("\n"));
	    // scrivo sulla prima pagina il sottotitolo del report
            Paragraph subTitolo = new Paragraph(incaricoApic.denominazioneStruttura+" - anno "+anno,fontTitle1);
            subTitolo.setAlignment("CENTER");
	    doc.add(subTitolo);
	    	   
	    // titolo 2 responsabile: P:M.
	    Paragraph titolo2;
	    if (null != incaricoApic.responsabile) {
		    titolo2 = new Paragraph(incaricoApic.carica+": "+incaricoApic.responsabile,fontTitle3);
	    } else{
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale");
	    }
	    titolo2.setAlignment("CENTER");
            doc.add(titolo2);
            // Riga vuota
	    doc.add(new Paragraph("\n"));
	   
	    
		// verifico se lista vuota
	    if (listIncarichiDept.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Incarico trovato per "+incaricoApic.denominazioneStruttura+" - anno:"+anno);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
    	
	    // itero sulla lista 
	    for (IncaricoGeko inc : listIncarichiDept) {
                // salto-pagina
                doc.newPage();			
	    	// scheda 1
	    	this.generaSchedaObiettivi(doc, inc,incaricoApic,anno);			    	
		// schede 2	    	
	    	// itero sugli obiettivi
                this.generaSchedeDettaglioObiettivi(doc, inc,incaricoApic,anno);                
	    // ------------------------------
	    } // fine loop incarico
	   
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
                throw new IOException(e.getMessage());
            }
    } // fine metodo pdfProgrammazioneDipartimentaleController ---------------------------------------
    
 
    // **************************** METODI PRIVATI **********************************************************************
    // -------------------------------------------------------------------------------------
    private void generaSchedaObiettivi(Document doc, IncaricoGeko inc, IncaricoGeko incaricoApicale, int anno){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
    	//
    	//
    	PdfPTable tableProgr = new PdfPTable(1);
    	//
    	tableProgr.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	tableProgr.getDefaultCell().setCellEvent(whiteRectangle);
    	PdfPCell cellriga1 = new PdfPCell(new Paragraph("SCHEDA PROGRAMMAZIONE 1: programmazione obiettivi del dirigente ai fini della successiva valutazione della performance - Anno "+anno,fontTitle3));
    	tableProgr.addCell(cellriga1);    	
    	
    	//tableStru.setTableEvent(tableBackground);
    	//tableStru.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	//tableStru.getDefaultCell().setCellEvent(whiteRectangle);
    	PdfPCell cellStru = new PdfPCell(new Paragraph("STRUTTURA: "+inc.denominazioneStruttura,fontBold));
    	tableProgr.addCell(cellStru);
    	String nomeResponsabile="";
    	if(inc.responsabile != null){
    		nomeResponsabile = inc.responsabile;
    	}
        String strInc = inc.carica+": "+nomeResponsabile;
        if(inc.interim) strInc += " INTERIM";
    	PdfPCell cellResp = new PdfPCell(new Paragraph(strInc,fontBold));
    	tableProgr.addCell(cellResp);
    	//PdfPCell competenze = new PdfPCell(new Paragraph(mapItem.getKey().getCompetenze()));
    	//tableStru.addCell(competenze);
    	//
    	
	    // salto riga
	    try {
			doc.add(new Paragraph("\n"));
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		        
	    PdfPCell cellTitlePerfOper = new PdfPCell(new Paragraph("PERFORMANCE OPERATIVA",fontTitle3));
            cellTitlePerfOper.setBackgroundColor(Color.LIGHT_GRAY);
	    cellTitlePerfOper.setHorizontalAlignment(Element.ALIGN_CENTER);
    	tableProgr.addCell(cellTitlePerfOper);
    	try {
			doc.add(tableProgr);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	// genero la scheda 1
    	int nrObj = 0;
    	PdfPTable tableSk1 = new PdfPTable(7);
    	try {
			tableSk1.setWidths(new int[] {10,43,10,15,15,15,12});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// riga intestazione tabella obiettivi
	    PdfPCell thNrObj = new PdfPCell(new Phrase("Nr.",fontObj));
	    thNrObj.setBackgroundColor(Color.LIGHT_GRAY);
	    thNrObj.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thNrObj);
	    //
	    PdfPCell thObj = new PdfPCell(new Phrase("Descrizione sintetica obiettivo",fontObj));
	    thObj.setBackgroundColor(Color.LIGHT_GRAY);
	    thObj.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thObj);
	    //
	    PdfPCell thStr = new PdfPCell(new Phrase("Corr. ob. Strategico",fontObj));
	    thStr.setBackgroundColor(Color.LIGHT_GRAY);
	    thStr.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thStr);
	    //
	    PdfPCell thIndic = new PdfPCell(new Phrase("Indicatore",fontObj));
	    thIndic.setBackgroundColor(Color.LIGHT_GRAY);
	    thIndic.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thIndic);
	    //
	    PdfPCell thVal = new PdfPCell(new Phrase("Valore Obiettivo",fontObj));
	    thVal.setBackgroundColor(Color.LIGHT_GRAY);
	    thVal.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thVal);
	    //
	    PdfPCell thData = new PdfPCell(new Phrase("Data ultima",fontObj));
	    thData.setBackgroundColor(Color.LIGHT_GRAY);
	    thData.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thData);
	    //
	    PdfPCell thPeso = new PdfPCell(new Phrase("Peso",fontObj));
	    thPeso.setBackgroundColor(Color.LIGHT_GRAY);
	    thPeso.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thPeso);
    	
    	// itero sugli obiettivi
    	for(Obiettivo obj : inc.getObiettivi()){
    		nrObj++;
    		//
		    // row obj
	    	// nr
		    PdfPCell colNrObj = new PdfPCell(new Phrase(""+nrObj,fontObj));
		    colNrObj.setBackgroundColor(Color.LIGHT_GRAY);
		    colNrObj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(colNrObj);
		    // righe descriz e note
		    PdfPCell cellobj;
		    cellobj = new PdfPCell(new Paragraph(obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote(),fontObj));		
		    cellobj.setBackgroundColor(Color.WHITE);
		    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(cellobj);
		    // strategico
		    String strStrat = "";
		    if(!obj.getAssociazObiettivi().isEmpty()){
			    for (AssociazObiettivi ass : obj.getAssociazObiettivi()){
			    	strStrat += (ass.getStrategico().getCodice() + " ");
			    }		    
		    }
		    PdfPCell cellStrat = new PdfPCell(new Paragraph(strStrat,fontObj));
		    cellStrat.setBackgroundColor(Color.WHITE);
		    cellStrat.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(cellStrat);
		    // indicatore
		    PdfPCell cellIndObj = new PdfPCell(new Phrase(" - ",fontObj));
		    if(null != obj.getIndicatore()){
		    cellIndObj = new PdfPCell(new Phrase(obj.getIndicatore(),fontObj));
		    }
		    tableSk1.addCell(cellIndObj);
		    // valore obiettivo
		    PdfPCell cellValObj = new PdfPCell(new Phrase(" - ",fontObj));
		    if(null != obj.getValObiettivo()){
		    	cellValObj = new PdfPCell(new Phrase(obj.getValObiettivo(),fontObj));
		    }
		    tableSk1.addCell(cellValObj);
		    // data ultima
		    PdfPCell cellDataObj = new PdfPCell(new Phrase(" - ",fontObj));
		    if(null != obj.getDataUltima()){
			    cellDataObj = new PdfPCell(new Phrase(sdf.format(obj.getDataUltima()),fontObj));			    
			    tableSk1.addCell(cellDataObj);
		    }
		    // peso
		    PdfPCell cellPeso = new PdfPCell(new Paragraph(""+obj.getPeso(),fontObj));			
		    cellPeso.setBackgroundColor(Color.WHITE);
		    cellPeso.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableSk1.addCell(cellPeso);
		    //
		    
    	} // fine loop obiettivi
    	try {
			doc.add(tableSk1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// riga Totale performance operativa
    	PdfPTable tabletpo = new PdfPTable(2);
    	try {
			tabletpo.setWidths(new int[] {5,1});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    PdfPCell cellatpo = new PdfPCell(new Phrase("Totale conseguibile performance operativa",fontBold));
            
	    cellatpo.setBackgroundColor(Color.WHITE);
	    cellatpo.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tabletpo.addCell(cellatpo);
	    //
	    PdfPCell cellatpo2 = new PdfPCell(new Phrase(""+inc.getTotPesoObiettivi(),fontObj));
	    cellatpo2.setBackgroundColor(Color.WHITE);
	    cellatpo2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tabletpo.addCell(cellatpo2);
	    try {
			doc.add(tabletpo);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 // valutazioni
		for (Valutazione valutazione : inc.getValutazioni()){
			
                    if (valutazione.getAnno() == anno){
                        // tabella attuazione piano di lavoro
                        PdfPTable tableValutaz = new PdfPTable(3);
                        // define relative columns width p.96
                        try {
                                tableValutaz.setWidths(new int[] {4,1,1});
                        } catch (DocumentException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                    }
                    if(inc.incaricoDirigenziale){
                    // prima intestazione tabella valutazione
                    PdfPCell cellah1 = new PdfPCell(new Phrase("COMPORTAMENTO ORGANIZZATIVO",fontTitle3));
                    cellah1.setBackgroundColor(Color.LIGHT_GRAY);
                    cellah1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellah1.setColspan(3);
                    tableValutaz.addCell(cellah1);
                    // seconda intestazione tabella valutazione
                    PdfPCell cellah2 = new PdfPCell(new Phrase("QUALITA' GESTIONALI-RELAZIONALI (selezionare 3 su 4)",fontBold));
                    cellah2.setBackgroundColor(Color.LIGHT_GRAY);
                    cellah2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellah2);
                    PdfPCell cellah3 = new PdfPCell(new Phrase("Range assegnabile",fontBold));
                    cellah3.setBackgroundColor(Color.LIGHT_GRAY);
                    cellah3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellah3);
                    PdfPCell cellah4 = new PdfPCell(new Phrase("Peso attribuito",fontBold));
                    cellah4.setBackgroundColor(Color.LIGHT_GRAY);
                    cellah4.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tableValutaz.addCell(cellah4);
                    //
                    // riga Gestione
                    PdfPCell cellar1 = new PdfPCell(new Phrase("Capacità di intercettare, gestire risorse e programmare",fontObj));
                    cellar1.setBackgroundColor(Color.WHITE);
                    cellar1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tableValutaz.addCell(cellar1);
                    PdfPCell cellagrr2 = new PdfPCell(new Phrase("5-20",fontObj));
                    cellagrr2.setBackgroundColor(Color.WHITE);
                    cellagrr2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellagrr2);
                    PdfPCell cellagrr3 = new PdfPCell(new Phrase("" + valutazione.getGestRealAss(),fontObj));
                    cellagrr3.setBackgroundColor(Color.WHITE);
                    cellagrr3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellagrr3);
                    // riga Analisi e Programmazione
                    PdfPCell cellaapr1 = new PdfPCell(new Phrase("Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione",fontObj));
                    cellaapr1.setBackgroundColor(Color.WHITE);
                    cellaapr1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tableValutaz.addCell(cellaapr1);
                    PdfPCell cellaapr2 = new PdfPCell(new Phrase("5-20",fontObj));
                    cellaapr2.setBackgroundColor(Color.WHITE);
                    cellaapr2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellaapr2);
                    PdfPCell cellaapr3 = new PdfPCell(new Phrase("" + valutazione.getAnalProgrAss(),fontObj));
                    cellaapr3.setBackgroundColor(Color.WHITE);
                    cellaapr3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellaapr3);
                    // riga Relazione e Coordinamento
                    PdfPCell cellarcr1 = new PdfPCell(new Phrase("Capacità di valorizzare competenze e attitudini dei propri collaboratori",fontObj));
                    cellarcr1.setBackgroundColor(Color.WHITE);
                    cellarcr1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tableValutaz.addCell(cellarcr1);
                    PdfPCell cellarcr2 = new PdfPCell(new Phrase("5-20",fontObj));
                    cellarcr2.setBackgroundColor(Color.WHITE);
                    cellarcr2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellarcr2);
                    PdfPCell cellarcr3 = new PdfPCell(new Phrase("" + valutazione.getRelazCoordAss(),fontObj));
                    cellarcr3.setBackgroundColor(Color.WHITE);
                    cellarcr3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellarcr3);
                    // riga priorità
                    PdfPCell cellarr1 = new PdfPCell(new Phrase("Capacità di individuazione del livello di priorità degli interventi da realizzare",fontObj));
                    cellarr1.setBackgroundColor(Color.WHITE);
                    cellarr1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    tableValutaz.addCell(cellarr1);
                    PdfPCell cellar2 = new PdfPCell(new Phrase("5-20",fontObj));
                    cellar2.setBackgroundColor(Color.WHITE);
                    cellar2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellar2);
                    PdfPCell cellar3 = new PdfPCell(new Phrase("" + valutazione.getPdlAss(),fontObj));
                    cellar3.setBackgroundColor(Color.WHITE);
                    cellar3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableValutaz.addCell(cellar3);
                }
                else if(inc.incaricoPop) {
                        // prima intestazione tabella valutazione
                        PdfPCell cellah1 = new PdfPCell(new Phrase("COMPORTAMENTO ORGANIZZATIVO",fontTitle3));
                        cellah1.setBackgroundColor(Color.LIGHT_GRAY);
                        cellah1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cellah1.setColspan(3);
                        tableValutaz.addCell(cellah1);
                        // seconda intestazione tabella valutazione
                        PdfPCell cellah2 = new PdfPCell(new Phrase("QUALITA' GESTIONALI-RELAZIONALI",fontTitle3));
                        cellah2.setBackgroundColor(Color.LIGHT_GRAY);
                        cellah2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tableValutaz.addCell(cellah2);
                        PdfPCell cellah3 = new PdfPCell(new Phrase("Range assegnabile",fontTitle3));
                        cellah3.setBackgroundColor(Color.LIGHT_GRAY);
                        cellah3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tableValutaz.addCell(cellah3);
                        PdfPCell cellah4 = new PdfPCell(new Phrase("Peso attribuito",fontTitle3));
                        cellah4.setBackgroundColor(Color.LIGHT_GRAY);
                        cellah4.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tableValutaz.addCell(cellah4);
                        //
                        // riga Relazione e Coordinamento
                        PdfPCell cellarcr1 = new PdfPCell(new Phrase("Capacità di organizzazione del lavoro",fontObj));
                        cellarcr1.setBackgroundColor(Color.WHITE);
                        cellarcr1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tableValutaz.addCell(cellarcr1);
                        PdfPCell cellarcr2 = new PdfPCell(new Phrase("5-20",fontObj));
                        cellarcr2.setBackgroundColor(Color.WHITE);
                        cellarcr2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tableValutaz.addCell(cellarcr2);
                        PdfPCell cellarcr3 = new PdfPCell(new Phrase("" + valutazione.getRelazCoordAss(),fontObj));
                        cellarcr3.setBackgroundColor(Color.WHITE);
                        cellarcr3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tableValutaz.addCell(cellarcr3);
                        // riga Analisi e Programmazione
                        PdfPCell cellaapr1 = new PdfPCell(new Phrase("Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione",fontObj));
                        cellaapr1.setBackgroundColor(Color.WHITE);
                        cellaapr1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tableValutaz.addCell(cellaapr1);
                        PdfPCell cellaapr2 = new PdfPCell(new Phrase("5-20",fontObj));
                        cellaapr2.setBackgroundColor(Color.WHITE);
                        cellaapr2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tableValutaz.addCell(cellaapr2);
                        PdfPCell cellaapr3 = new PdfPCell(new Phrase("" + valutazione.getAnalProgrAss(),fontObj));
                        cellaapr3.setBackgroundColor(Color.WHITE);
                        cellaapr3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tableValutaz.addCell(cellaapr3);

                        // riga priorità
                        PdfPCell cellarr1 = new PdfPCell(new Phrase("Capacità di individuazione del livello di priorità degli interventi da realizzare",fontObj));
                        cellarr1.setBackgroundColor(Color.WHITE);
                        cellarr1.setHorizontalAlignment(Element.ALIGN_LEFT);
                        tableValutaz.addCell(cellarr1);
                        PdfPCell cellar2 = new PdfPCell(new Phrase("5-20",fontObj));
                        cellar2.setBackgroundColor(Color.WHITE);
                        cellar2.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tableValutaz.addCell(cellar2);
                        PdfPCell cellar3 = new PdfPCell(new Phrase("" + valutazione.getPdlAss(),fontObj));
                        cellar3.setBackgroundColor(Color.WHITE);
                        cellar3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tableValutaz.addCell(cellar3);
                    }
			    try {
					doc.add(new Paragraph("\n"));
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    //
				try {
					doc.add(tableValutaz);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			    // tabella sintesi finale
			    PdfPTable tableSintesi = new PdfPTable(2);
				// define relative columns width p.96
			    try {
					tableSintesi.setWidths(new int[] {4,2});
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    // intestazione tabella valutazione
			    PdfPCell cellash1 = new PdfPCell(new Phrase("TABELLA RIASSUNTIVA",fontTitle3));
		    	cellash1.setBackgroundColor(Color.LIGHT_GRAY);
			    cellash1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    cellash1.setColspan(2);
			    tableSintesi.addCell(cellash1);
			    //
			    // riga Totale performance operativa
			    PdfPCell cellaopr1 = new PdfPCell(new Phrase("Totale performance operativa",fontObj));
			    cellaopr1.setBackgroundColor(Color.WHITE);
			    cellaopr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableSintesi.addCell(cellaopr1);
			    PdfPCell cellaopr2 = new PdfPCell(new Phrase(""+inc.getTotPesoObiettivi(),fontObj));
			    cellaopr2.setBackgroundColor(Color.WHITE);
			    cellaopr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellaopr2);
			    
			    // riga Totale Comportamento Organizzativo
			    PdfPCell cellacor1 = new PdfPCell(new Phrase("Totale Comportamento Organizzativo",fontObj));
			    cellacor1.setBackgroundColor(Color.WHITE);
			    cellacor1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableSintesi.addCell(cellacor1);
			    int totCompOrgAss = valutazione.getAnalProgrAss()+
			    		valutazione.getRelazCoordAss()+
			    		valutazione.getGestRealAss()+
			    		valutazione.getPdlAss();
			    PdfPCell cellacor2 = new PdfPCell(new Phrase(""+totCompOrgAss,fontObj));
			    cellacor2.setBackgroundColor(Color.WHITE);
			    cellacor2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellacor2);
			    
			    // riga Totale Complessivo
			    PdfPCell cellatcr1 = new PdfPCell(new Phrase("Totale Complessivo",fontObj));
			    cellatcr1.setBackgroundColor(Color.WHITE);
			    cellatcr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableSintesi.addCell(cellatcr1);
			    PdfPCell cellatcr2 = new PdfPCell(new Phrase(""+valutazione.getTotPeso(),fontObj));
			    cellatcr2.setBackgroundColor(Color.WHITE);
			    cellatcr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellatcr2);
			    //
			    try {
					doc.add(tableSintesi);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    //
			} // fine if
		} // fine loop valutazioni
		this.addFirme(doc, incaricoApicale.carica,inc.carica,incaricoApicale.getResponsabile(),inc.getResponsabile());
    } // fine metodo generaSchedaObiettivi
    
    // -----------------------------------------------------
    
 // -------------------------------------------------------------------------------------
    private void generaSchedaObiettiviApicali(Document doc, IncaricoGeko inc, int anno){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
    	//
		
    	//
    	PdfPTable tableProgr = new PdfPTable(1);
    	//
    	tableProgr.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	tableProgr.getDefaultCell().setCellEvent(whiteRectangle);
    	PdfPCell cellriga1 = new PdfPCell(new Paragraph("SCHEDA PROGRAMMAZIONE 1: programmazione obiettivi del dirigente generale ai fini della successiva valutazione della performance - Anno "+anno,fontTitle3));
    	tableProgr.addCell(cellriga1);    	
    	
    	//tableStru.setTableEvent(tableBackground);
    	//tableStru.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	//tableStru.getDefaultCell().setCellEvent(whiteRectangle);
    	PdfPCell cellStru = new PdfPCell(new Paragraph("DIPARTIMENTO/UFFICIO: "+inc.denominazioneStruttura));
    	tableProgr.addCell(cellStru);
    	String nomeResponsabile="";
    	if(inc.responsabile != null){
    		nomeResponsabile = inc.responsabile;
    	}
        String strInc = inc.carica+": "+nomeResponsabile;
    	if(inc.interim) strInc += " INTERIM";
    	PdfPCell cellResp = new PdfPCell(new Paragraph(strInc));
    	tableProgr.addCell(cellResp);
    	//PdfPCell competenze = new PdfPCell(new Paragraph(mapItem.getKey().getCompetenze()));
    	//tableStru.addCell(competenze);
    	//
    	
	    // salto riga
	    try {
			doc.add(new Paragraph("\n"));
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		        
	    PdfPCell cellTitlePerfOper = new PdfPCell(new Paragraph("PERFORMANCE OPERATIVA"));
	    cellTitlePerfOper.setHorizontalAlignment(Element.ALIGN_CENTER);
    	tableProgr.addCell(cellTitlePerfOper);
    	try {
			doc.add(tableProgr);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	// genero la scheda 1
    	int nrObj = 0;
    	PdfPTable tableSk1 = new PdfPTable(9);
    	try {
			tableSk1.setWidths(new int[] {5,10,10,50,10,20,20,20,10});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// riga intestazione tabella obiettivi
	    PdfPCell thNrObj = new PdfPCell(new Phrase("Nr."));
	    thNrObj.setBackgroundColor(Color.LIGHT_GRAY);
	    thNrObj.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thNrObj);
	    //
	    PdfPCell thProgramma = new PdfPCell(new Phrase("Programma"));
	    thProgramma.setBackgroundColor(Color.LIGHT_GRAY);
	    thProgramma.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thProgramma);
	    //
	    PdfPCell thMissione = new PdfPCell(new Phrase("Missione"));
	    thMissione.setBackgroundColor(Color.LIGHT_GRAY);
	    thMissione.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thMissione);
	    //
	    PdfPCell thObj = new PdfPCell(new Phrase("Descrizione obiettivo operativo"));
	    thObj.setBackgroundColor(Color.LIGHT_GRAY);
	    thObj.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thObj);
	    //
	    PdfPCell thStr = new PdfPCell(new Phrase("Corr. ob. Strategico"));
	    thStr.setBackgroundColor(Color.LIGHT_GRAY);
	    thStr.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thStr);
	    //
	    PdfPCell thIndic = new PdfPCell(new Phrase("Indicatore previsto"));
	    thIndic.setBackgroundColor(Color.LIGHT_GRAY);
	    thIndic.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thIndic);
	    //
	    PdfPCell thVal = new PdfPCell(new Phrase("Valore Obiettivo"));
	    thVal.setBackgroundColor(Color.LIGHT_GRAY);
	    thVal.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thVal);
	    //
	    PdfPCell thData = new PdfPCell(new Phrase("Data ultima"));
	    thData.setBackgroundColor(Color.LIGHT_GRAY);
	    thData.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thData);
	    //
	    PdfPCell thPeso = new PdfPCell(new Phrase("Peso attrib."));
	    thPeso.setBackgroundColor(Color.LIGHT_GRAY);
	    thPeso.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thPeso);
    	
    	// itero sugli obiettivi
    	for(Obiettivo obj : inc.getObiettivi()){
    		nrObj++;
    		//
		    // row obj
	    	// nr
		    PdfPCell colNrObj = new PdfPCell(new Phrase(""+nrObj));
		    colNrObj.setBackgroundColor(Color.LIGHT_GRAY);
		    colNrObj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(colNrObj);
		    //
		    String strProgramma ="";
		    String strMissione ="";
		    List<AssociazProgramma> lista = associazProgrammaServizi.findByApicale(obj);
		    for (AssociazProgramma ass : lista){
		    	//log.info("buttana ass:"+ass.toString());
		    	strProgramma += ass.getProgramma().getCodice();
		    	strProgramma += " - "+ass.getProgramma().getDescrizione();
		    	strMissione += ass.getProgramma().getMissione().getCodice();
		    	strMissione += " - "+ass.getProgramma().getMissione().getDescrizione();
		    	
		    }
		    PdfPCell tdProgramma = new PdfPCell(new Paragraph(strProgramma));
		    tdProgramma.setBackgroundColor(Color.LIGHT_GRAY);
		    tdProgramma.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(tdProgramma);
		    //
		    PdfPCell tdMissione = new PdfPCell(new Paragraph(strMissione));
		    tdMissione.setBackgroundColor(Color.LIGHT_GRAY);
		    tdMissione.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(tdMissione);
		    // righe descriz e note
		    PdfPCell cellobj;
		    cellobj = new PdfPCell(new Paragraph(obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote()));		
		    cellobj.setBackgroundColor(Color.WHITE);
		    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(cellobj);
		    // strategico
		    String strStrat = "";
		    if(!obj.getAssociazObiettivi().isEmpty()){
			    for (AssociazObiettivi ass : obj.getAssociazObiettivi()){
			    	strStrat += (ass.getStrategico().getCodice() + " ");
			    }		    
		    }
		    PdfPCell cellStrat = new PdfPCell(new Paragraph(strStrat));
		    cellStrat.setBackgroundColor(Color.WHITE);
		    cellStrat.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(cellStrat);
		    // indicatore
		    PdfPCell cellIndObj = new PdfPCell(new Phrase(" - "));
		    if(null != obj.getIndicatore()){
		    cellIndObj = new PdfPCell(new Phrase(obj.getIndicatore()));	
		    }
		    tableSk1.addCell(cellIndObj);
		    // valore obiettivo
		    PdfPCell cellValObj = new PdfPCell(new Phrase(" - "));
		    if(null != obj.getValObiettivo()){
		    	cellValObj = new PdfPCell(new Phrase(obj.getValObiettivo()));
		    }
		    tableSk1.addCell(cellValObj);
		    // data ultima
		    PdfPCell cellDataObj = new PdfPCell(new Phrase(" - "));
		    if(null != obj.getDataUltima()){
			    cellDataObj = new PdfPCell(new Phrase(sdf.format(obj.getDataUltima())));			    
			    tableSk1.addCell(cellDataObj);
		    }
		    // peso
		    PdfPCell cellPeso = new PdfPCell(new Paragraph(""+obj.getPeso()));			
		    cellPeso.setBackgroundColor(Color.WHITE);
		    cellPeso.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableSk1.addCell(cellPeso);
		    //
		    
    	} // fine loop obiettivi
    	try {
			doc.add(tableSk1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// riga Totale performance operativa
    	PdfPTable tabletpo = new PdfPTable(2);
    	try {
			tabletpo.setWidths(new int[] {4,1});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    PdfPCell cellatpo = new PdfPCell(new Phrase("Totale conseguibile performance operativa"));
	    cellatpo.setBackgroundColor(Color.WHITE);
	    cellatpo.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tabletpo.addCell(cellatpo);
	    //
	    PdfPCell cellatpo2 = new PdfPCell(new Phrase(""+inc.getTotPesoObiettivi()));
	    cellatpo2.setBackgroundColor(Color.WHITE);
	    cellatpo2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tabletpo.addCell(cellatpo2);
	    try {
			doc.add(tabletpo);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 // valutazioni
		for (Valutazione valutazione : inc.getValutazioni()){
			
			if (valutazione.getAnno() == anno){
				// tabella attuazione piano di lavoro
				PdfPTable tableValutaz = new PdfPTable(3);
				// define relative columns width p.96
				try {
					tableValutaz.setWidths(new int[] {4,1,1});
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// prima intestazione tabella valutazione
			    PdfPCell cellah1 = new PdfPCell(new Phrase("COMPORTAMENTO ORGANIZZATIVO"));
		    	cellah1.setBackgroundColor(Color.LIGHT_GRAY);
			    cellah1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    cellah1.setColspan(3);
			    tableValutaz.addCell(cellah1);
			    // seconda intestazione tabella valutazione
			    PdfPCell cellah2 = new PdfPCell(new Phrase("QUALITA' GESTIONALI-RELAZIONALI (selezionare 3 su 4)"));
		    	cellah2.setBackgroundColor(Color.LIGHT_GRAY);
			    cellah2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellah2);
			    PdfPCell cellah3 = new PdfPCell(new Phrase("Range assegnabile"));
		    	cellah3.setBackgroundColor(Color.LIGHT_GRAY);
			    cellah3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellah3);
			    PdfPCell cellah4 = new PdfPCell(new Phrase("Peso attribuito"));
		    	cellah4.setBackgroundColor(Color.LIGHT_GRAY);
			    cellah4.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellah4);
				//
			    // riga Gestione
			    PdfPCell cellar1 = new PdfPCell(new Phrase("Capacità di intercettare, gestire risorse e programmare"));
			    cellar1.setBackgroundColor(Color.WHITE);
			    cellar1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellar1);
			    PdfPCell cellagrr2 = new PdfPCell(new Phrase("5-20"));
			    cellagrr2.setBackgroundColor(Color.WHITE);
			    cellagrr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellagrr2);
			    PdfPCell cellagrr3 = new PdfPCell(new Phrase(""+valutazione.getGestRealAss()));
			    cellagrr3.setBackgroundColor(Color.WHITE);
			    cellagrr3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellagrr3);
			    // riga Analisi e Programmazione
			    PdfPCell cellaapr1 = new PdfPCell(new Phrase("Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione"));
			    cellaapr1.setBackgroundColor(Color.WHITE);
			    cellaapr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellaapr1);
			    PdfPCell cellaapr2 = new PdfPCell(new Phrase("5-20"));
			    cellaapr2.setBackgroundColor(Color.WHITE);
			    cellaapr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellaapr2);
			    PdfPCell cellaapr3 = new PdfPCell(new Phrase(""+valutazione.getAnalProgrAss()));
			    cellaapr3.setBackgroundColor(Color.WHITE);
			    cellaapr3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellaapr3);
			    // riga Relazione e Coordinamento
			    PdfPCell cellarcr1 = new PdfPCell(new Phrase("Capacità di valorizzare competenze e attitudini dei propri collaboratori"));
			    cellarcr1.setBackgroundColor(Color.WHITE);
			    cellarcr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellarcr1);
			    PdfPCell cellarcr2 = new PdfPCell(new Phrase("5-20"));
			    cellarcr2.setBackgroundColor(Color.WHITE);
			    cellarcr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellarcr2);
			    PdfPCell cellarcr3 = new PdfPCell(new Phrase(""+valutazione.getRelazCoordAss()));
			    cellarcr3.setBackgroundColor(Color.WHITE);
			    cellarcr3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellarcr3);
			 // riga priorità
			    PdfPCell cellarr1 = new PdfPCell(new Phrase("Capacità di individuazione del livello di priorità degli interventi da realizzare"));
			    cellarr1.setBackgroundColor(Color.WHITE);
			    cellarr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellarr1);
			    PdfPCell cellar2 = new PdfPCell(new Phrase("5-20"));
			    cellar2.setBackgroundColor(Color.WHITE);
			    cellar2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellar2);
			    PdfPCell cellar3 = new PdfPCell(new Phrase(""+valutazione.getPdlAss()));
			    cellar3.setBackgroundColor(Color.WHITE);
			    cellar3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellar3);
			    try {
					doc.add(new Paragraph("\n"));
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    //
				try {
					doc.add(tableValutaz);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			    // tabella sintesi finale
			    PdfPTable tableSintesi = new PdfPTable(2);
				// define relative columns width p.96
			    try {
					tableSintesi.setWidths(new int[] {4,2});
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    // intestazione tabella valutazione
			    PdfPCell cellash1 = new PdfPCell(new Phrase("TABELLA RIASSUNTIVA"));
		    	cellash1.setBackgroundColor(Color.LIGHT_GRAY);
			    cellash1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    cellash1.setColspan(2);
			    tableSintesi.addCell(cellash1);
			    //
			    // riga Totale performance operativa
			    PdfPCell cellaopr1 = new PdfPCell(new Phrase("Totale performance operativa"));
			    cellaopr1.setBackgroundColor(Color.WHITE);
			    cellaopr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableSintesi.addCell(cellaopr1);
			    PdfPCell cellaopr2 = new PdfPCell(new Phrase(""+inc.getTotPesoObiettivi()));
			    cellaopr2.setBackgroundColor(Color.WHITE);
			    cellaopr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellaopr2);
			    
			    // riga Totale Comportamento Organizzativo
			    PdfPCell cellacor1 = new PdfPCell(new Phrase("Totale Comportamento Organizzativo"));
			    cellacor1.setBackgroundColor(Color.WHITE);
			    cellacor1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableSintesi.addCell(cellacor1);
			    int totCompOrgAss = valutazione.getAnalProgrAss()+
			    		valutazione.getRelazCoordAss()+
			    		valutazione.getGestRealAss()+
			    		valutazione.getPdlAss();
			    PdfPCell cellacor2 = new PdfPCell(new Phrase(""+totCompOrgAss));
			    cellacor2.setBackgroundColor(Color.WHITE);
			    cellacor2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellacor2);
			    
			    // riga Totale Complessivo
			    PdfPCell cellatcr1 = new PdfPCell(new Phrase("Totale Complessivo"));
			    cellatcr1.setBackgroundColor(Color.WHITE);
			    cellatcr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableSintesi.addCell(cellatcr1);
			    PdfPCell cellatcr2 = new PdfPCell(new Phrase(""+valutazione.getTotPeso()));
			    cellatcr2.setBackgroundColor(Color.WHITE);
			    cellatcr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellatcr2);
			    
			    //
			    try {
					doc.add(tableSintesi);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    //
			} // fine if
		} // fine loop valutazioni
		this.addFirme(doc, "IL PRESIDENTE / L'ASSESSORE",inc.carica,"",inc.getResponsabile());
    } // fine metodo generaSchedaObiettivi
    
    
    // -------------------------------------------------------------------------------------
	private void addFirme(Document doc, String thValutatore, String thValutato, String valutatore, String valutato) {
    	PdfPTable tableFirma = new PdfPTable(3);
	    try {
			tableFirma.setWidths(new int[] {2,5,5});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // genero le firme
	    // riga intestazione
	    PdfPCell thData = new PdfPCell(new Phrase("Data" ));
	    thData.setBackgroundColor(Color.LIGHT_GRAY);
	    thData.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableFirma.addCell(thData);
	    //
	    PdfPCell cellthValutatore = new PdfPCell(new Phrase(thValutatore));
	    cellthValutatore.setBackgroundColor(Color.LIGHT_GRAY);
	    cellthValutatore.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableFirma.addCell(cellthValutatore);
	    //
	    PdfPCell cellthValutato = new PdfPCell(new Phrase(thValutato));
	    cellthValutato.setBackgroundColor(Color.LIGHT_GRAY);
	    cellthValutato.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableFirma.addCell(cellthValutato);
	    // riga valutatori
	    PdfPCell cellbianca= new PdfPCell(new Phrase(""));
	    cellbianca.setBackgroundColor(Color.WHITE);
	    cellbianca.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableFirma.addCell(cellbianca);
	    //
	    PdfPCell celltdValutatore= new PdfPCell(new Phrase(valutatore));
	    celltdValutatore.setBackgroundColor(Color.WHITE);
	    celltdValutatore.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableFirma.addCell(celltdValutatore);
	    //
	    PdfPCell celltdValutato= new PdfPCell(new Phrase(valutato));
	    celltdValutato.setBackgroundColor(Color.WHITE);
	    celltdValutato.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableFirma.addCell(celltdValutato);
	    // riga _________ 					   
	    PdfPCell cellData = new PdfPCell(new Phrase("__/__/__"));
	    cellData.setBackgroundColor(Color.WHITE);
	    cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cellData.setVerticalAlignment(Element.ALIGN_BOTTOM);
	    cellData.setFixedHeight(60.0f);
	    cellData.setBorderColor(Color.WHITE);
	    tableFirma.addCell(cellData);
	    PdfPCell cellfdirig = new PdfPCell(new Phrase(""+"________________"));
	    cellfdirig.setBackgroundColor(Color.WHITE);
	    cellfdirig.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cellfdirig.setVerticalAlignment(Element.ALIGN_BOTTOM);
	    cellfdirig.setFixedHeight(60.0f);
	    cellfdirig.setBorderColor(Color.WHITE);
	    tableFirma.addCell(cellfdirig);
	    PdfPCell cellfdip= new PdfPCell(new Phrase("________________"));
	    cellfdip.setBackgroundColor(Color.WHITE);
	    cellfdip.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cellfdip.setVerticalAlignment(Element.ALIGN_BOTTOM);
	    cellfdip.setFixedHeight(60.0f);
	    cellfdip.setBorderColor(Color.WHITE);
	    tableFirma.addCell(cellfdip);
	    // 
	    try {
			doc.add(tableFirma);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } // fine metodo addfirme ------------------------------------------------
    
    // ------------------------------------------------------------------------------------
    private void generaSchedeDettaglioObiettivi(Document doc, IncaricoGeko inc, IncaricoGeko incaricoApicale, int anno){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
    	int nordObj = 0;
    	for(Obiettivo obj : inc.getObiettivi()){
    		//
    		doc.newPage();
    		// creo la scheda di programmazione 2 con tabella azioni / prodotti /scadenze
    		PdfPTable tableTitolo = new PdfPTable(1);
    		// righe descriz e note
		    PdfPCell cellsk2 = new PdfPCell(new Paragraph("Scheda programmazione 2: Scheda di dettaglio delle azioni correlate agli obiettivi assegnati (scheda di programmazione 1)"));		
		    cellsk2.setBackgroundColor(Color.WHITE);
		    cellsk2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableTitolo.addCell(cellsk2);
		    try {
				doc.add(tableTitolo);
			} catch (DocumentException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		    // struttura
		    PdfPTable tableUff = new PdfPTable(3);
		    PdfPTable table3righe = new PdfPTable(1);
		    PdfPCell cellStru2 = new PdfPCell(new Paragraph("STRUTTURA: "+inc.denominazioneStruttura));
		    table3righe.addCell(cellStru2);
		    // dirigente
		    String nomeDirig2="";
	    	if(inc.responsabile != null){
	    		nomeDirig2 = inc.responsabile;
	    	}
	    	String strInc = inc.carica+": "+nomeDirig2;
                if(inc.interim) strInc += " INTERIM";
                PdfPCell cellResp2 = new PdfPCell(new Paragraph(strInc));
                table3righe.addCell(cellResp2);
		    //
	    	
	    	//
		    nordObj++;
		    PdfPCell cellNordObj;
		    cellNordObj = new PdfPCell(new Paragraph("Obiettivo operativo nr. "+nordObj ));		
		    cellNordObj.setBackgroundColor(Color.WHITE);
		    cellNordObj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table3righe.addCell(cellNordObj);
	    	//
		    PdfPCell cell3righe = new PdfPCell(table3righe);
		    tableUff.addCell(cell3righe);
		    //
		    PdfPCell cellAnno = new PdfPCell(new Paragraph("Anno di riferimento della valutazione: "+anno));		
		    cellAnno.setBackgroundColor(Color.WHITE);
		    cellAnno.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableUff.addCell(cellAnno);
		    String strStrat="";
		    if(!obj.getAssociazObiettivi().isEmpty()){
			    for (AssociazObiettivi ass : obj.getAssociazObiettivi()){
			    	strStrat += (ass.getStrategico().getDescrizione() + " ");
			    }
		    }    
		    PdfPCell cellStrat = new PdfPCell(new Paragraph("obiettivo strategico di riferimento: "+strStrat));
		    cellStrat.setBackgroundColor(Color.WHITE);
		    cellStrat.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableUff.addCell(cellStrat);
		    try {
				doc.add(tableUff);
			} catch (DocumentException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 					    
	    	// Genero una tabella
		    PdfPTable table = new PdfPTable(6);
		    // define relative columns width p.96
		    try {
				table.setWidths(new int[] {10,38,12,10,12,8});
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}					 
		    //
		    PdfPCell cellobj;
	    	cellobj = new PdfPCell(new Paragraph("Descrizione obiettivo:"+obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote()));		
		    cellobj.setBackgroundColor(Color.WHITE);
		    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellobj.setColspan(6);
		    table.addCell(cellobj);
		    
		    // itero sulle azioni
		    int nrAct =0;
		    int totPesoAct =0;
		    // riga intestazione tabella azioni
		    // act descript row
		    PdfPCell thNr = new PdfPCell(new Phrase("Nr."));
		    thNr.setBackgroundColor(Color.LIGHT_GRAY);
		    thNr.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thNr);
		    //
		    PdfPCell thAct = new PdfPCell(new Phrase("Azioni"));
		    thAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thAct);
		    //
		    PdfPCell thIndicAct = new PdfPCell(new Phrase("Indicatore"));
		    thIndicAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thIndicAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thIndicAct);
		    //
		    PdfPCell thValAct = new PdfPCell(new Phrase("Valore Obiettivo"));
		    thValAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thValAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thValAct);
		    //
		    PdfPCell thDataAct = new PdfPCell(new Phrase("Data ultima"));
		    thDataAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thDataAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thDataAct);
		    //
		    PdfPCell thPesoAct = new PdfPCell(new Phrase("Peso attribuito all'azione"));
		    thPesoAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thPesoAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thPesoAct);
		    
		    // seconda riga intestazione tabella azioni
		    // act descript row
		    PdfPCell thCol = new PdfPCell(new Phrase(""));
		    thCol.setBackgroundColor(Color.LIGHT_GRAY);
		    thCol.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thCol);
		    //
		    PdfPCell thColB = new PdfPCell(new Phrase("Colonna B"));
		    thColB.setBackgroundColor(Color.LIGHT_GRAY);
		    thColB.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColB);
		    //
		    PdfPCell thColC = new PdfPCell(new Phrase("Col. C"));
		    thColC.setBackgroundColor(Color.LIGHT_GRAY);
		    thColC.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColC);
		    //
		    PdfPCell thColD = new PdfPCell(new Phrase("Col. D"));
		    thColD.setBackgroundColor(Color.LIGHT_GRAY);
		    thColD.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColD);
		    //
		    PdfPCell thColE = new PdfPCell(new Phrase("Col. E"));
		    thColE.setBackgroundColor(Color.LIGHT_GRAY);
		    thColE.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColE);
		    //
		    PdfPCell thColF = new PdfPCell(new Phrase("Col. F"));
		    thColF.setBackgroundColor(Color.LIGHT_GRAY);
		    thColF.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColF);
		    
		    if(!obj.getAzioni().isEmpty()){
		    for (Azione act : obj.getAzioni()){
		    	nrAct++;
		    	totPesoAct += act.getPeso();
		    	// nr azione
		    	// act descript row
			    PdfPCell cellNrAct = new PdfPCell(new Phrase(""+ nrAct +")"));
			    cellNrAct.setBackgroundColor(Color.LIGHT_GRAY);
			    cellNrAct.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellNrAct);
		    	// act descript row
			    String strcellActDescr = act.getDenominazione() +" - "+act.getDescrizione() +" - "+ act.getNote();
			    if (act.isTassativa()) strcellActDescr += "AZIONE E SCADENZA TASSATIVE";
			    PdfPCell cellActDescr = new PdfPCell(new Phrase(strcellActDescr));			    
			    cellActDescr.setBackgroundColor(Color.WHITE);
			    cellActDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellActDescr);
		    	PdfPCell cellr1 = new PdfPCell(new Phrase(""+act.getIndicatore()));
			    cellr1.setBackgroundColor(Color.WHITE);
			    cellr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr1);
			    PdfPCell cellr2 = new PdfPCell(new Phrase(act.getProdotti()));
			    cellr2.setBackgroundColor(Color.WHITE);
			    cellr2.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr2);
			    //
			    PdfPCell cellr3 = new PdfPCell(new Phrase(" - "));
			    String strcellr3 = "";
			    if (act.getScadenza() != null)	strcellr3+=	sdf.format(act.getScadenza());
			    if (act.isTassativa()) strcellr3 += " TASSATIVA";
			    cellr3 = new PdfPCell(new Phrase(strcellr3));			    
			    cellr3.setBackgroundColor(Color.WHITE);
			    cellr3.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr3);
			    PdfPCell cellr4 = new PdfPCell(new Phrase(""+act.getPeso()));
			    cellr4.setBackgroundColor(Color.WHITE);
			    cellr4.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr4);
			    
		    } // fine loop azioni
		    } // fine if
		    
		    // totale peso azioni
		    PdfPCell cellVuota = new PdfPCell(new Phrase(""));
		    cellVuota.setBackgroundColor(Color.LIGHT_GRAY);
		    cellVuota.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellVuota.setColspan(2);
		    table.addCell(cellVuota);
		    //
		    PdfPCell cellTotalePesi = new PdfPCell(new Phrase("Totale pesi azioni (=100)"));
		    cellTotalePesi.setBackgroundColor(Color.LIGHT_GRAY);
		    cellTotalePesi.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellTotalePesi.setColspan(3);
		    table.addCell(cellTotalePesi);
		    //
		    PdfPCell cellSommaPesi = new PdfPCell(new Phrase(""+totPesoAct));
		    cellSommaPesi.setBackgroundColor(Color.LIGHT_GRAY);
		    cellSommaPesi.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellSommaPesi.setColspan(3);
		    table.addCell(cellSommaPesi);					    
		    try {
				doc.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    this.addFirme(doc, incaricoApicale.carica,inc.carica,incaricoApicale.getResponsabile(),inc.getResponsabile());
    	} // fine for obiettivi    	
    }
    
    
    // ---------------------------------
    // ------------------------------------------------------------------------------------
    private void generaSchedeDettaglioObiettiviApicali(Document doc, IncaricoGeko inc, int anno){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
    	int nordObj = 0;
    	for(Obiettivo obj : inc.getObiettivi()){
    		//
    		doc.newPage();
    		// creo la scheda di programmazione 2 con tabella azioni / prodotti /scadenze
    		PdfPTable tableTitolo = new PdfPTable(1);
    		// righe descriz e note
		    PdfPCell cellsk2 = new PdfPCell(new Paragraph("Scheda programmazione 2: Scheda di dettaglio delle azioni correlate agli obiettivi assegnati (scheda di programmazione 1)"));		
		    cellsk2.setBackgroundColor(Color.WHITE);
		    cellsk2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableTitolo.addCell(cellsk2);
		    try {
				doc.add(tableTitolo);
			} catch (DocumentException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		    // struttura
		    PdfPTable tableUff = new PdfPTable(3);
		    PdfPTable table3righe = new PdfPTable(1);
		    PdfPCell cellStru2 = new PdfPCell(new Paragraph("Dipartimento/Ufficio: "+inc.denominazioneStruttura));
		    table3righe.addCell(cellStru2);
		    // dirigente
		    String nomeDirig2="";
	    	if(inc.responsabile != null){
	    		nomeDirig2 = inc.responsabile;
	    	}
	    	PdfPCell cellResp2 = new PdfPCell(new Paragraph("DIRIGENTE GENERALE: "+nomeDirig2));
	    	table3righe.addCell(cellResp2);
		    //
	    	
	    	//
		    nordObj++;
		    PdfPCell cellNordObj;
		    cellNordObj = new PdfPCell(new Paragraph("Obiettivo operativo nr. "+nordObj ));		
		    cellNordObj.setBackgroundColor(Color.WHITE);
		    cellNordObj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table3righe.addCell(cellNordObj);
	    	//
		    PdfPCell cell3righe = new PdfPCell(table3righe);
		    tableUff.addCell(cell3righe);
		    //
		    PdfPCell cellAnno = new PdfPCell(new Paragraph("Anno di riferimento della valutazione: "+anno));		
		    cellAnno.setBackgroundColor(Color.WHITE);
		    cellAnno.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableUff.addCell(cellAnno);
		    String strStrat="";
		    if(!obj.getAssociazObiettivi().isEmpty()){
			    for (AssociazObiettivi ass : obj.getAssociazObiettivi()){
			    	strStrat += (ass.getStrategico().getDescrizione() + " ");
			    }
		    }    
		    PdfPCell cellStrat = new PdfPCell(new Paragraph("obiettivo strategico di riferimento: "+strStrat));
		    cellStrat.setBackgroundColor(Color.WHITE);
		    cellStrat.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableUff.addCell(cellStrat);
		    try {
				doc.add(tableUff);
			} catch (DocumentException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 					    
	    	// Genero una tabella
		    PdfPTable table = new PdfPTable(6);
		    // define relative columns width p.96
		    try {
				table.setWidths(new int[] {1,4,1,1,1,1});
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}					 
		    //
		    PdfPCell cellobj;
	    	cellobj = new PdfPCell(new Paragraph("Descrizione obiettivo:"+obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote()));		
		    cellobj.setBackgroundColor(Color.WHITE);
		    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellobj.setColspan(6);
		    table.addCell(cellobj);
		    
		    // itero sulle azioni
		    int nrAct =0;
		    int totPesoAct =0;
		    // riga intestazione tabella azioni
		    // act descript row
		    PdfPCell thNr = new PdfPCell(new Phrase("Nr."));
		    thNr.setBackgroundColor(Color.LIGHT_GRAY);
		    thNr.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thNr);
		    //
		    PdfPCell thAct = new PdfPCell(new Phrase("Azioni"));
		    thAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thAct);
		    //
		    PdfPCell thIndicAct = new PdfPCell(new Phrase("Indicatore"));
		    thIndicAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thIndicAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thIndicAct);
		    //
		    PdfPCell thValAct = new PdfPCell(new Phrase("Valore Obiettivo"));
		    thValAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thValAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thValAct);
		    //
		    PdfPCell thDataAct = new PdfPCell(new Phrase("Data ultima"));
		    thDataAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thDataAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thDataAct);
		    //
		    PdfPCell thPesoAct = new PdfPCell(new Phrase("Peso attribuito all'azione"));
		    thPesoAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thPesoAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thPesoAct);
		    
		    // seconda riga intestazione tabella azioni
		    // act descript row
		    PdfPCell thCol = new PdfPCell(new Phrase(""));
		    thCol.setBackgroundColor(Color.LIGHT_GRAY);
		    thCol.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thCol);
		    //
		    PdfPCell thColB = new PdfPCell(new Phrase("Colonna B"));
		    thColB.setBackgroundColor(Color.LIGHT_GRAY);
		    thColB.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColB);
		    //
		    PdfPCell thColC = new PdfPCell(new Phrase("Colonna C"));
		    thColC.setBackgroundColor(Color.LIGHT_GRAY);
		    thColC.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColC);
		    //
		    PdfPCell thColD = new PdfPCell(new Phrase("Colonna D"));
		    thColD.setBackgroundColor(Color.LIGHT_GRAY);
		    thColD.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColD);
		    //
		    PdfPCell thColE = new PdfPCell(new Phrase("Colonna E"));
		    thColE.setBackgroundColor(Color.LIGHT_GRAY);
		    thColE.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColE);
		    //
		    PdfPCell thColF = new PdfPCell(new Phrase("Colonna F"));
		    thColF.setBackgroundColor(Color.LIGHT_GRAY);
		    thColF.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColF);
		    
		    if(!obj.getAzioni().isEmpty()){
		    for (Azione act : obj.getAzioni()){
		    	nrAct++;
		    	totPesoAct += act.getPesoApicale();
		    	// nr azione
		    	// act descript row
			    PdfPCell cellNrAct = new PdfPCell(new Phrase(""+ nrAct +")"));
			    cellNrAct.setBackgroundColor(Color.LIGHT_GRAY);
			    cellNrAct.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellNrAct);
		    	// act descript row
			    String strcellActDescr = act.getDenominazione() +" - "+act.getDescrizione() +" - "+ act.getNote();
			    if (act.isTassativa()) strcellActDescr += "AZIONE E SCADENZA TASSATIVE";
			    PdfPCell cellActDescr = new PdfPCell(new Phrase(strcellActDescr));			    
			    cellActDescr.setBackgroundColor(Color.WHITE);
			    cellActDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellActDescr);
		    	PdfPCell cellr1 = new PdfPCell(new Phrase(""+act.getIndicatore()));
			    cellr1.setBackgroundColor(Color.WHITE);
			    cellr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr1);
			    PdfPCell cellr2 = new PdfPCell(new Phrase(act.getProdotti()));
			    cellr2.setBackgroundColor(Color.WHITE);
			    cellr2.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr2);
			    //
			    PdfPCell cellr3 = new PdfPCell(new Phrase(" - "));
			    String strcellr3 = "";
			    if (act.getScadenza() != null)	strcellr3+=	sdf.format(act.getScadenza());
			    if (act.isTassativa()) strcellr3 += " TASSATIVA";
			    cellr3 = new PdfPCell(new Phrase(strcellr3));			    
			    cellr3.setBackgroundColor(Color.WHITE);
			    cellr3.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr3);
			    PdfPCell cellr4 = new PdfPCell(new Phrase(""+act.getPesoApicale()));
			    cellr4.setBackgroundColor(Color.WHITE);
			    cellr4.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr4);
			    
		    } // fine loop azioni
		    } // fine if
		    
		    // totale peso azioni
		    PdfPCell cellVuota = new PdfPCell(new Phrase(""));
		    cellVuota.setBackgroundColor(Color.LIGHT_GRAY);
		    cellVuota.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellVuota.setColspan(2);
		    table.addCell(cellVuota);
		    //
		    PdfPCell cellTotalePesi = new PdfPCell(new Phrase("Totale pesi azioni (=100)"));
		    cellTotalePesi.setBackgroundColor(Color.LIGHT_GRAY);
		    cellTotalePesi.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellTotalePesi.setColspan(3);
		    table.addCell(cellTotalePesi);
		    //
		    PdfPCell cellSommaPesi = new PdfPCell(new Phrase(""+totPesoAct));
		    cellSommaPesi.setBackgroundColor(Color.LIGHT_GRAY);
		    cellSommaPesi.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellSommaPesi.setColspan(3);
		    table.addCell(cellSommaPesi);					    
		    try {
				doc.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    this.addFirme(doc, "IL PRESIDENTE / L'ASSESSORE","IL DIRIGENTE GENERALE","",inc.getResponsabile());
    	} // fine for obiettivi
    	
    } // fine metodo generaSchedeDettaglioObiettiviApicali
    
    
  //----------------------- Valutazione --------------------
    // 2. lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfProgrammazioneCompartoController/{anno}/{idIncarico}")
    public void pdfProgrammazioneCompartoManager(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// cfr iText in Action p.285 e 289
    	try {
    		String text = request.getParameter("text");
    		if (text == null || text.trim().length() == 0 ) {
    			text = "You didn't enter any text. ";
    		}
    	//Document doc = new Document(PageSize.A4.rotate());
    	Document doc = new Document(PageSize.A3);
	    // L'oggetto baosPDF conterr� i caratteri che costituiscono il file PDF crea il pdf in memoria
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;
	    // Crea l'associazione tra l'oggetto di tipo ByteArrayOutputStream che rappresenta il PDF e il documento
	    // Ritorna un oggetto di tipo PdfWriter
	    docWriter = PdfWriter.getInstance(doc, baos);
		// Apro il documento
		doc.open();
                this.setFonts();
		//
		/*
		 * genero il contenuto del documento  
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		// estraggo le collections che mi servono   
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
        
		List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(incarico.pgID, anno);
		listDipendenti.remove(manager);
		//
		Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap();
		Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap();
		//
		if (listDipendenti!= null && !listDipendenti.isEmpty()){
		for(PersonaFisicaGeko pf : listDipendenti){
			
			pf.setIncaricoValutazioneID(idIncarico);
			//
			List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, idIncarico, anno);
			if (!assegnazioni.isEmpty()) {
				pf.setAssegnazioni(assegnazioni); // altrimenti non totalizza i pesi
				mapDipendentiAssegnazioni.put(pf, assegnazioni);
			}
			 //
			//
        	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
        	if (valutazioni!=null && !valutazioni.isEmpty()) {
        		pf.setValutazioni(valutazioni);
            	mapDipendentiValutazioneComparto.put(pf, valutazioni);
        	}
        	else {
        		List<ValutazioneComparto> emptyList = new ArrayList<ValutazioneComparto>();
        		pf.setValutazioni(emptyList);
            	mapDipendentiValutazioneComparto.put(pf, emptyList);
        	}
        	//System.out.println("listDipendentiAssegnazioniProgrammazioneGet() pf= "+pf.stringa);
		 	
		}
		}
		// scrivo sulla prima pagina il titolo del report con il nome della struttura
		Paragraph titolo = new Paragraph("Programmazione Personale Comparto per "+struttura.getDenominazione()+" - anno:"+anno);
		doc.add(titolo);
		// Riga vuota
	    doc.add(new Paragraph("\n"));
		Paragraph titolo2 = new Paragraph("Dirigente Responsabile "+manager.stringa);
		doc.add(titolo2);
    	// itero sulle assegnazioni
		// per ogni dipendente
		for (Map.Entry<PersonaFisicaGeko, List<AzioneAssegnazione>> mapItem : mapDipendentiAssegnazioni.entrySet()) {
			// salto-pagina
			doc.newPage();
			
			// stampo il nome del dipendente
			Paragraph val = new Paragraph("Dipendente " + mapItem.getKey().getStringa());
			val.setAlignment(Element.ALIGN_CENTER);
			doc.add(val);
			doc.add(new Paragraph("\n"));
			
			// genero la tabella valutazione dei risultati ----------------------------------------------
			PdfPTable tableValResult = new PdfPTable(3);
			// define relative columns width p.96
			tableValResult.setWidths(new int[] {6,4,2});
			// first header row
			PdfPCell cell = new PdfPCell(new Phrase("Programmazione anno: "+anno));
		    cell.setColspan(3);
		    cell.setBackgroundColor(Color.CYAN);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cell);
		    // second header row
 			PdfPCell cell2 = new PdfPCell(new Phrase("Performance operativa"));
 		    cell2.setColspan(3);
 		    cell2.setBackgroundColor(Color.MAGENTA);
 		    cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
 		    tableValResult.addCell(cell2);
			// intestazione tabella
		    PdfPCell cellh1 = new PdfPCell(new Phrase("Obiettivo"));
	    	cellh1.setBackgroundColor(Color.LIGHT_GRAY);
		    cellh1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cellh1);
		    PdfPCell cellh2 = new PdfPCell(new Phrase("Azione"));
	    	cellh2.setBackgroundColor(Color.LIGHT_GRAY);
		    cellh2.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cellh2);
		    
		    PdfPCell cellh4 = new PdfPCell(new Phrase("Peso"));
	    	cellh4.setBackgroundColor(Color.LIGHT_GRAY);
		    cellh4.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellh4);
		    
		    //
		    // itero sulle assegnazioni
		    for (AzioneAssegnazione assegn : mapItem.getValue()){
		    	PdfPCell cellr1 = new PdfPCell(new Phrase(assegn.getAzione().getObiettivo().getDescrizione()));
		    	cellr1.setBackgroundColor(Color.WHITE);
			    cellr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValResult.addCell(cellr1);
			    PdfPCell cellr2 = new PdfPCell(new Phrase(assegn.getAzione().getDescrizione()+" (scadenza azione: "+
			    		sdf.format(assegn.getAzione().getScadenza())
			    +")"));
		    	cellr2.setBackgroundColor(Color.WHITE);
			    cellr2.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValResult.addCell(cellr2);
			    
			    PdfPCell cellr4 = new PdfPCell(new Phrase(""+assegn.getPeso()));
		    	cellr4.setBackgroundColor(Color.WHITE);
			    cellr4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(cellr4);
			    
		    }  // fine loop assegnazioni
		    // riga totale risultati
		    PdfPCell cellf1 = new PdfPCell(new Phrase("Totale pesi"));
	    	cellf1.setBackgroundColor(Color.LIGHT_GRAY);
		    cellf1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cellf1);
		    PdfPCell cellf2 = new PdfPCell(new Phrase(""));
	    	cellf2.setBackgroundColor(Color.LIGHT_GRAY);
		    cellf2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellf2);
		    
		    PdfPCell cellf4 = new PdfPCell(new Phrase(""+mapItem.getKey().getPesoAssegnazioni()));
	    	cellf4.setBackgroundColor(Color.LIGHT_GRAY);
		    cellf4.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellf4);
		    // 
		    
		    // itero sulle valutazioni
		    for (Map.Entry<PersonaFisicaGeko, List<ValutazioneComparto>> mapItem2 : mapDipendentiValutazioneComparto.entrySet()) {
		    	if (mapItem2.getKey() == mapItem.getKey()){
		 // second header row
 			PdfPCell cell23 = new PdfPCell(new Phrase("Comportamenti organizzativi"));
 		    cell23.setColspan(3);
 		    cell23.setBackgroundColor(Color.MAGENTA);
 		    cell23.setHorizontalAlignment(Element.ALIGN_LEFT);
 		    tableValResult.addCell(cell23);
 		    // intestazione tabella comportamenti organizzativi
		    PdfPCell cell2h1 = new PdfPCell(new Phrase("Indicatore"));
		    cell2h1.setColspan(2);
	    	cell2h1.setBackgroundColor(Color.LIGHT_GRAY);
		    cell2h1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cell2h1);   
		    //
		    PdfPCell cell2h4 = new PdfPCell(new Phrase("Peso"));
	    	cell2h4.setBackgroundColor(Color.LIGHT_GRAY);
		    cell2h4.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cell2h4);
		    
		    // 5 righe o anche meno?
		    for (ValutazioneComparto valutazioneComparto : mapItem2.getValue()){
			    PdfPCell cell21r1 = new PdfPCell(new Phrase("Competenza nello svolgimento delle attivita'"));
			    cell21r1.setColspan(2);
		    	cell21r1.setBackgroundColor(Color.WHITE);
			    cell21r1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValResult.addCell(cell21r1);   
			    //
			    PdfPCell cell21r4 = new PdfPCell(new Phrase(""+valutazioneComparto.getCompetSvolgAttivAss()));
		    	cell21r4.setBackgroundColor(Color.WHITE);
			    cell21r4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(cell21r4);
			    //
			    PdfPCell cell22r1 = new PdfPCell(new Phrase("Capacita' di adattamento al contesto lavorativo"));
			    cell22r1.setColspan(2);
		    	cell22r1.setBackgroundColor(Color.WHITE);
			    cell22r1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValResult.addCell(cell22r1);   
			    //
			    PdfPCell cell22r4 = new PdfPCell(new Phrase(""+valutazioneComparto.getAdattContextLavAss()));
		    	cell22r4.setBackgroundColor(Color.WHITE);
			    cell22r4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(cell22r4);
			    //
			    PdfPCell cell23r1 = new PdfPCell(new Phrase("Capacita' di assolvere ai compiti assegnati"));
			    cell23r1.setColspan(2);
		    	cell23r1.setBackgroundColor(Color.WHITE);
			    cell23r1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValResult.addCell(cell23r1);   
			    //
			    PdfPCell cell23r4 = new PdfPCell(new Phrase(""+valutazioneComparto.getCapacAssolvCompitiAss()));
		    	cell23r4.setBackgroundColor(Color.WHITE);
			    cell23r4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(cell23r4);
			    //
			    if(!mapItem2.getKey().isCatAB()){
				    PdfPCell cell24r1 = new PdfPCell(new Phrase("Capacita' di promuovere e gestire l'innovazione"));
				    cell24r1.setColspan(2);
			    	cell24r1.setBackgroundColor(Color.WHITE);
				    cell24r1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    tableValResult.addCell(cell24r1);   
				    //
				    PdfPCell cell24r4 = new PdfPCell(new Phrase(""+valutazioneComparto.getInnovazAss()));
			    	cell24r4.setBackgroundColor(Color.WHITE);
				    cell24r4.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableValResult.addCell(cell24r4);
				    //
				    PdfPCell cell25r1 = new PdfPCell(new Phrase("Capacita' di organizzazione del lavoro"));
				    cell25r1.setColspan(2);
			    	cell25r1.setBackgroundColor(Color.WHITE);
				    cell25r1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    tableValResult.addCell(cell25r1);   
				    //
				    PdfPCell cell25r4 = new PdfPCell(new Phrase(""+valutazioneComparto.getOrgLavAss()));
			    	cell25r4.setBackgroundColor(Color.WHITE);
				    cell25r4.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableValResult.addCell(cell25r4);
			    }
			    // totale organizzativi 
			    PdfPCell celltorgr1 = new PdfPCell(new Phrase("Totale Comportamenti Organizzativi"));
			    celltorgr1.setColspan(2);
			    celltorgr1.setBackgroundColor(Color.LIGHT_GRAY);
			    celltorgr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValResult.addCell(celltorgr1);   
			    //
			    PdfPCell celltorgr4 = new PdfPCell(new Phrase(""+valutazioneComparto.getTotPeso()));
			    celltorgr4.setBackgroundColor(Color.LIGHT_GRAY);
			    celltorgr4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(celltorgr4);
			    //
			 // totale generale 
			    PdfPCell celltgen1 = new PdfPCell(new Phrase("Totale Generale"));
			    celltgen1.setColspan(2);
			    celltgen1.setBackgroundColor(Color.LIGHT_GRAY);
			    celltgen1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValResult.addCell(celltgen1);   
			    //
			    PdfPCell celltgen4 = new PdfPCell(new Phrase(""+(mapItem.getKey().getPesoAssegnazioni()+valutazioneComparto.getTotPeso())));
			    celltgen4.setBackgroundColor(Color.LIGHT_GRAY);
			    celltgen4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(celltgen4);
			    //
		    
		    }
		    }
		    }
		    
		 // genero le firme
		    // riga firma
		    PdfPCell cellfdirig = new PdfPCell(new Phrase("  Data    Dirigente Responsabile "
		    +manager.getNome()+" "
		    +manager.getCognome()
		    ));
		    cellfdirig.setColspan(1);
		    cellfdirig.setBackgroundColor(Color.LIGHT_GRAY);
		    cellfdirig.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cellfdirig);
		    PdfPCell cellfdip= new PdfPCell(new Phrase("Dipendente "
		    		+mapItem.getKey().getNome()+" "
		    		+mapItem.getKey().getCognome()
		    		));
		    cellfdip.setColspan(3);
		    cellfdip.setBackgroundColor(Color.LIGHT_GRAY);
		    cellfdip.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cellfdip);
		    // riga _________ 
		    PdfPCell cell_fdirig = new PdfPCell(new Phrase("                                                     "
		    +"                                                            "
		    +"__/__/__  __________________________________ "));
		    cell_fdirig.setColspan(1);
		    cell_fdirig.setBackgroundColor(Color.WHITE);
		    cell_fdirig.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cell_fdirig);
		    PdfPCell cell_fdip= new PdfPCell(new Phrase("                                          " +
		    		"__________________________________ "	));
		    cell_fdip.setColspan(3);
		    cell_fdip.setBackgroundColor(Color.WHITE);
		    cell_fdip.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cell_fdip);
		    // 
		    doc.add(tableValResult);
		    doc.add(new Paragraph("\n"));
		    
	
		
		}

	    
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
  
    
} // --- fine classe ControllerProgrPdfController.java ------------------------------------------
