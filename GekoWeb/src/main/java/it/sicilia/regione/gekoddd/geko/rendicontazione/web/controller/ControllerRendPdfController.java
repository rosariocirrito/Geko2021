package it.sicilia.regione.gekoddd.geko.rendicontazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAction;
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
@RequestMapping("/controllerRendPdf")
public class ControllerRendPdfController  {
	//
    @Autowired
    private ObiettivoQryService objServizi;
    @Autowired
    private AssociazProgrammaQryService associazProgrammaServizi;
    
    
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
    public ControllerRendPdfController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
 
	
    
  //-----------------------Rendicontazione Dipartimentale --------------------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfRendicontazioneCompletaIncaricoController/{anno}/{idIncarico}")
    public void pdfRendicontazioneDipartimentoControllerManager(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, 
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// estraggo le collections che mi servono 
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviDiretti = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettiviDiretti);
    	listIncarichi.add(incarico);
        //
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
        	inc.setObiettivi(lstObjs);
        }
        listIncarichi.addAll(listIncarichiDept);
        
		
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
    	Document doc = new Document(PageSize.A3);
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
		
		
		// imposto i font
		Font fontTitle1 = new Font();
		fontTitle1.setFamily("HELVETICA");
		fontTitle1.setStyle("BOLD");
		fontTitle1.setColor(Color.BLUE);
		fontTitle1.setSize(24.0f);
		//
		Font fontTitle2 = new Font();
		fontTitle2.setFamily("HELVETICA");
		fontTitle2.setStyle("BOLD");
		fontTitle2.setColor(Color.BLUE);
		fontTitle2.setSize(20.0f);
		//
		//
		Font fontTitle3 = new Font();
		fontTitle3.setFamily("HELVETICA");
		fontTitle3.setStyle("BOLD");
		fontTitle3.setColor(Color.BLUE);
		fontTitle3.setSize(16.0f);
		
		Font fontObj = new Font();
		fontObj.setFamily("HELVETICA");
		//fontObj.setStyle("ITALICS");
		fontObj.setColor(Color.WHITE);
		fontObj.setSize(12.0f);
        
        // scrivo sulla prima pagina il titolo del report
		Paragraph titolo = new Paragraph("RENDICONTAZIONE DIPARTIMENTALE",fontTitle1);
		titolo.setAlignment("CENTER");
		
     	doc.add(titolo);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	    // scrivo sulla prima pagina il sottotitolo del report
	 	Paragraph subTitolo = new Paragraph(incarico.denominazioneStruttura+" - anno "+anno,fontTitle2);
	 	subTitolo.setAlignment("CENTER");
	    doc.add(subTitolo);
	    // Riga vuota
	 	//doc.add(new Paragraph("\n"));
	    
	   
	    // titolo 2 responsabile: P:M.
	    Paragraph titolo2;
	    if (null != incarico.responsabile) {
		    titolo2 = new Paragraph("RESPONSABILE "+incarico.responsabile);
	    } else{
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale");
	    }
	    titolo2.setAlignment("CENTER");
     	doc.add(titolo2);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	   
	    
		// verifico se mappa vuota
	    if (listIncarichiDept.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Incarico trovato per "+incarico.denominazioneStruttura+" - anno:"+anno);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
    	
	    // itero sulla lista
	    for (IncaricoGeko inc : listIncarichiDept) {
			// salto-pagina
			doc.newPage();

	    	//
	    	PdfPTable tableStru = new PdfPTable(1);
	    	//tableStru.setTableEvent(tableBackground);
	    	tableStru.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	    	tableStru.getDefaultCell().setCellEvent(whiteRectangle);
	    	PdfPCell cellStru = new PdfPCell(new Paragraph("Struttura "+inc.denominazioneStruttura,fontTitle3));
	    	tableStru.addCell(cellStru);
	    	String nomeResponsabile="";
	    	if(inc.responsabile != null){
	    		nomeResponsabile = inc.responsabile;
	    	}
	    	PdfPCell cellResp = new PdfPCell(new Paragraph("Responsabile "+nomeResponsabile+" con incarico: "+inc.getStringa()));
	    	tableStru.addCell(cellResp);
	    	//PdfPCell competenze = new PdfPCell(new Paragraph(mapItem.getKey().getCompetenze()));
	    	//tableStru.addCell(competenze);
	    	//
		    doc.add(tableStru);
		    doc.add(new Paragraph("\n"));
	    	// itero sugli obiettivi
	    	for(Obiettivo obj : inc.getObiettivi()){
	    		String apicale ="";
		    	if (obj.isApicale()) apicale = "APICALE - ";
		    	// creo tabella azioni / prodotti /scadenze
		    	// Genero una tabella
			    PdfPTable table = new PdfPTable(7);
			    
			    
			    // define relative columns width p.96
			    table.setWidths(new int[] {3,6,3,2,6,6,6});
			    //
			    PdfPCell cellobj;
			    if (obj.getTipo().ordinal() == 0) cellobj = new PdfPCell(new Paragraph(apicale+ " - "+obj.getCodice()+ " - "+obj.getDescrizione() +
			    		 obj.getNote()+ " peso: "+obj.getPeso(),fontObj));
			    else cellobj = new PdfPCell(new Paragraph(obj.getCodice()+ " - "+obj.getDescrizione(),fontObj));
			    cellobj.setColspan(7);
			    cellobj.setBackgroundColor(Color.GRAY);
			    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellobj);
			 // itero sulle azioni
			    for (Azione act : obj.getAzioni()){
			    	// act descript row
				    PdfPCell cellActDescr = new PdfPCell(new Phrase("Azione: "+ act.getDenominazione() +" - "+act.getDescrizione() +" "+ act.getNote()));
				    cellActDescr.setBackgroundColor(Color.LIGHT_GRAY);
				    cellActDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cellActDescr.setColspan(7);
				    table.addCell(cellActDescr);
				    // first header row
				    PdfPCell cellh1 = new PdfPCell(new Phrase("Indicatore"));
				    cellh1.setBackgroundColor(Color.CYAN);
				    cellh1.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellh1);
				    PdfPCell cellh2 = new PdfPCell(new Phrase("Valore Obiettivo"));
				    cellh2.setBackgroundColor(Color.CYAN);
				    cellh2.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellh2);
				    PdfPCell cellh3 = new PdfPCell(new Phrase("Scadenza"));
				    cellh3.setBackgroundColor(Color.CYAN);
				    cellh3.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellh3);
				    PdfPCell cellh4 = new PdfPCell(new Phrase("Peso"));
				    cellh4.setBackgroundColor(Color.CYAN);
				    cellh4.setHorizontalAlignment(Element.ALIGN_CENTER);
				    PdfPCell cellh5 = new PdfPCell(new Phrase("Risultato"));
				    cellh5.setBackgroundColor(Color.CYAN);
				    if (obj.getTipo().ordinal() == 0) table.addCell(cellh4);
				    else cellh5.setColspan(2);
				    table.addCell(cellh5);
				    PdfPCell cellh6 = new PdfPCell(new Phrase("Documenti"));
			    	cellh6.setBackgroundColor(Color.CYAN);
			    	table.addCell(cellh6);
			    	PdfPCell cellh7 = new PdfPCell(new Phrase("Indicazioni"));
			    	cellh7.setBackgroundColor(Color.CYAN);
			    	table.addCell(cellh7);
			    
			    	PdfPCell cellr1 = new PdfPCell(new Phrase(""+act.getIndicatore()));
				    cellr1.setBackgroundColor(Color.WHITE);
				    cellr1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr1);
				    PdfPCell cellr2 = new PdfPCell(new Phrase(act.getProdotti()));
				    cellr2.setBackgroundColor(Color.WHITE);
				    cellr2.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr2);
				    PdfPCell cellr3 = new PdfPCell(new Phrase(" - "));
				    if(null != act.getScadenza()){
				    	cellr3 = new PdfPCell(new Phrase(sdf.format(act.getScadenza())));
				    }
				    cellr3.setBackgroundColor(Color.WHITE);
				    cellr3.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr3);
				    PdfPCell cellr4 = new PdfPCell(new Phrase(""+act.getPeso()));
				    cellr4.setBackgroundColor(Color.WHITE);
				    cellr4.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr4);
				    PdfPCell cellr5 = new PdfPCell(new Phrase(""+act.getRisultato()));
				    cellr5.setBackgroundColor(Color.WHITE);
				    cellr5.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr5);
				    String docs = "";
			    	for (Documento docum : act.getDocumenti()){
			    		docs += docum.getDescrizione() + " \n";
			    		}
			    	PdfPCell cellr6 = new PdfPCell(new Phrase(docs));
			    	cellr6.setBackgroundColor(Color.WHITE);
				    cellr6.setHorizontalAlignment(Element.ALIGN_LEFT);
			    	table.addCell(cellr6);
			    	//if (obj.getTipo().ordinal() == 0) table.addCell(cellr4); 
				    //else cellr5.setColspan(2);
			    	//table.addCell(cellr5);
			    	String indics = "";
			    	for (Criticita crit : act.getCriticita()){
			    		indics += crit.getIndicazioni() + " \n";
			    	}
			    	PdfPCell cellr7 = new PdfPCell(new Phrase(indics));
			    	cellr7.setBackgroundColor(Color.WHITE);
				    cellr7.setHorizontalAlignment(Element.ALIGN_LEFT);
			    	table.addCell(cellr7);
			    }
			    doc.add(table);

			    
			    doc.add(new Paragraph("\n"));
	    	}
	    	
	    	//doc.add(new Paragraph("\n"));
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
    	
    	
    	
    } // fine metodo ---------------------------------------------------------------------------------
 
    
  //-----------------------Pianificazione Dipartimentale --------------------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfRendicontazioneDipartimentalePrioritariaController/{anno}/{idIncarico}")
    public void pdfRendicontazionePrioritariaDipartimentoControllerManager(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico, 
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// estraggo le collections che mi servono 
    	List<IncaricoGeko> listIncarichi = new ArrayList<IncaricoGeko>();
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviDiretti = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incarico.setObiettivi(listObiettiviDiretti);
    	listIncarichi.add(incarico);
        //
        List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
        for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = objServizi.findObiettiviTotaliByIncaricoIDAndAnno(inc.idIncarico, anno);
        	List<Obiettivo> lstPrios = new ArrayList<Obiettivo>();
        	for(Obiettivo obj :lstObjs){
        		if (obj.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) lstPrios.add(obj);
        	}
        	inc.setObiettivi(lstPrios);
        }
        listIncarichi.addAll(listIncarichiDept); 
        
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
    	Document doc = new Document(PageSize.A3);
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
		
		
		// imposto i font
		Font fontTitle1 = new Font();
		fontTitle1.setFamily("HELVETICA");
		fontTitle1.setStyle("BOLD");
		fontTitle1.setColor(Color.BLUE);
		fontTitle1.setSize(24.0f);
		//
		Font fontTitle2 = new Font();
		fontTitle2.setFamily("HELVETICA");
		fontTitle2.setStyle("BOLD");
		fontTitle2.setColor(Color.BLUE);
		fontTitle2.setSize(20.0f);
		//
		//
		Font fontTitle3 = new Font();
		fontTitle3.setFamily("HELVETICA");
		fontTitle3.setStyle("BOLD");
		fontTitle3.setColor(Color.BLUE);
		fontTitle3.setSize(16.0f);
		
		Font fontObj = new Font();
		fontObj.setFamily("HELVETICA");
		//fontObj.setStyle("ITALICS");
		fontObj.setColor(Color.WHITE);
		fontObj.setSize(12.0f);
        
        // scrivo sulla prima pagina il titolo del report
		Paragraph titolo = new Paragraph("RENDICONTAZIONE DIPARTIMENTALE",fontTitle1);
		titolo.setAlignment("CENTER");
		
     	doc.add(titolo);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	    // scrivo sulla prima pagina il sottotitolo del report
	 	Paragraph subTitolo = new Paragraph(incarico.denominazioneStruttura+" - anno "+anno,fontTitle2);
	 	subTitolo.setAlignment("CENTER");
	    doc.add(subTitolo);
	    // Riga vuota
	 	//doc.add(new Paragraph("\n"));
	    
	   
	    // titolo 2 responsabile: P:M.
	    Paragraph titolo2;
	    if (null != incarico.responsabile) {
		    titolo2 = new Paragraph("RESPONSABILE "+incarico.responsabile);
	    } else{
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale");
	    }
	    titolo2.setAlignment("CENTER");
     	doc.add(titolo2);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	   
	    
		// verifico se mappa vuota
	    if (listIncarichiDept.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Incarico trovato per "+incarico.denominazioneStruttura+" - anno:"+anno);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
    	
	    // itero sulla lista
	    for (IncaricoGeko inc : listIncarichiDept) {
			// salto-pagina
			doc.newPage();

	    	//
	    	PdfPTable tableStru = new PdfPTable(1);
	    	//tableStru.setTableEvent(tableBackground);
	    	tableStru.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	    	tableStru.getDefaultCell().setCellEvent(whiteRectangle);
	    	PdfPCell cellStru = new PdfPCell(new Paragraph("Struttura "+inc.denominazioneStruttura,fontTitle3));
	    	tableStru.addCell(cellStru);
	    	String nomeResponsabile="";
	    	if(inc.responsabile != null){
	    		nomeResponsabile = inc.responsabile;
	    	}
	    	PdfPCell cellResp = new PdfPCell(new Paragraph("Responsabile "+nomeResponsabile+" con incarico: "+inc.responsabile));
	    	tableStru.addCell(cellResp);
	    	//PdfPCell competenze = new PdfPCell(new Paragraph(mapItem.getKey().getCompetenze()));
	    	//tableStru.addCell(competenze);
	    	//
		    doc.add(tableStru);
		    doc.add(new Paragraph("\n"));
	    	// itero sugli obiettivi
	    	for(Obiettivo obj : inc.getObiettivi()){
	    		String apicale ="";
		    	if (obj.isApicale()) apicale = "APICALE - ";
		    	// creo tabella azioni / prodotti /scadenze
		    	// Genero una tabella
			    PdfPTable table = new PdfPTable(7);
			    
			    
			    // define relative columns width p.96
			    table.setWidths(new int[] {3,6,3,2,6,6,6});
			    //
			    PdfPCell cellobj;
			    if (obj.getTipo().ordinal() == 0) cellobj = new PdfPCell(new Paragraph(apicale+ " - "+obj.getCodice()+ " - "+obj.getDescrizione() +
			    		 obj.getNote()+ " peso: "+obj.getPeso(),fontObj));
			    else cellobj = new PdfPCell(new Paragraph(obj.getCodice()+ " - "+obj.getDescrizione(),fontObj));
			    cellobj.setColspan(7);
			    cellobj.setBackgroundColor(Color.GRAY);
			    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellobj);
			 // itero sulle azioni
			    for (Azione act : obj.getAzioni()){
			    	// act descript row
				    PdfPCell cellActDescr = new PdfPCell(new Phrase("Azione: "+ act.getDenominazione() +" - "+act.getDescrizione() +" "+ act.getNote()));
				    cellActDescr.setBackgroundColor(Color.LIGHT_GRAY);
				    cellActDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cellActDescr.setColspan(7);
				    table.addCell(cellActDescr);
				    // first header row
				    PdfPCell cellh1 = new PdfPCell(new Phrase("Indicatore"));
				    cellh1.setBackgroundColor(Color.CYAN);
				    cellh1.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellh1);
				    PdfPCell cellh2 = new PdfPCell(new Phrase("Valore Obiettivo"));
				    cellh2.setBackgroundColor(Color.CYAN);
				    cellh2.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellh2);
				    PdfPCell cellh3 = new PdfPCell(new Phrase("Scadenza"));
				    cellh3.setBackgroundColor(Color.CYAN);
				    cellh3.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellh3);
				    PdfPCell cellh4 = new PdfPCell(new Phrase("Peso"));
				    cellh4.setBackgroundColor(Color.CYAN);
				    cellh4.setHorizontalAlignment(Element.ALIGN_CENTER);
				    PdfPCell cellh5 = new PdfPCell(new Phrase("Risultato"));
				    cellh5.setBackgroundColor(Color.CYAN);
				    if (obj.getTipo().ordinal() == 0) table.addCell(cellh4);
				    else cellh5.setColspan(2);
				    table.addCell(cellh5);
				    PdfPCell cellh6 = new PdfPCell(new Phrase("Documenti"));
			    	cellh6.setBackgroundColor(Color.CYAN);
			    	table.addCell(cellh6);
			    	PdfPCell cellh7 = new PdfPCell(new Phrase("Indicazioni"));
			    	cellh7.setBackgroundColor(Color.CYAN);
			    	table.addCell(cellh7);
			    
			    	PdfPCell cellr1 = new PdfPCell(new Phrase(""+act.getIndicatore()));
				    cellr1.setBackgroundColor(Color.WHITE);
				    cellr1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr1);
				    PdfPCell cellr2 = new PdfPCell(new Phrase(act.getProdotti()));
				    cellr2.setBackgroundColor(Color.WHITE);
				    cellr2.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr2);
				    PdfPCell cellr3 = new PdfPCell(new Phrase(" - "));
				    if(null != act.getScadenza()){
				    	cellr3 = new PdfPCell(new Phrase(sdf.format(act.getScadenza())));
				    }
				    cellr3.setBackgroundColor(Color.WHITE);
				    cellr3.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr3);
				    PdfPCell cellr4 = new PdfPCell(new Phrase(""+act.getPeso()));
				    cellr4.setBackgroundColor(Color.WHITE);
				    cellr4.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr4);
				    PdfPCell cellr5 = new PdfPCell(new Phrase(""+act.getRisultato()));
				    cellr5.setBackgroundColor(Color.WHITE);
				    cellr5.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr5);
				    String docs = "";
			    	for (Documento docum : act.getDocumenti()){
			    		docs += docum.getDescrizione() + " \n";
			    		}
			    	PdfPCell cellr6 = new PdfPCell(new Phrase(docs));
			    	cellr6.setBackgroundColor(Color.WHITE);
				    cellr6.setHorizontalAlignment(Element.ALIGN_LEFT);
			    	table.addCell(cellr6);
			    	//if (obj.getTipo().ordinal() == 0) table.addCell(cellr4); 
				    //else cellr5.setColspan(2);
			    	//table.addCell(cellr5);
			    	String indics = "";
			    	for (Criticita crit : act.getCriticita()){
			    		indics += crit.getIndicazioni() + " \n";
			    	}
			    	PdfPCell cellr7 = new PdfPCell(new Phrase(indics));
			    	cellr7.setBackgroundColor(Color.WHITE);
				    cellr7.setHorizontalAlignment(Element.ALIGN_LEFT);
			    	table.addCell(cellr7);
			    }
			    doc.add(table);

			    
			    doc.add(new Paragraph("\n"));
	    	}
	    	
	    	//doc.add(new Paragraph("\n"));
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
    	
    	
    	
    } // fine metodo ---------------------------------------------------------------------------------
 
    
   //*************************
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfRendicontazioneApicaleIncaricoController/{anno}/{idIncarico}")
    public void pdfRendicontazioneApicaleIncaricoController(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// estraggo le collections che mi servono 
    	final IncaricoGeko incaricoApic = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
    	incaricoApic.setObiettivi(listObiettiviApicali);
    	//List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(incaricoApic.idIncarico, anno); // una sola in realt�
    	//incaricoApic.setValutazioni(lstValutDirig);

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
    	Document doc = new Document(PageSize.A3);
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
		
		
		// imposto i font
		Font fontTitle1 = new Font();
		fontTitle1.setFamily("HELVETICA");
		fontTitle1.setStyle("BOLD");
		fontTitle1.setColor(Color.BLUE);
		fontTitle1.setSize(24.0f);
		//
		Font fontTitle2 = new Font();
		fontTitle2.setFamily("HELVETICA");
		fontTitle2.setStyle("BOLD");
		fontTitle2.setColor(Color.BLUE);
		fontTitle2.setSize(20.0f);
		//
		//
		Font fontTitle3 = new Font();
		fontTitle3.setFamily("HELVETICA");
		fontTitle3.setStyle("BOLD");
		fontTitle3.setColor(Color.BLUE);
		fontTitle3.setSize(16.0f);
		
		Font fontObj = new Font();
		fontObj.setFamily("HELVETICA");
		//fontObj.setStyle("ITALICS");
		fontObj.setColor(Color.WHITE);
		fontObj.setSize(12.0f);
		
		// scrivo sulla prima pagina il titolo del report
		
		Paragraph titolo = new Paragraph("RENDICONTAZIONE APICALE",fontTitle1);
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
		    titolo2 = new Paragraph("RESPONSABILE "+incaricoApic.responsabile);
	    } else{
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale");
	    }
	    titolo2.setAlignment("CENTER");
     	doc.add(titolo2);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	   
	    // salto-pagina
		doc.newPage();
		
		// verifico se lista vuota
	    if (listObiettiviApicali.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Obiettivo Apicale trovato per "+incaricoApic.denominazioneStruttura+" - anno:"+anno);
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
   
    private void generaSchedaObiettiviApicali(Document doc, IncaricoGeko inc, int anno){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
    	//
		Font fontTitle3 = new Font();
		fontTitle3.setFamily("HELVETICA");
		fontTitle3.setStyle("BOLD");
		fontTitle3.setColor(Color.BLUE);
		fontTitle3.setSize(16.0f);
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
    	PdfPCell cellResp = new PdfPCell(new Paragraph("DIRIGENTE GENERALE: "+nomeResponsabile));
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
		//this.addFirme(doc, "IL PRESIDENTE / L'ASSESSORE","IL DIRIGENTE GENERALE","",inc.getResponsabile());
    } // fine metodo generaSchedaObiettivi
    
 // ------------------------------------------------------------------------------------
    private void generaSchedeDettaglioObiettiviApicali(Document doc, IncaricoGeko inc, int anno) throws MalformedURLException{
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
		    PdfPTable table = new PdfPTable(7);
		    // define relative columns width p.96
		    try {
				table.setWidths(new int[] {1,4,1,1,1,1,1});
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}					 
		    //
		    PdfPCell cellobj;
	    	cellobj = new PdfPCell(new Paragraph("Descrizione obiettivo:"+obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote()));		
		    cellobj.setBackgroundColor(Color.WHITE);
		    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cellobj.setColspan(7);
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
		    PdfPCell thPesoAct = new PdfPCell(new Phrase("Peso"));
		    thPesoAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thPesoAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thPesoAct);
		    
		    PdfPCell thRisulAct = new PdfPCell(new Phrase("Risultato"));
		    thRisulAct.setBackgroundColor(Color.LIGHT_GRAY);
		    thRisulAct.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thRisulAct);
		    
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
		    //
		    PdfPCell thColG = new PdfPCell(new Phrase("Colonna G"));
		    thColG.setBackgroundColor(Color.LIGHT_GRAY);
		    thColG.setHorizontalAlignment(Element.ALIGN_LEFT);
		    table.addCell(thColG);
		    
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
			    if (act.getScadenzaApicale() != null)	strcellr3+=	sdf.format(act.getScadenzaApicale());
			    if (act.isTassativa()) strcellr3 += " TASSATIVA";
			    cellr3 = new PdfPCell(new Phrase(strcellr3));			    
			    cellr3.setBackgroundColor(Color.WHITE);
			    cellr3.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr3);
			    PdfPCell cellr4 = new PdfPCell(new Phrase(""+act.getPesoApicale()));
			    cellr4.setBackgroundColor(Color.WHITE);
			    cellr4.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr4);
			    PdfPCell cellr5 = new PdfPCell(new Phrase(act.getRisultato()));
			    cellr5.setBackgroundColor(Color.WHITE);
			    cellr5.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellr5);
			    //
			    table.addCell(thCol);
			    //
			    PdfPCell thDocDescr = new PdfPCell(new Phrase("Descrizione documento"));
			    thDocDescr.setBackgroundColor(Color.LIGHT_GRAY);
			    thDocDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(thDocDescr);
			    //
			    PdfPCell thDocNome = new PdfPCell(new Phrase("Nome"));
			    thDocNome.setBackgroundColor(Color.LIGHT_GRAY);
			    thDocNome.setHorizontalAlignment(Element.ALIGN_LEFT);	
			    thDocNome.setColspan(2);
			    table.addCell(thDocNome);
			    //
			    PdfPCell thDocFile = new PdfPCell(new Phrase("File"));
			    thDocFile.setBackgroundColor(Color.LIGHT_GRAY);
			    thDocFile.setHorizontalAlignment(Element.ALIGN_LEFT);	
			    thDocFile.setColspan(3);
			    table.addCell(thDocFile);
			    //
			    for (Documento docu : act.getDocumenti()) {
				    PdfPCell cellRisVuota = new PdfPCell(new Phrase(""));
				    cellRisVuota.setBackgroundColor(Color.WHITE);
				    cellRisVuota.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellRisVuota);
				    //
				    PdfPCell cellDocDescrizione = new PdfPCell(new Phrase(docu.getDescrizione()));
				    cellDocDescrizione.setBackgroundColor(Color.WHITE);
				    cellDocDescrizione.setHorizontalAlignment(Element.ALIGN_LEFT);			   
				    table.addCell(cellDocDescrizione);
				    //
				    Font fontLink = new Font();
				    fontLink.setFamily("HELVETICA");
				    fontLink.setStyle("ITALIC");
				    fontLink.setColor(Color.BLUE);
				    fontLink.setSize(12.0f);
				    Chunk imdb = new Chunk(docu.getNome(), fontLink);
				    imdb.setAction(new PdfAction(new URL("http://172.28.109.94:9000/dirigenteDocumenti/download/"+docu.getId())));
				    Paragraph p = new Paragraph("");
				    p.add(imdb);
				    PdfPCell cellDocNome = new PdfPCell(p);
			
				    cellDocNome.setBackgroundColor(Color.WHITE);
				    cellDocNome.setHorizontalAlignment(Element.ALIGN_LEFT);	
				    cellDocNome.setColspan(2);
				    table.addCell(cellDocNome);
				    //
				    PdfPCell cellDocNomeFile = new PdfPCell(new Phrase(docu.getNomefile()));
				    cellDocNomeFile.setBackgroundColor(Color.WHITE);
				    cellDocNomeFile.setHorizontalAlignment(Element.ALIGN_LEFT);		
				    cellDocNomeFile.setColspan(3);
				    table.addCell(cellDocNomeFile);
				    // "/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nome} </a>
		         	/*
		         	 Chunk imdb = new Chunk(
					"Internet Movie Database", FilmFonts.ITALIC);
					imdb.setAction(
					new PdfAction(new URL("http://www.imdb.com/")));
					p = new Paragraph("Click on a country, and you'll get a list of movies,"
					+ " containing links to the ");
					p.add(imdb);
					p.add(".");
					document.add(p);
		         	 */
				    
				    
				    
			    }
			    // riga spaziatura tra obiettivi
			    table.addCell(thCol);
			    table.addCell(thCol);
			    table.addCell(thCol);
			    table.addCell(thCol);
			    table.addCell(thCol);
			    table.addCell(thCol);
			    table.addCell(thCol);
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
		    //this.addFirme(doc, "IL PRESIDENTE / L'ASSESSORE","IL DIRIGENTE GENERALE","",inc.getResponsabile());
    	} // fine for obiettivi
    	
    } // fine metodo generaSchedeDettaglioObiettiviApicali
    
    
} // ---------------------------------------------
