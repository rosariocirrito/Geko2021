package it.sicilia.regione.gekoddd.geko.programmazione.web.gab;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo.StatoApprovEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo.TipoEnum;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazioneComparto.ValutazioneCompartoQryService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
@RequestMapping("/gabinettoPdf")
public class GabinettoProgrPdfController  {
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
    public GabinettoProgrPdfController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
    
    //-----------------------Pianificazione Dipartimentale --------------------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfPianificazioneCompletaIncaricoController/{anno}/{idIncarico}")
    public void pdfPianificazioneDipartimentoControllerManager(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
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
		Paragraph titolo = new Paragraph("PROGRAMMAZIONE DIPARTIMENTALE",fontTitle1);
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
    	
	    // // itero sulla Mappa 
	    // for (Map.Entry<Incarico, List<Obiettivo>> mapItem : mapObiettiviDept.entrySet()) {
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
	    		
		    	// creo tabella azioni / prodotti /scadenze
		    	// Genero una tabella
			    PdfPTable table = new PdfPTable(5);
			    
			    
			    // define relative columns width p.96
			    table.setWidths(new int[] {2,3,1,1,3});
			    //
			    PdfPCell cellobj;
			    String apicale ="";
		    	if (obj.isApicale()) apicale = "APICALE - ";
			    if (obj.getTipo().ordinal() == 0) cellobj = new PdfPCell(new Paragraph(apicale+
			    		 " - "+obj.getCodice()+ " - "+obj.getDescrizione() +
			    		 " peso: "+obj.getPeso(),fontObj));
			    else cellobj = new PdfPCell(new Paragraph(obj.getCodice()+ " - "+obj.getDescrizione(),fontObj));
			    cellobj.setColspan(5);
			    cellobj.setBackgroundColor(Color.GRAY);
			    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellobj);
			    //
			    PdfPCell cellNote = new PdfPCell(new Paragraph(obj.getNote() ,fontObj));
			    cellNote.setColspan(5);
			    cellNote.setBackgroundColor(Color.LIGHT_GRAY);
			    cellNote.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellNote);
			 // itero sulle azioni
			    for (Azione act : obj.getAzioni()){
			    	// act descript row
				    PdfPCell cellActDescr = new PdfPCell(new Phrase("Azione: "+ act.getDenominazione() +" - "+act.getDescrizione() +" "+ act.getNote()));
				    cellActDescr.setBackgroundColor(Color.LIGHT_GRAY);
				    cellActDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cellActDescr.setColspan(5);
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
				    PdfPCell cellh5 = new PdfPCell(new Phrase("Dipendenti"));
				    cellh5.setBackgroundColor(Color.CYAN);
				    if (obj.getTipo().ordinal() == 0) table.addCell(cellh4);
				    else cellh5.setColspan(2);
				    table.addCell(cellh5);
			    
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
				    //table.addCell(cellr4);
				    String dips = "";
				    if (!act.getAssegnazioni().isEmpty()){
				    	for (AzioneAssegnazione ass : act.getAssegnazioni()){
				    		PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(ass.getPfID());
				    		dips += dip.stringa + " \n";
				    	}
				    }
			    	PdfPCell cellr5 = new PdfPCell(new Phrase(dips));
			    	cellr5.setBackgroundColor(Color.WHITE);
				    cellr5.setHorizontalAlignment(Element.ALIGN_LEFT);
			    	//table.addCell(cellr5);
			    	if (obj.getTipo().ordinal() == 0) table.addCell(cellr4); 
				    else cellr5.setColspan(2);
			    	table.addCell(cellr5);
			    }
			    doc.add(table);

			    
			    doc.add(new Paragraph("\n"));
	    	}
	    	PdfPTable tableFirma = new PdfPTable(3);
		    tableFirma.setWidths(new int[] {1,3,3});
		    // genero le firme
		    // riga firma
		    PdfPCell cellData = new PdfPCell(new Phrase("Data" ));
		    cellData.setBackgroundColor(Color.WHITE);
		    cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableFirma.addCell(cellData);
		    PdfPCell cellfdirig = new PdfPCell(new Phrase(""+incarico.responsabile));
		    cellfdirig.setBackgroundColor(Color.WHITE);
		    cellfdirig.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableFirma.addCell(cellfdirig);
		    String nomeResponsabile2="";
	    	if(inc.responsabile != null){
	    		nomeResponsabile2 = inc.responsabile;
	    	}
		    PdfPCell cellfdip= new PdfPCell(new Phrase(nomeResponsabile2));
		    cellfdip.setBackgroundColor(Color.WHITE);
		    cellfdip.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableFirma.addCell(cellfdip);
		    // riga _________ 
		    
		    cellData = new PdfPCell(new Phrase("___/___/___"));
		    cellData.setBackgroundColor(Color.WHITE);
		    cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cellData.setVerticalAlignment(Element.ALIGN_BOTTOM);
		    cellData.setFixedHeight(30.0f);
		    cellData.setBorderColor(Color.WHITE);
		    tableFirma.addCell(cellData);
		    cellfdirig = new PdfPCell(new Phrase(""+"_________________________________"));
		    cellfdirig.setBackgroundColor(Color.WHITE);
		    cellfdirig.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cellfdirig.setVerticalAlignment(Element.ALIGN_BOTTOM);
		    cellfdirig.setFixedHeight(30.0f);
		    cellfdirig.setBorderColor(Color.WHITE);
		    tableFirma.addCell(cellfdirig);
		    cellfdip= new PdfPCell(new Phrase("__________________________________"));
		    cellfdip.setBackgroundColor(Color.WHITE);
		    cellfdip.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cellfdip.setVerticalAlignment(Element.ALIGN_BOTTOM);
		    cellfdip.setFixedHeight(30.0f);
		    cellfdip.setBorderColor(Color.WHITE);
		    tableFirma.addCell(cellfdip);
		    // 
		    doc.add(tableFirma);
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
 
    
 // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfPianificazioneSemplificataIncaricoController/{anno}/{idIncarico}")
    public void pdfPianificazioneSemplificataControllerManager(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
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
		Paragraph titolo = new Paragraph("PROGRAMMAZIONE DIPARTIMENTALE",fontTitle1);
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
    	
	    // // itero sulla Mappa 
	    // for (Map.Entry<Incarico, List<Obiettivo>> mapItem : mapObiettiviDept.entrySet()) {
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
	    		
		    	// creo tabella azioni / prodotti /scadenze
		    	// Genero una tabella
			    //PdfPTable table = new PdfPTable(5);
			    PdfPTable table = new PdfPTable(4);
			    
			    
			    // define relative columns width p.96
			    //table.setWidths(new int[] {2,3,1,1,3});
			    table.setWidths(new int[] {2,3,1,1});
			    //
			    PdfPCell cellobj;
			    String apicale ="";
		    	if (obj.isApicale()) apicale = "APICALE - ";
			    if (obj.getTipo().ordinal() == 0) cellobj = new PdfPCell(new Paragraph(apicale+
			    		" - "+obj.getCodice()+ " - "+obj.getDescrizione() +
			    		 " peso: "+obj.getPeso(),fontObj));
			    else cellobj = new PdfPCell(new Paragraph(obj.getCodice()+ " - "+obj.getDescrizione(),fontObj));
			    //cellobj.setColspan(5);
			    cellobj.setColspan(4);
			    cellobj.setBackgroundColor(Color.GRAY);
			    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellobj);
			    //
			    PdfPCell cellNote = new PdfPCell(new Paragraph(obj.getNote() ,fontObj));
			    cellNote.setColspan(5);
			    cellNote.setBackgroundColor(Color.LIGHT_GRAY);
			    cellNote.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellNote);
			 // itero sulle azioni
			    for (Azione act : obj.getAzioni()){
			    	// act descript row
				    PdfPCell cellActDescr = new PdfPCell(new Phrase("Azione: "+ act.getDenominazione() +" - "+act.getDescrizione() +" "+ act.getNote()));
				    cellActDescr.setBackgroundColor(Color.LIGHT_GRAY);
				    cellActDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
				    cellActDescr.setColspan(5);
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
				    table.addCell(cellh4);
				    //PdfPCell cellh5 = new PdfPCell(new Phrase("Dipendenti"));
				    //cellh5.setBackgroundColor(Color.CYAN);
				    //if (obj.getTipo().ordinal() == 0) table.addCell(cellh4);
				    //else cellh5.setColspan(2);
				    //table.addCell(cellh5);
			    
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
				    /*
				    String dips = "";
				    if (!act.getAssegnazioni().isEmpty()){
				    	for (AzioneAssegnazione ass : act.getAssegnazioni()){
				    		PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(ass.getPfID());
				    		dips += dip.stringa + " \n";
				    	}
				    }
			    	PdfPCell cellr5 = new PdfPCell(new Phrase(dips));
			    	cellr5.setBackgroundColor(Color.WHITE);
				    cellr5.setHorizontalAlignment(Element.ALIGN_LEFT);
			    	//table.addCell(cellr5);
			    	if (obj.getTipo().ordinal() == 0) table.addCell(cellr4); 
				    else cellr5.setColspan(2);
			    	table.addCell(cellr5);
			    	*/
			    }
			    doc.add(table);

			    
			    doc.add(new Paragraph("\n"));
	    	}
	    	PdfPTable tableFirma = new PdfPTable(3);
		    tableFirma.setWidths(new int[] {1,3,3});
		    // genero le firme
		    // riga firma
		    PdfPCell cellData = new PdfPCell(new Phrase("Data" ));
		    cellData.setBackgroundColor(Color.WHITE);
		    cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableFirma.addCell(cellData);
		    PdfPCell cellfdirig = new PdfPCell(new Phrase(""+incarico.responsabile));
		    cellfdirig.setBackgroundColor(Color.WHITE);
		    cellfdirig.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableFirma.addCell(cellfdirig);
		    String nomeResponsabile2="";
	    	if(inc.responsabile != null){
	    		nomeResponsabile2 = inc.responsabile;
	    	}
		    PdfPCell cellfdip= new PdfPCell(new Phrase(nomeResponsabile2));
		    cellfdip.setBackgroundColor(Color.WHITE);
		    cellfdip.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableFirma.addCell(cellfdip);
		    // riga _________ 
		    
		    cellData = new PdfPCell(new Phrase("___/___/___"));
		    cellData.setBackgroundColor(Color.WHITE);
		    cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cellData.setVerticalAlignment(Element.ALIGN_BOTTOM);
		    cellData.setFixedHeight(30.0f);
		    cellData.setBorderColor(Color.WHITE);
		    tableFirma.addCell(cellData);
		    cellfdirig = new PdfPCell(new Phrase(""+"_________________________________"));
		    cellfdirig.setBackgroundColor(Color.WHITE);
		    cellfdirig.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cellfdirig.setVerticalAlignment(Element.ALIGN_BOTTOM);
		    cellfdirig.setFixedHeight(30.0f);
		    cellfdirig.setBorderColor(Color.WHITE);
		    tableFirma.addCell(cellfdirig);
		    cellfdip= new PdfPCell(new Phrase("__________________________________"));
		    cellfdip.setBackgroundColor(Color.WHITE);
		    cellfdip.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cellfdip.setVerticalAlignment(Element.ALIGN_BOTTOM);
		    cellfdip.setFixedHeight(30.0f);
		    cellfdip.setBorderColor(Color.WHITE);
		    tableFirma.addCell(cellfdip);
		    // 
		    doc.add(tableFirma);
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
    	
    	
    	
    } // fine metodo -------semplificata ------------------------------------------------------------------------
 
    
    /*
    
    // ------------- Incarichi dipartimento -----------------------------------
    @RequestMapping(value="listIncarichiDipartimento")
    public void pdfIncarichiManagerQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
		//
		
		
		// -----------------------    
		final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser(); 
		List<Incarico> listIncarichiDept = new ArrayList<Incarico>();
        //
        if (null!=dipartimento){
            //            
            //for (OpPersonaGiuridica struttura : dipartimento.getOpPersonaGiuridicas()){
            for (OpPersonaGiuridica struttura : dipartimento.getSubStrutture()){
                List<Incarico> listIncarichi = struttura.getIncarichi(); 
            	listIncarichiDept.addAll(listIncarichi);
            }
           
        }
		// Aggiungo due paragrafi e una riga vuota
	    //doc.add(new Paragraph("Questo documento � stato creato da una classe chiamata: " +
	    //this.getClass().getName()));
	    //doc.add(new Paragraph("Questo documento � stato creato il " + new java.util.Date()));
	    //doc.add(new Paragraph("\n"));  
	    // Riga vuota
	    //doc.add(new Paragraph("\n"));
	    // Genero una tabella
	    PdfPTable table = new PdfPTable(4);
	    // define relative columns width p.96
	    table.setWidths(new int[] {4,4,1,1});
	    // first header row
	    PdfPCell cell = new PdfPCell(new Phrase("Incarichi Dirigenziali per "+dipartimento.getDenominazione()));
	    cell.setColspan(4);
	    cell.setBackgroundColor(Color.CYAN);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(cell);
	    // aggiunge il 2 header ed il footer
	    table.getDefaultCell().setBackgroundColor(Color.LIGHT_GRAY);
	    for (int i = 0; i<2; i++){
	    	table.addCell("Struttura");
	    	table.addCell("Responsabile");
	    	table.addCell("Data Inizio");
	    	table.addCell("Data Fine");
	    }
	    //
	    table.getDefaultCell().setBackgroundColor(null);
	    table.setHeaderRows(3); // 3 header rows
	    table.setFooterRows(1); // di cui 1 footer
	    //
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
	    // vedi p 113 per header ripetuti
	    for (Incarico incarico : listIncarichiDept ){
	    	table.addCell(incarico.getOpPersonaGiuridica().getDenominazione());
	    	table.addCell(incarico.getOpPersonaFisica().getStringa());
		    if (null != incarico.getDataInizio() ) table.addCell(sdf.format(incarico.getDataInizio()));
		    else table.addCell("-");
		    if (null != incarico.getDataFine() )table.addCell(sdf.format(incarico.getDataFine()));	
		    else table.addCell("-");
	    }
	    //
	    
	    doc.add(table);
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
	} // fine pdf incarichi
    
    	
 
    */
    
    
    //-----------------------Scheda A Apicale --------------------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfPianificazioneApicaleSchedaAGabinetto/{anno}/{idIncarico}")
    public void pdfPianificazioneApicaleSchedaAController(@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	
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
    	Document doc = new Document(PageSize.A4);
    	doc.setMargins(1, 10, 10, 10);
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
		fontTitle1.setColor(Color.BLACK);
		fontTitle1.setSize(24.0f);
		//
		Font fontTitle2 = new Font();
		fontTitle2.setFamily("HELVETICA");
		fontTitle2.setStyle("BOLD");
		fontTitle2.setColor(Color.BLACK);
		fontTitle2.setSize(20.0f);
		//
		//
		Font fontTitle3 = new Font();
		fontTitle3.setFamily("HELVETICA");
		fontTitle3.setStyle("BOLD");
		fontTitle3.setColor(Color.BLACK);
		fontTitle3.setSize(16.0f);
		
		Font fontObj = new Font();
		fontObj.setFamily("HELVETICA");
		//fontObj.setStyle("ITALICS");
		fontObj.setColor(Color.BLACK);
		fontObj.setSize(8.0f);
		
		Font fontQuadro = new Font();
		fontQuadro.setFamily("HELVETICA");
		fontQuadro.setStyle("ITALICS");
		fontQuadro.setColor(Color.BLACK);
		fontQuadro.setSize(8.0f);
		
		Font fontHeader = new Font();
		fontHeader.setFamily("HELVETICA");
		//fontHeader.setStyle("ITALICS");
		fontHeader.setColor(Color.BLACK);
		fontHeader.setSize(10.0f);
        
        // scrivo sulla prima pagina il titolo del report
		//Paragraph titolo = new Paragraph("AMMINISTRAZIONE " + dipartimento.getOpPersonaGiuridica().getDenominazione(),fontTitle1);
		Paragraph titolo = new Paragraph("DIPARTIMENTO " + incarico.denominazioneStruttura,fontTitle1);
		titolo.setAlignment("CENTER");
		
     	doc.add(titolo);
     	
	    // Riga vuota
	 	//doc.add(new Paragraph("\n"));
	    
	    // titolo 2 responsabile: P:M.
	    //dipartimento.setAnno(anno); // per individuare il responsabile
	    Paragraph titolo2;
	    if (null != incarico.responsabile) {
		    titolo2 = new Paragraph(incarico.responsabile.toString());
	    } else{
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale)");
	    }
	    titolo2.setAlignment("CENTER");
     	doc.add(titolo2);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	    
		// verifico se mappa vuota
	    if (listObiettiviApicali.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Obiettivo Apicale trovato per "+incarico.denominazioneStruttura+" - anno:"+anno);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
	    
	 // scheda A --------------------------------------------
    	// Genero la tabella della scheda A
	    PdfPTable tableA = new PdfPTable(8);
	    tableA.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	    //tableA.getDefaultCell().setCellEvent(roundRectangle);
	    // define relative columns width p.96
	    tableA.setWidths(new int[] {2,12,3,4,3,5,5,4});
	    
	    // RIGA scheda A
	    PdfPCell cellSchedaA = new PdfPCell(new Paragraph("SCHEDA A"));
	    cellSchedaA.setColspan(8);
	    cellSchedaA.setBackgroundColor(Color.LIGHT_GRAY);
	    cellSchedaA.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellSchedaA);
	    // RIGA scheda A2
	    PdfPCell cellSchedaA2 = new PdfPCell(new Paragraph("quadro riassuntivo obiettivi assegnati ad inizio esercizio al Dirigente Generale ai fini della successiva valutazione finale dei risultati conseguiti",fontQuadro));
	    cellSchedaA2.setColspan(8);
	    cellSchedaA2.setBackgroundColor(Color.LIGHT_GRAY);
	    cellSchedaA2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellSchedaA2);
	    //
	    PdfPCell cellaVuota1 = new PdfPCell(new Paragraph("\n"));
	    cellaVuota1.setColspan(8);
	    tableA.addCell(cellaVuota1);
	    // RIGA anno scheda A
	    PdfPCell cellSchedaA3 = new PdfPCell(new Paragraph("ANNO "+anno,fontTitle3));
	    cellSchedaA3.setColspan(8);
	    cellSchedaA3.setBackgroundColor(Color.WHITE);
	    cellSchedaA3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellSchedaA3);
	    //
	    PdfPCell cellaVuota2 = new PdfPCell(new Paragraph("\n"));
	    cellaVuota2.setColspan(8);
	    tableA.addCell(cellaVuota2);
	    //
	    PdfPCell cellSchedaA4 = new PdfPCell(new Paragraph("PERFORMANCE OPERATIVA"));
	    cellSchedaA4.setColspan(8);
	    cellSchedaA4.setBackgroundColor(Color.LIGHT_GRAY);
	    cellSchedaA4.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellSchedaA4);
	    //
	    // first header row
	    PdfPCell cellh1 = new PdfPCell(new Phrase("N"));
	    cellh1.setBackgroundColor(Color.WHITE);
	    cellh1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellh1);
	    PdfPCell cellh2 = new PdfPCell(new Phrase("Descrizione sintetica obiettivi operativi",fontHeader));
	    cellh2.setBackgroundColor(Color.WHITE);
	    cellh2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellh2);
	    PdfPCell cellh3 = new PdfPCell(new Phrase("Priorit�",fontHeader));
	    cellh3.setBackgroundColor(Color.LIGHT_GRAY);
	    cellh3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellh3);
	    PdfPCell cellh4 = new PdfPCell(new Phrase("Corr. Area",fontHeader));
	    cellh4.setBackgroundColor(Color.WHITE);
	    cellh4.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellh4);
	    PdfPCell cellh5 = new PdfPCell(new Phrase("Indicatore previsto",fontHeader));
	    cellh5.setBackgroundColor(Color.WHITE);
	    cellh5.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellh5);
	    PdfPCell cellh6 = new PdfPCell(new Phrase("Valore Obiettivo",fontHeader));
	    cellh6.setBackgroundColor(Color.WHITE);
	    cellh6.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellh6);
	    PdfPCell cellh7 = new PdfPCell(new Phrase("Data ultima",fontHeader));
	    cellh7.setBackgroundColor(Color.WHITE);
	    cellh7.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellh7);
	    PdfPCell cellh8 = new PdfPCell(new Phrase("Peso attribuito",fontHeader));
	    cellh8.setBackgroundColor(Color.LIGHT_GRAY);
	    cellh8.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cellh8);
	    
	    int nrObj =0;
	    int totPesoApicale=0;
    	// itero sulla Mappa e per ogni struttura estraggo gli obiettivi apicali
    	//for (Map.Entry<Incarico, List<Obiettivo>> mapItem : mapObiettiviDept.entrySet()) {
    		// itero sugli obiettivi
	    	for(Obiettivo obj : listObiettiviApicali){
	    		nrObj++;
	    		totPesoApicale += obj.getPesoApicale(); 
	    		//
	    		PdfPCell cell1 = new PdfPCell(new Paragraph(""+nrObj,fontObj));
	    		cell1.setBackgroundColor(Color.WHITE);
	    		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableA.addCell(cell1);
			    //
	    		PdfPCell cell2 = new PdfPCell(new Paragraph(obj.getDescrizione(),fontObj));
	    		cell2.setBackgroundColor(Color.WHITE);
	    		cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableA.addCell(cell2);
			    //
	    		PdfPCell cell3 = new PdfPCell(new Paragraph("",fontObj));
	    		cell3.setBackgroundColor(Color.LIGHT_GRAY);
	    		cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableA.addCell(cell3);
			    //
			    String strStrategici="";
			    for(AssociazObiettivi assoc : obj.getAssociazObiettivi()){
			    	strStrategici += " "+assoc.getStrategico().getOrder();
			    }
	    		PdfPCell cell4 = new PdfPCell(new Paragraph(strStrategici,fontObj));
	    		cell4.setBackgroundColor(Color.WHITE);
	    		cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableA.addCell(cell4);
			    //
	    		PdfPCell cell5 = new PdfPCell(new Paragraph(obj.getIndicatore(),fontObj));
	    		cell5.setBackgroundColor(Color.WHITE);
	    		cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableA.addCell(cell5);
			    //
	    		PdfPCell cell6 = new PdfPCell(new Paragraph(obj.getValObiettivo(),fontObj));
	    		cell6.setBackgroundColor(Color.WHITE);
	    		cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableA.addCell(cell6);
			    // 
			    PdfPCell cell7;
			    if (null != obj.getDataUltimaApicale())
			    	cell7 = new PdfPCell(new Paragraph(sdf.format(obj.getDataUltimaApicale()),fontObj));
			    else
			    	cell7 = new PdfPCell(new Paragraph("?",fontObj));
			    cell7.setBackgroundColor(Color.WHITE);
	    		cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableA.addCell(cell7);
			    //
	    		PdfPCell cell8 = new PdfPCell(new Paragraph(""+obj.getPesoApicale(),fontObj));
	    		cell8.setBackgroundColor(Color.LIGHT_GRAY);
	    		cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableA.addCell(cell8);
			    //	
	    	//}
    	}
    	// riga totale
    	PdfPCell cell9 = new PdfPCell(new Paragraph("Raggiungimento obiettivi operativi prioritari (range 45-65)",fontObj));
    	cell9.setColspan(7);
    	cell9.setBackgroundColor(Color.WHITE);
		cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableA.addCell(cell9);
	    //
	    PdfPCell cell10 = new PdfPCell(new Paragraph(""+totPesoApicale,fontObj));
		cell10.setBackgroundColor(Color.LIGHT_GRAY);
		cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableA.addCell(cell10);
	    //
	    int totPerformance=0;
	    int totOrganizzativo=0;
	    //for (Incarico incarico : listIncarichiDept){
	    	for (Valutazione valutazione : incarico.getValutazioni()){
	    		if(valutazione.getAnno()==anno){
	    			// riga totale
	    	    	PdfPCell cell11 = new PdfPCell(new Paragraph("Attuazione piano di lavoro (range 20-30)",fontObj));
	    	    	cell11.setColspan(7);
	    	    	cell11.setBackgroundColor(Color.WHITE);
	    	    	cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		    tableA.addCell(cell11);
	    		    //
	    		    PdfPCell cell12 = new PdfPCell(new Paragraph(""+valutazione.getPdlAss(),fontObj));
	    		    cell12.setBackgroundColor(Color.LIGHT_GRAY);
	    		    cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell12);
	    		    // riga totale
	    	    	PdfPCell cell13 = new PdfPCell(new Paragraph("Totale conseguibile Performance operativa (range 75-85)",fontObj));
	    	    	cell13.setColspan(7);
	    	    	cell13.setBackgroundColor(Color.WHITE);
	    	    	cell13.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	    	tableA.addCell(cell13);
	    		    //
	    	    	totPerformance = totPesoApicale+valutazione.getPdlAss();
	    		    PdfPCell cell14 = new PdfPCell(new Paragraph(""+totPerformance,fontObj));
	    		    cell14.setBackgroundColor(Color.LIGHT_GRAY);
	    		    cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell14);
	    		    // riga comportamento organizzativo
	    	    	PdfPCell cell15 = new PdfPCell(new Paragraph("COMPORTAMENTO ORGANIZZATIVO"));
	    	    	cell15.setColspan(8);
	    	    	cell15.setBackgroundColor(Color.LIGHT_GRAY);
	    	    	cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	    	tableA.addCell(cell15);
	    	    	// riga header comportamento organizzativo
	    	    	PdfPCell cell16 = new PdfPCell(new Paragraph("Qualit� gestionali-relazionali",fontObj));
	    	    	cell16.setColspan(6);
	    	    	cell16.setBackgroundColor(Color.WHITE);
	    	    	cell16.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	    	tableA.addCell(cell16);
	    		    //
	    		    PdfPCell cell17 = new PdfPCell(new Paragraph("Range assegnabile",fontObj));
	    		    cell17.setBackgroundColor(Color.WHITE);
	    		    cell17.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell17);
	    		    //
	    		    PdfPCell cell18 = new PdfPCell(new Paragraph("Peso attribuito",fontObj));
	    		    cell18.setBackgroundColor(Color.WHITE);
	    		    cell18.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell18);
	    		    // riga capacit� analisi e programmazione
	    	    	PdfPCell cell19 = new PdfPCell(new Paragraph("Capacit� di analisi e programmazione",fontObj));
	    	    	cell19.setColspan(6);
	    	    	cell19.setBackgroundColor(Color.WHITE);
	    	    	cell19.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	    	tableA.addCell(cell19);
	    		    //
	    		    PdfPCell cell20 = new PdfPCell(new Paragraph("5-10",fontObj));
	    		    cell20.setBackgroundColor(Color.WHITE);
	    		    cell20.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell20);
	    		    //
	    		    PdfPCell cell21 = new PdfPCell(new Paragraph(""+valutazione.getAnalProgrAss(),fontObj));
	    		    cell21.setBackgroundColor(Color.WHITE);
	    		    cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell21);
	    		 // riga capacit� analisi e programmazione
	    	    	PdfPCell cell22 = new PdfPCell(new Paragraph("Capacit� di relazione e coordinamento",fontObj));
	    	    	cell22.setColspan(6);
	    	    	cell22.setBackgroundColor(Color.WHITE);
	    	    	cell22.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	    	tableA.addCell(cell22);
	    		    //
	    		    PdfPCell cell23 = new PdfPCell(new Paragraph("5-10",fontObj));
	    		    cell23.setBackgroundColor(Color.WHITE);
	    		    cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell23);
	    		    //
	    		    PdfPCell cell24 = new PdfPCell(new Paragraph(""+valutazione.getRelazCoordAss(),fontObj));
	    		    cell24.setBackgroundColor(Color.WHITE);
	    		    cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell24);
	    		 // riga capacit� analisi e programmazione
	    	    	PdfPCell cell25 = new PdfPCell(new Paragraph("Capacit� di gestione e realizzazione",fontObj));
	    	    	cell25.setColspan(6);
	    	    	cell25.setBackgroundColor(Color.WHITE);
	    	    	cell25.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	    	tableA.addCell(cell25);
	    		    //
	    		    PdfPCell cell26 = new PdfPCell(new Paragraph("5-10",fontObj));
	    		    cell26.setBackgroundColor(Color.WHITE);
	    		    cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell26);
	    		    //
	    		    PdfPCell cell27 = new PdfPCell(new Paragraph(""+valutazione.getGestRealAss(),fontObj));
	    		    cell27.setBackgroundColor(Color.WHITE);
	    		    cell27.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell27);
	    		 // riga totale
	    	    	PdfPCell cell28 = new PdfPCell(new Paragraph("Totale conseguibile (range 15-25)",fontObj));
	    	    	cell28.setColspan(7);
	    	    	cell28.setBackgroundColor(Color.WHITE);
	    	    	cell28.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    	    	tableA.addCell(cell28);
	    		    //
	    	    	totOrganizzativo = valutazione.getAnalProgrAss()+valutazione.getRelazCoordAss()+valutazione.getGestRealAss();
	    		    PdfPCell cell29 = new PdfPCell(new Paragraph(""+(totOrganizzativo),fontObj));
	    		    cell29.setBackgroundColor(Color.LIGHT_GRAY);
	    		    cell29.setHorizontalAlignment(Element.ALIGN_CENTER);
	    		    tableA.addCell(cell29);
	    		} // fine if
	    	}
	    	// riga comportamento organizzativo
	    	PdfPCell cell30 = new PdfPCell(new Paragraph("TABELLA RIASSUNTIVA"));
	    	cell30.setColspan(8);
	    	cell30.setBackgroundColor(Color.LIGHT_GRAY);
	    	cell30.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	tableA.addCell(cell30);
	    	// riga header comportamento organizzativo
	    	PdfPCell cell31 = new PdfPCell(new Paragraph("Tabella riassuntiva",fontObj));
	    	cell31.setColspan(6);
	    	cell31.setBackgroundColor(Color.WHITE);
	    	cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	tableA.addCell(cell31);
		    //
		    PdfPCell cell32 = new PdfPCell(new Paragraph("Range assegnabile",fontObj));
		    cell32.setBackgroundColor(Color.WHITE);
		    cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableA.addCell(cell32);
		    //
		    PdfPCell cell33 = new PdfPCell(new Paragraph("Peso attribuito",fontObj));
		    cell33.setBackgroundColor(Color.WHITE);
		    cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableA.addCell(cell33);
		    // riga capacit� analisi e programmazione
	    	PdfPCell cell34 = new PdfPCell(new Paragraph("Totale Performance Operativa",fontObj));
	    	cell34.setColspan(6);
	    	cell34.setBackgroundColor(Color.WHITE);
	    	cell34.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	tableA.addCell(cell34);
		    //
		    PdfPCell cell35 = new PdfPCell(new Paragraph("75-85",fontObj));
		    cell35.setBackgroundColor(Color.WHITE);
		    cell35.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableA.addCell(cell35);
		    //
		    PdfPCell cell36 = new PdfPCell(new Paragraph(""+totPerformance,fontObj));
		    cell36.setBackgroundColor(Color.WHITE);
		    cell36.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableA.addCell(cell36);
		 // riga capacit� analisi e programmazione
	    	PdfPCell cell37 = new PdfPCell(new Paragraph("Totale Comportamento Organizzativo",fontObj));
	    	cell37.setColspan(6);
	    	cell37.setBackgroundColor(Color.WHITE);
	    	cell37.setHorizontalAlignment(Element.ALIGN_LEFT);
	    	tableA.addCell(cell37);
		    //
		    PdfPCell cell38 = new PdfPCell(new Paragraph("5-10",fontObj));
		    cell38.setBackgroundColor(Color.WHITE);
		    cell38.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableA.addCell(cell38);
		    //
		    PdfPCell cell39 = new PdfPCell(new Paragraph(""+totOrganizzativo,fontObj));
		    cell39.setBackgroundColor(Color.WHITE);
		    cell39.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableA.addCell(cell39);
		    // riga totali
	    	PdfPCell cell40 = new PdfPCell(new Paragraph("Totali",fontObj));
	    	cell40.setColspan(6);
	    	cell40.setBackgroundColor(Color.WHITE);
	    	cell40.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    	tableA.addCell(cell40);
		    //
		    PdfPCell cell41 = new PdfPCell(new Paragraph("100/100",fontObj));
		    cell41.setBackgroundColor(Color.WHITE);
		    cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableA.addCell(cell41);
		    //
		    PdfPCell cell42 = new PdfPCell(new Paragraph(""+(totPerformance+totOrganizzativo),fontObj));
		    cell42.setBackgroundColor(Color.WHITE);
		    cell42.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableA.addCell(cell42);
	    	
	    //}
    	//
    	doc.add(tableA);
    	
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

  //-----------------------Scheda B Apicale --------------------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfPianificazioneApicaleSchedaBGabinetto/{anno}/{idIncarico}")
    public void pdfPianificazioneApicaleSchedaBController(
    		@PathVariable("anno") int anno, 
    		@PathVariable("idIncarico") int idIncarico, 
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// estraggo le collections che mi servono 
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	
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
    	Document doc = new Document(PageSize.A4.rotate());
    	//Document doc = new Document(PageSize.A4);
    	doc.setMargins(1, 10, 10, 10);
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
		fontTitle1.setColor(Color.BLACK);
		fontTitle1.setSize(24.0f);
		//
		Font fontTitle2 = new Font();
		fontTitle2.setFamily("HELVETICA");
		fontTitle2.setStyle("BOLD");
		fontTitle2.setColor(Color.BLACK);
		fontTitle2.setSize(20.0f);
		//
		//
		Font fontTitle3 = new Font();
		fontTitle3.setFamily("HELVETICA");
		fontTitle3.setStyle("BOLD");
		fontTitle3.setColor(Color.BLACK);
		fontTitle3.setSize(16.0f);
		
		Font fontObj = new Font();
		fontObj.setFamily("HELVETICA");
		//fontObj.setStyle("ITALICS");
		fontObj.setColor(Color.BLACK);
		fontObj.setSize(8.0f);
		
		Font fontQuadro = new Font();
		fontQuadro.setFamily("HELVETICA");
		fontQuadro.setStyle("ITALICS");
		fontQuadro.setColor(Color.BLACK);
		fontQuadro.setSize(8.0f);
		
		Font fontHeader = new Font();
		fontHeader.setFamily("HELVETICA");
		//fontHeader.setStyle("ITALICS");
		fontHeader.setColor(Color.BLACK);
		fontHeader.setSize(10.0f);
        
        // scrivo sulla prima pagina il titolo del report
		Paragraph titolo = new Paragraph("DIPARTIMENTO " + incarico.denominazioneStruttura,fontTitle1);
		titolo.setAlignment("CENTER");
		
     	doc.add(titolo);
     	
	    // Riga vuota
	 	//doc.add(new Paragraph("\n"));
	    
	    // titolo 2 responsabile: P:M.
	    
	    Paragraph titolo2;
	    if (null != incarico.responsabile) {
		    titolo2 = new Paragraph(incarico.responsabile);
	    } else{
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale)");
	    }
	    titolo2.setAlignment("CENTER");
     	doc.add(titolo2);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	    
		// verifico se mappa vuota
	    if (listObiettiviApicali.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Obiettivo Apicale trovato per "+incarico.denominazioneStruttura+" - anno:"+anno);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
	    
	 // scheda B --------------------------------------------
    	// Genero la tabella della scheda B
	    PdfPTable tableB = new PdfPTable(7);
	    tableB.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
	    //tableA.getDefaultCell().setCellEvent(roundRectangle);
	    // define relative columns width p.96
	    tableB.setWidths(new int[] {1,13,13,4,5,8,9});
	    
	    // RIGA scheda B
	    PdfPCell cellSchedaB = new PdfPCell(new Paragraph("SCHEDA B"));
	    cellSchedaB.setColspan(7);
	    cellSchedaB.setBackgroundColor(Color.LIGHT_GRAY);
	    cellSchedaB.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableB.addCell(cellSchedaB);
	    // RIGA scheda A2
	    PdfPCell cellSchedaB2 = new PdfPCell(new Paragraph("SCHEDA DI DETTAGLIO DELLE AZIONI INDIVIDUATE AI FINI DEL RAGGIUNGIMENTO DEI SINGOLI OBIETTIVI OPERATIVI AI FINI DELLA SUCCESSIVA VALUTAZIONE FINALE DEI RISULTATI CONSEGUITI",fontQuadro));
	    cellSchedaB2.setColspan(7);
	    cellSchedaB2.setBackgroundColor(Color.LIGHT_GRAY);
	    cellSchedaB2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableB.addCell(cellSchedaB2);
	    //
	    PdfPCell cellaVuota1 = new PdfPCell(new Paragraph("\n"));
	    cellaVuota1.setColspan(7);
	    tableB.addCell(cellaVuota1);
	    // RIGA anno scheda A
	    PdfPCell cellSchedaB3 = new PdfPCell(new Paragraph("ANNO "+anno,fontTitle3));
	    cellSchedaB3.setColspan(7);
	    cellSchedaB3.setBackgroundColor(Color.WHITE);
	    cellSchedaB3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tableB.addCell(cellSchedaB3);
	    //
	    PdfPCell cellaVuota2 = new PdfPCell(new Paragraph("\n"));
	    cellaVuota2.setColspan(7);
	    tableB.addCell(cellaVuota2);
	    //
	    
	    //
	    
	    
	    int nrObj =0;
	    int totPesoApicale=0;
    	// itero sulla Mappa e per ogni struttura estraggo gli obiettivi apicali
    	//for (Map.Entry<Incarico, List<Obiettivo>> mapItem : mapObiettiviDept.entrySet()) {
    		// itero sugli obiettivi
	    	for(Obiettivo obj : listObiettiviApicali){
	    		obj.setAnno(anno);
	    		nrObj++;
	    		totPesoApicale += obj.getPesoApicale(); 
	    		//
	    		PdfPCell cell1 = new PdfPCell(new Paragraph("OBIETTIVO OPERATIVO N. "+nrObj,fontObj));
	    		cell1.setColspan(2);
	    		cell1.setBackgroundColor(Color.WHITE);
	    		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableB.addCell(cell1);
			    //
	    		PdfPCell cell2 = new PdfPCell(new Paragraph("DESCRIZIONE: "+obj.getDescrizione(),fontObj));
	    		cell2.setColspan(5);
	    		cell2.setBackgroundColor(Color.WHITE);
	    		cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableB.addCell(cell2);
			    
			    // dettaglio azioni
			    // header azioni
			    // first header row
			    PdfPCell cellh0 = new PdfPCell(new Phrase("",fontObj));
			    cellh0.setBackgroundColor(Color.WHITE);
			    cellh0.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableB.addCell(cellh0);
			    PdfPCell cellh1 = new PdfPCell(new Phrase("Azioni del piano di lavoro concorrenti all'obiettivo",fontObj));
			    cellh1.setColspan(2);
			    cellh1.setBackgroundColor(Color.WHITE);
			    cellh1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableB.addCell(cellh1);
			    PdfPCell cellh5 = new PdfPCell(new Phrase("Indicatore previsto",fontObj));
			    cellh5.setBackgroundColor(Color.WHITE);
			    cellh5.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableB.addCell(cellh5);
			    PdfPCell cellh2 = new PdfPCell(new Phrase("Valore Obiettivo",fontObj));
			    cellh2.setBackgroundColor(Color.WHITE);
			    cellh2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableB.addCell(cellh2);
			    PdfPCell cellh3 = new PdfPCell(new Phrase("Data ultima",fontObj));
			    cellh3.setBackgroundColor(Color.WHITE);
			    cellh3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableB.addCell(cellh3);
			    PdfPCell cellh4 = new PdfPCell(new Phrase("Peso attribuito",fontObj));
			    cellh4.setBackgroundColor(Color.LIGHT_GRAY);
			    cellh4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableB.addCell(cellh4);
			    // dettaglio sulle azioni
			    int nrAct =0;
			    for (Azione act : obj.getAzioni()){
			    	
			    	nrAct++;
			    	// 
			    	PdfPCell cellr0 = new PdfPCell(new Phrase(""+nrAct,fontObj));
				    cellr0.setBackgroundColor(Color.WHITE);
				    cellr0.setHorizontalAlignment(Element.ALIGN_LEFT);
				    tableB.addCell(cellr0);
			    	PdfPCell cellr1 = new PdfPCell(new Phrase(act.getDescrizione(),fontObj));
			    	cellr1.setColspan(2);
				    cellr1.setBackgroundColor(Color.WHITE);
				    cellr1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    tableB.addCell(cellr1);
				    PdfPCell cellr5 = new PdfPCell(new Phrase(act.getIndicatore(),fontObj));
				    cellr5.setBackgroundColor(Color.WHITE);
				    cellr5.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableB.addCell(cellr5);
				    PdfPCell cellr2 = new PdfPCell(new Phrase(act.getProdotti(),fontObj));
				    cellr2.setBackgroundColor(Color.WHITE);
				    cellr2.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableB.addCell(cellr2);
				    PdfPCell cellr3 = new PdfPCell(new Phrase(" ? "));
				    if(null != act.getScadenzaApicale()){
				    	cellr3 = new PdfPCell(new Phrase(sdf.format(act.getScadenzaApicale()),fontObj));
				    }
				    cellr3.setBackgroundColor(Color.WHITE);
				    cellr3.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableB.addCell(cellr3);
				    PdfPCell cellr4 = new PdfPCell(new Phrase(""+act.getPesoApicale(),fontObj));
				    //if (act.getObiettivo().getStruttura().isDipartimento()){
				    //	cellr4 = new PdfPCell(new Phrase(""+act.getPeso(),fontObj));
				    //}
				    
				    cellr4.setBackgroundColor(Color.LIGHT_GRAY);
				    cellr4.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableB.addCell(cellr4);
			    }
			    //
			    PdfPCell cellr5 = new PdfPCell(new Phrase("TOTALE PESO ATTRIBUITO ALL'OBIETTIVO"));
			    cellr5.setColspan(6);
			    cellr5.setBackgroundColor(Color.LIGHT_GRAY);
			    cellr5.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    tableB.addCell(cellr5);
			    //	
			    //
			    PdfPCell cellr6 = new PdfPCell(new Phrase(""+obj.getPesoApicale()));
			    cellr6.setBackgroundColor(Color.LIGHT_GRAY);
			    cellr6.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableB.addCell(cellr6);
			    //
			    //
			    PdfPCell cellr7 = new PdfPCell(new Phrase(""));
			    cellr7.setColspan(7);
			    cellr7.setBackgroundColor(Color.LIGHT_GRAY);
			    cellr7.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableB.addCell(cellr7);
			    //
	    	//}
    	}
    	
	    //
    	//
    	doc.add(tableB);
    	
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
    
    
  //-----------------------Pianificazione Apicale --------------------
    // lista obiettivi e azioni e dipendenti in base al nome del dirigente
    @RequestMapping(value="pdfPianificazioneApicaleIncaricoController/{anno}/{idIncarico}")
    public void pdfPianificazioneApicaleIncaricoController(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// estraggo le collections che mi servono 
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(idIncarico, anno);
    	

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
		Paragraph titolo = new Paragraph("PROGRAMMAZIONE OBIETTIVI APICALI",fontTitle1);
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
	    	titolo2 = new Paragraph("RESPONSABILE DIPARTIMENTO DA INSERIRE !!! (manca incarico apicale)");
	    }
	    titolo2.setAlignment("CENTER");
     	doc.add(titolo2);
     	// Riga vuota
	    doc.add(new Paragraph("\n"));
	    
		// verifico se mappa vuota
	    if (listObiettiviApicali.isEmpty()){
	    	Paragraph empty = new Paragraph("Nessun Obiettivo Apicale trovato per "+incarico.denominazioneStruttura+" - anno:"+anno);
	     	doc.add(empty);
	     	doc.add(new Paragraph("\n"));
	    }
	    
	 
    	
    	// 
			// salto-pagina
			//doc.newPage();
	    	
	    	
	    	
	    	// scheda B
	    	// itero sugli obiettivi
	    	for (Obiettivo obj : listObiettiviApicali) {
	    		
	    		PdfPTable tableStru = new PdfPTable(1);
		    	//tableStru.setTableEvent(tableBackground);
		    	tableStru.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		    	tableStru.getDefaultCell().setCellEvent(whiteRectangle);
		    	PdfPCell cellStru = new PdfPCell(new Paragraph("Struttura "+obj.getIncarico().denominazioneStruttura,fontTitle3));
		    	tableStru.addCell(cellStru);
		    	String userName = obj.getIncarico().responsabile;  
		        
		    	PdfPCell cellResp = new PdfPCell(new Paragraph("Responsabile "+userName));
		    	tableStru.addCell(cellResp);
		    	
		    	//
		    	doc.add(tableStru);
		    	// creo tabella azioni / prodotti /scadenze
		    	// Genero una tabella
			    PdfPTable table = new PdfPTable(6);
			    
			    
			    // define relative columns width p.96
			    table.setWidths(new int[] {4,2,1,1,1,1});
			    //
			    for (AssociazObiettivi associaz : obj.getAssociazObiettivi()){
			    PdfPCell cellStrat = new PdfPCell(new Paragraph(associaz.getStrategico().getDescrizione(),fontObj));
			    
			    cellStrat.setColspan(6);
			    cellStrat.setBackgroundColor(Color.LIGHT_GRAY);
			    cellStrat.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellStrat);
			    }
			    //
			    PdfPCell cellobj = new PdfPCell(new Paragraph(
		    			" - "+obj.getDescrizione() + " peso: "+obj.getPesoApicale(),fontObj));
			    
			    cellobj.setColspan(6);
			    cellobj.setBackgroundColor(Color.GRAY);
			    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellobj);
			    //
			    PdfPCell cellNote = new PdfPCell(new Paragraph(obj.getNote() ,fontObj));
			    
			    cellNote.setColspan(6);
			    cellNote.setBackgroundColor(Color.LIGHT_GRAY);
			    cellNote.setHorizontalAlignment(Element.ALIGN_LEFT);
			    table.addCell(cellNote);
			    
			    // first header row
			    PdfPCell cellh1 = new PdfPCell(new Phrase("Azioni"));
			    cellh1.setBackgroundColor(Color.CYAN);
			    cellh1.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(cellh1);
			    PdfPCell cellh5 = new PdfPCell(new Phrase("Indicatore previsto"));
			    cellh5.setBackgroundColor(Color.CYAN);
			    cellh5.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(cellh5);
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
			    table.addCell(cellh4);
			    PdfPCell cellh6 = new PdfPCell(new Phrase("Scadenza Apicale"));
			    cellh6.setBackgroundColor(Color.CYAN);
			    cellh6.setHorizontalAlignment(Element.ALIGN_CENTER);
			    table.addCell(cellh6);
			    
			    // itero sulle azioni
			    for (Azione act : obj.getAzioni()){
			    	PdfPCell cellr1 = new PdfPCell(new Phrase(act.getDescrizione()+" "+act.getNote()));
				    cellr1.setBackgroundColor(Color.LIGHT_GRAY);
				    cellr1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr1);
				    PdfPCell cellr5 = new PdfPCell(new Phrase(act.getIndicatore()));
				    cellr5.setBackgroundColor(Color.LIGHT_GRAY);
				    cellr5.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr5);
				    PdfPCell cellr2 = new PdfPCell(new Phrase(act.getProdotti()));
				    cellr2.setBackgroundColor(Color.LIGHT_GRAY);
				    cellr2.setHorizontalAlignment(Element.ALIGN_LEFT);
				    table.addCell(cellr2);
				    PdfPCell cellr3 = new PdfPCell(new Phrase(" - "));
				    if(null != act.getScadenza()){
				    	cellr3 = new PdfPCell(new Phrase(sdf.format(act.getScadenza())));
				    }
				    cellr3.setBackgroundColor(Color.LIGHT_GRAY);
				    cellr3.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellr3);
				    PdfPCell cellr4 = new PdfPCell(new Phrase(""+act.getPesoApicale()));
				    //if (act.getObiettivo().getStruttura().isDipartimento()){
				    //	cellr4 = new PdfPCell(new Phrase(""+act.getPeso()));
				    //}
				    
				    cellr4.setBackgroundColor(Color.LIGHT_GRAY);
				    cellr4.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellr4);
				    PdfPCell cellr6 = new PdfPCell(new Phrase(" - "));
				    if(null != act.getScadenzaApicale()){
				    	cellr6 = new PdfPCell(new Phrase(sdf.format(act.getScadenzaApicale())));
				    }
				    cellr6.setBackgroundColor(Color.LIGHT_GRAY);
				    cellr6.setHorizontalAlignment(Element.ALIGN_CENTER);
				    table.addCell(cellr6);
			    }
			    //
			    
			    doc.add(table);
			    
	    	}
	    	doc.add(new Paragraph("\n"));
	  
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
   


    
} // ---------------------------------------------
