package it.sicilia.regione.gekoddd.geko.valutazione.web.manager;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/dirigenteValPdf")
public class ManagerValutazionePdfController  {
	
	private static final Logger log = LoggerFactory.getLogger(ManagerValutazionePdfController.class);
	
	//
    @Autowired
    private ObiettivoQryService objServizi;
    @Autowired
    private AzioneAssegnazioneQryService azAssServizi;
    @Autowired
    private ValutazioneCompartoQryService valutazioneCompartoServizi;
    @Autowired
    private ValutazioneQryService valutazioneQryServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
    //
    public ManagerValutazionePdfController() { }
    
    //
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
    }
    
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
 
    
     
  //----------------------- Valutazione --------------------
    // 4.
    @RequestMapping(value="pdfValutazioneStrutturaManager/{anno}/{idIncarico}")
    public void pdfValutazioneStrutturaManager(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
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
		//
		/*
		 * genero il contenuto del documento  
		 */
		
		// estraggo le collections che mi servono 
		/*
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
    	final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	//log.info("responsabile =" +manager.stringa+"con IDIncarico="+incarico.idIncarico);
		//
        List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiGlobalByStrutturaIDAndAnno(incarico.pgID, anno); 
        listDipendenti.remove(manager);
        //
        Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap<PersonaFisicaGeko,List<AzioneAssegnazione>>();
        Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap<PersonaFisicaGeko,List<ValutazioneComparto>>();
        */
		log.info("pdfValutazioneStrutturaManager(");
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        final PersonaFisicaGeko manager = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
        
		List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiStrictByStrutturaIDAndAnno(incarico.pgID, anno);
		listDipendenti.remove(manager);
		//
		Map<PersonaFisicaGeko,List<AzioneAssegnazione>> mapDipendentiAssegnazioni = new LinkedHashMap();
		Map<PersonaFisicaGeko,List<ValutazioneComparto>> mapDipendentiValutazioneComparto = new LinkedHashMap();
		
		if (listDipendenti!= null && !listDipendenti.isEmpty()){
        for(PersonaFisicaGeko pf : listDipendenti){
        	log.info("pf =" +pf.stringa+"con ID"+pf.idPersona);
        	pf.setAnno(anno);
        	pf.setIncaricoValutazioneID(incarico.getIdIncarico());
        	//
        	// List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pfID, incaricoID, anno);
        	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
        	if (assegnazioni!=null && !assegnazioni.isEmpty()) {
        		pf.setAssegnazioni(assegnazioni);
        		mapDipendentiAssegnazioni.put(pf, assegnazioni);
        	}
        	
        	
        	//
        	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(pf.idPersona, incarico.idIncarico, anno);
        	if (valutazioni!=null && !valutazioni.isEmpty()) {
        		pf.setValutazioni(valutazioni);
        	    mapDipendentiValutazioneComparto.put(pf, valutazioni);
        	}
        }
		
		} // 
	    
		
		// scrivo sulla prima pagina il titolo del report con il nome della struttura
		Paragraph titolo = new Paragraph("Valutazione Personale Comparto per "+struttura.getDenominazione()+" - anno:"+anno);
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
			Paragraph val = new Paragraph("Valutazione Dipendente " + mapItem.getKey().getStringa());
			val.setAlignment(Element.ALIGN_CENTER);
			doc.add(val);
			doc.add(new Paragraph("\n"));
			
			// genero la tabella valutazione dei risultati ----------------------------------------------
			PdfPTable tableValResult = new PdfPTable(4);
			// define relative columns width p.96
			tableValResult.setWidths(new int[] {8,1,2,1});
			// first header row
			PdfPCell cell = new PdfPCell(new Phrase("VALUTAZIONE DEI RISULTATI - anno: "+anno));
		    cell.setColspan(5);
		    cell.setBackgroundColor(Color.CYAN);
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cell);
			// intestazione tabella
		    PdfPCell cellh1 = new PdfPCell(new Phrase("Obiettivo / Azione"));
	    	cellh1.setBackgroundColor(Color.LIGHT_GRAY);
		    cellh1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cellh1);
		    PdfPCell cellh2 = new PdfPCell(new Phrase("Peso "));
	    	cellh2.setBackgroundColor(Color.LIGHT_GRAY);
		    cellh2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellh2);
		    PdfPCell cellh3 = new PdfPCell(new Phrase("Valutazione"));
	    	cellh3.setBackgroundColor(Color.LIGHT_GRAY);
		    cellh3.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellh3);
		    PdfPCell cellh4 = new PdfPCell(new Phrase("Punteggio"));
	    	cellh4.setBackgroundColor(Color.LIGHT_GRAY);
		    cellh4.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellh4);
		    
		    //
		    // itero sulle assegnazioni
		    for (AzioneAssegnazione assegn : mapItem.getValue()){
		    	PdfPCell cellr1 = new PdfPCell(new Phrase(assegn.getAzione().getObiettivo().getDescrizione()+" / "
		    			+ assegn.getAzione().getDescrizione()));
		    	cellr1.setBackgroundColor(Color.WHITE);
			    cellr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValResult.addCell(cellr1);
			    PdfPCell cellr2 = new PdfPCell(new Phrase(""+assegn.getPeso()));
		    	cellr2.setBackgroundColor(Color.WHITE);
			    cellr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(cellr2);
			    PdfPCell cellr3 = new PdfPCell(new Phrase(""+assegn.getValutazione()));
		    	cellr3.setBackgroundColor(Color.WHITE);
			    cellr3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(cellr3);
			    PdfPCell cellr4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(assegn.getPunteggio())));
		    	cellr4.setBackgroundColor(Color.WHITE);
			    cellr4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValResult.addCell(cellr4);			    
		    }
		    // riga totale risultati
		    PdfPCell cellf1 = new PdfPCell(new Phrase("Totale risultati"));
	    	cellf1.setBackgroundColor(Color.LIGHT_GRAY);
		    cellf1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValResult.addCell(cellf1);
		    PdfPCell cellf2 = new PdfPCell(new Phrase(""+mapItem.getKey().getPesoAssegnazioni()));
	    	cellf2.setBackgroundColor(Color.LIGHT_GRAY);
		    cellf2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellf2);
		    PdfPCell cellf3 = new PdfPCell(new Phrase("-"));
	    	cellf3.setBackgroundColor(Color.LIGHT_GRAY);
		    cellf3.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellf3);
		    PdfPCell cellf4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(mapItem.getKey().getPunteggioAssegnazioni())));
	    	cellf4.setBackgroundColor(Color.LIGHT_GRAY);
		    cellf4.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValResult.addCell(cellf4);
		    // 
		    
		    //
		    doc.add(tableValResult);
		    doc.add(new Paragraph("\n"));
		    
		    
		 // itero sulle assegnazioni
			for (Map.Entry<PersonaFisicaGeko, List<ValutazioneComparto>> map2Item : mapDipendentiValutazioneComparto.entrySet()) {
		    if(map2Item.getKey().equals(mapItem.getKey())){
			// genero la tabella valutazione delle prestazioni ----------------------------------------
		    PdfPTable tableValPrest = new PdfPTable(4);
			// define relative columns width p.96
		    tableValPrest.setWidths(new int[] {8,1,2,1});
			// first header row
			PdfPCell cellb = new PdfPCell(new Phrase("VALUTAZIONE DEI COMPORTAMENTI ORGANIZZATIVI - anno: "+anno));
		    cellb.setColspan(4);
		    cellb.setBackgroundColor(Color.CYAN);
		    cellb.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValPrest.addCell(cellb);
		 // intestazione tabella
		    PdfPCell cellbh1 = new PdfPCell(new Phrase("Indicatore"));
	    	cellbh1.setBackgroundColor(Color.LIGHT_GRAY);
		    cellbh1.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableValPrest.addCell(cellbh1);
		    PdfPCell cellbh2 = new PdfPCell(new Phrase("Peso "));
	    	cellbh2.setBackgroundColor(Color.LIGHT_GRAY);
		    cellbh2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValPrest.addCell(cellbh2);
		    PdfPCell cellbh3 = new PdfPCell(new Phrase("Valutazione"));
	    	cellbh3.setBackgroundColor(Color.LIGHT_GRAY);
		    cellbh3.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValPrest.addCell(cellbh3);
		    PdfPCell cellbh4 = new PdfPCell(new Phrase("Punteggio"));
	    	cellbh4.setBackgroundColor(Color.LIGHT_GRAY);
		    cellbh4.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableValPrest.addCell(cellbh4);
		    
		    //
		 // itero sulle valutazioni
		    for (ValutazioneComparto valut : map2Item.getValue()){
		    	// 1.
		    	PdfPCell cell21r1 = new PdfPCell(new Phrase("Competenza nello svolgimento delle attivita'"));			  
		    	cell21r1.setBackgroundColor(Color.WHITE);
			    cell21r1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValPrest.addCell(cell21r1);   
			    //
			    PdfPCell cell21r2 = new PdfPCell(new Phrase(""+valut.getCompetSvolgAttivAss()));
		    	cell21r2.setBackgroundColor(Color.WHITE);
			    cell21r2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cell21r2);
			    PdfPCell cell21r3 = new PdfPCell(new Phrase(""+valut.getCompetSvolgAttivVal()));
		    	cell21r3.setBackgroundColor(Color.WHITE);
			    cell21r3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cell21r3);
			    PdfPCell cell21r4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valut.getCompetSvolgAttivPunteggio())));
		    	cell21r4.setBackgroundColor(Color.WHITE);
		    	cell21r4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cell21r4);
		    	// 2
			    PdfPCell adattcol1 = new PdfPCell(new Phrase("Capacita' di adattamento al contesto lavorativo"));			    
			    adattcol1.setBackgroundColor(Color.WHITE);
			    adattcol1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValPrest.addCell(adattcol1);  
			    //
			    PdfPCell adattcol2 = new PdfPCell(new Phrase(""+valut.getAdattContextLavAss()));
			    adattcol2.setBackgroundColor(Color.WHITE);
			    adattcol2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(adattcol2);
			    PdfPCell adattcol3 = new PdfPCell(new Phrase(""+valut.getAdattContextLavVal()));
			    adattcol3.setBackgroundColor(Color.WHITE);
			    adattcol3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(adattcol3);
			    PdfPCell adattcol4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valut.getAdattContextLavPunteggio())));
			    adattcol4.setBackgroundColor(Color.WHITE);
			    adattcol4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(adattcol4);
			    
			    // 3
			    PdfPCell cell23r1 = new PdfPCell(new Phrase("Capacita' di assolvere ai compiti assegnati"));			    
		    	cell23r1.setBackgroundColor(Color.WHITE);
			    cell23r1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValPrest.addCell(cell23r1);   
			    //
			    PdfPCell cell23r2 = new PdfPCell(new Phrase(""+valut.getCapacAssolvCompitiAss()));
		    	cell23r2.setBackgroundColor(Color.WHITE);
			    cell23r2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cell23r2);
			    PdfPCell cell23r3 = new PdfPCell(new Phrase(""+valut.getCapacAssolvCompitiVal()));
		    	cell23r3.setBackgroundColor(Color.WHITE);
			    cell23r3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cell23r3);
			    PdfPCell cell23r4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valut.getCapacAssolvCompitiPunteggio())));
		    	cell23r4.setBackgroundColor(Color.WHITE);
		    	cell23r4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cell23r4);
			    
			    // 
			    
			    if(!map2Item.getKey().isCatAB()){
				    PdfPCell cell24r1 = new PdfPCell(new Phrase("Capacita' di promuovere e gestire l'innovazione"));				    
			    	cell24r1.setBackgroundColor(Color.WHITE);
				    cell24r1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    tableValPrest.addCell(cell24r1);   
				    //
				    PdfPCell cell24r2 = new PdfPCell(new Phrase(""+valut.getInnovazAss()));
			    	cell24r2.setBackgroundColor(Color.WHITE);
				    cell24r2.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableValPrest.addCell(cell24r2);
				    PdfPCell cell24r3 = new PdfPCell(new Phrase(""+valut.getInnovazVal()));
			    	cell24r3.setBackgroundColor(Color.WHITE);
				    cell24r3.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableValPrest.addCell(cell24r3);
				    PdfPCell cell24r4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valut.getInnovazPunteggio())));
			    	cell24r4.setBackgroundColor(Color.WHITE);
			    	cell24r4.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableValPrest.addCell(cell24r4);		    
				    
				    //
				    PdfPCell cell25r1 = new PdfPCell(new Phrase("Capacita' di organizzazione del lavoro"));				    
			    	cell25r1.setBackgroundColor(Color.WHITE);
				    cell25r1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    tableValPrest.addCell(cell25r1);   
				    //
				    PdfPCell cell25r2 = new PdfPCell(new Phrase(""+valut.getOrgLavAss()));
			    	cell25r2.setBackgroundColor(Color.WHITE);
				    cell25r2.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableValPrest.addCell(cell25r2);
				    PdfPCell cell25r3 = new PdfPCell(new Phrase(""+valut.getOrgLavVal()));
			    	cell25r3.setBackgroundColor(Color.WHITE);
				    cell25r3.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableValPrest.addCell(cell25r3);
				    PdfPCell cell25r4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valut.getOrgLavPunteggio())));
			    	cell25r4.setBackgroundColor(Color.WHITE);
			    	cell25r4.setHorizontalAlignment(Element.ALIGN_CENTER);
				    tableValPrest.addCell(cell25r4);
				   
			    }
			    
			    
			 // riga totale prestazioni
			    PdfPCell cellfp1 = new PdfPCell(new Phrase("Totale Prestazioni"));
		    	cellfp1.setBackgroundColor(Color.LIGHT_GRAY);
			    cellfp1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValPrest.addCell(cellfp1);
			    PdfPCell cellfp2 = new PdfPCell(new Phrase(""+valut.getTotPeso()));
		    	cellfp2.setBackgroundColor(Color.LIGHT_GRAY);
			    cellfp2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cellfp2);
			    PdfPCell cellfp3 = new PdfPCell(new Phrase("-"));
		    	cellfp3.setBackgroundColor(Color.LIGHT_GRAY);
			    cellfp3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cellfp3);
			    PdfPCell cellfp4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valut.getTotPunteggio())));
		    	cellfp4.setBackgroundColor(Color.LIGHT_GRAY);
			    cellfp4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValPrest.addCell(cellfp4);
			    // 
			   
			    doc.add(tableValPrest);
			    doc.add(new Paragraph("\n"));
			    
			    // genero la tabella valutazioni finali ---------------------------------------------------
			    PdfPTable tableValFinal = new PdfPTable(4);
				// define relative columns width p.96
			    tableValFinal.setWidths(new int[] {8,1,2,1});
				// first header row
				PdfPCell cellc = new PdfPCell(new Phrase("VALUTAZIONE FINALE - anno: "+anno));
			    cellc.setColspan(4);
			    cellc.setBackgroundColor(Color.CYAN);
			    cellc.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValFinal.addCell(cellc);
			    //
			    // riga totale generale
			    PdfPCell cellft1 = new PdfPCell(new Phrase("Totale Generale"));
		    	cellft1.setBackgroundColor(Color.LIGHT_GRAY);
			    cellft1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValFinal.addCell(cellft1);
			    PdfPCell cellft2 = new PdfPCell(new Phrase(""+(mapItem.getKey().getPesoAssegnazioni()+valut.getTotPeso())));
		    	cellft2.setBackgroundColor(Color.LIGHT_GRAY);
			    cellft2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValFinal.addCell(cellft2);
			    PdfPCell cellft3 = new PdfPCell(new Phrase("-"));
		    	cellft3.setBackgroundColor(Color.LIGHT_GRAY);
			    cellft3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValFinal.addCell(cellft3);
			    double d = mapItem.getKey().getPunteggioAssegnazioni()+valut.getTotPunteggio();
			    // new DecimalFormat("#.##").format(
			    PdfPCell cellft4 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(d)));
		    	cellft4.setBackgroundColor(Color.LIGHT_GRAY);
			    cellft4.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValFinal.addCell(cellft4);
			    
		    	
			    // genero le firme
			    // riga firma
			    PdfPCell cellfdirig = new PdfPCell(new Phrase("  Data    Dirigente Responsabile "
			    +manager.getNome()+" "
			    +manager.getCognome()
			    ));
			    //cellfdirig.setColspan(2);
			    cellfdirig.setBackgroundColor(Color.LIGHT_GRAY);
			    cellfdirig.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValFinal.addCell(cellfdirig);
			    PdfPCell cellfdip= new PdfPCell(new Phrase("Dipendente "
			    		+mapItem.getKey().getNome()+" "
			    		+mapItem.getKey().getCognome()
			    		));
			    cellfdip.setColspan(3);
			    cellfdip.setBackgroundColor(Color.LIGHT_GRAY);
			    cellfdip.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValFinal.addCell(cellfdip);
			    // riga _________ 
			    PdfPCell cell_fdirig = new PdfPCell(new Phrase("                                                     "
			    +"                                                            "
			    +"__/__/__  __________________________________ "));
			    //cell_fdirig.setColspan(2);
			    cell_fdirig.setBackgroundColor(Color.WHITE);
			    cell_fdirig.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValFinal.addCell(cell_fdirig);
			    PdfPCell cell_fdip= new PdfPCell(new Phrase("                                          " +
			    		"___________________________ "	));
			    cell_fdip.setColspan(3);
			    cell_fdip.setBackgroundColor(Color.WHITE);
			    cell_fdip.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValFinal.addCell(cell_fdip);
			    // 
			    doc.add(tableValFinal);
			    doc.add(new Paragraph("\n"));
			    
		    }
		    
		    //
			
		    
		    }
			}
			
			
			// genero le firme
		
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
    
    
    // -------------------
    // pdf valutazionidipartimento per anno
    @RequestMapping(value="pdfValutazionePopManager/{anno}/{idIncarico}")
    public void pdfValutazionePopManagerController(@PathVariable("anno") int anno, @PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// cfr iText in Action p.285 e 289
    	try {
    		String text = request.getParameter("text");
    		if (text == null || text.trim().length() == 0 ) {
    			text = "You didn't enter any text. ";
    		}
    		
		tableBackground = new TableBackground();
        cellBackground = new CellBackground();
        roundRectangle = new RoundRectangle(new int[]{ 0xFF, 0x00, 0xFF, 0x00 });
        whiteRectangle = new RoundRectangle(new int[]{ 0x00, 0x00, 0x00, 0x00 });
        
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
     		
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
    		
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
		
		// estraggo le collections che mi servono   
		// final IncaricoGeko incaricoDept = fromOrganikoServizi.findIncaricoById(idIncarico);
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(idIncarico);
        final PersonaGiuridicaGeko struttura = fromOrganikoServizi.findPersonaGiuridicaById(incarico.getPgID());
        List<IncaricoGeko> lstIncPop = fromOrganikoServizi.findIncarichiPopByIntermediaIDAndAnno(struttura.getIdPersona(),anno);
        //
        if(!lstIncPop.equals(null) && !lstIncPop.isEmpty()) {
     	   for (IncaricoGeko incPop : lstIncPop) {
	   	       if (null != incPop) {
	   	    	   final List<Obiettivo> listObiettivi = objServizi.findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(incPop.getIdIncarico(), anno);
	   	    	   final List<Valutazione> lstValutPop = valutazioneQryServizi.findByIncaricoIDAndAnno(incPop.getIdIncarico(), anno); // una sola in realt�        	
	   	    	   //	
	   	    	   for (Obiettivo obj : listObiettivi){
	   	    		obj.setIncarico(incPop);
	   	    		obj.setIncaricoPadreID(idIncarico);
	   	    	   }		    	
		        	incPop.setObiettivi(listObiettivi);
		        	//	
		        	if (lstValutPop!=null && !lstValutPop.isEmpty()) {
		            	incPop.setValutazioni(lstValutPop);
		            }              	       
	   	       } // end if
     	   } // end for 
        }// end if lst empty   
        //
    	
    	// scrivo sulla prima pagina il titolo del report con il nome della struttura
		Paragraph titoloApic = new Paragraph("Valutazione Incarichi POP per "+struttura.getDenominazione()+" - anno:"+anno,fontTitle1);
		doc.add(titoloApic);
		// Riga vuota
	    doc.add(new Paragraph("\n"));
		Paragraph titolo2 = new Paragraph("Responsabile "+incarico.getResponsabile(),fontTitle2);
		doc.add(titolo2);
    	
    	
    	// itero sugli incarichi dipartimentali
    	for (IncaricoGeko inc : lstIncPop){
    		
        	// salto-pagina
    		doc.newPage();
			Paragraph titolo = new Paragraph("VALUTAZIONE",fontTitle1);
			titolo.setAlignment("CENTER");
	     	doc.add(titolo);
	     	// Riga vuota
		    doc.add(new Paragraph("\n"));
		    // scrivo sulla prima pagina il sottotitolo del report
		 	Paragraph subTitolo = new Paragraph(inc.denominazioneStruttura+" - anno "+anno,fontTitle2);
		 	subTitolo.setAlignment("CENTER");
		    doc.add(subTitolo);
		    	   
		    // titolo 2 responsabile: P:M.
		    Paragraph titolo3;
		    if (null != incarico.responsabile) {
			    titolo3 = new Paragraph(""+inc.responsabile);
		    } else{
		    	titolo3 = new Paragraph("RESPONSABILE DA INSERIRE !!! (manca incarico");
		    }
		    titolo3.setAlignment("CENTER");
	     	doc.add(titolo3);
	     	
	     	Paragraph titolo4;
		    if (null != inc.responsabile) {
			    titolo4 = new Paragraph("da: "+inc.getStrDataInizio()+" a: "+inc.getStrDataFine());
			    titolo4.setAlignment("CENTER");
		     	doc.add(titolo4);
		    } 
		    
	     	// Riga vuota
		    doc.add(new Paragraph("\n"));
		   
		    // salto-pagina
			doc.newPage();
			
			
			// verifico se lista vuota
		    if (inc.getObiettivi().isEmpty()){
		    	Paragraph empty = new Paragraph("Nessun Obiettivo trovato per "+inc.denominazioneStruttura+" - anno:"+anno);
		     	doc.add(empty);
		     	doc.add(new Paragraph("\n"));
		    }
	    	// scheda 1
		    else {
		    	this.generaSchedaValutazObiettiviPop(doc, inc, incarico,anno);
		    	//this.addFirme(doc,incaricoApic,inc);
		    	this.generaSchedeValutazDettaglioObiettiviPop(doc, inc, incarico,anno);
			    //this.addFirme(doc,incaricoApic,inc);
		    }
		
			
			
		} // fine for incarico
	   
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
    } // fine metodo -----------------------------------------------------------------------------------
    
 
    // ***************** metodi privati *****************
    // -------------------------------------------------------------------------------------
    private void generaSchedaValutazObiettiviPop(Document doc, IncaricoGeko inc, IncaricoGeko incaricoApicale, int anno){
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
    	PdfPCell cellriga1 = new PdfPCell(new Paragraph("Scheda di valutazione individuale -  Anno "+anno,fontTitle3));
    	tableProgr.addCell(cellriga1);   
    	PdfPCell cellriga2 = new PdfPCell(new Paragraph("Responsabile POP"));
    	tableProgr.addCell(cellriga2);   
    	
    	//tableStru.setTableEvent(tableBackground);
    	//tableStru.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	//tableStru.getDefaultCell().setCellEvent(whiteRectangle);
    	PdfPCell cellStru = new PdfPCell(new Paragraph("STRUTTURA: "+inc.denominazioneStruttura));
    	tableProgr.addCell(cellStru);
    	String nomeResponsabile="";
    	if(inc.responsabile != null){
    		nomeResponsabile = inc.responsabile;
    	}
    	PdfPCell cellResp = new PdfPCell(new Paragraph("TITOLARE POP: "+nomeResponsabile));
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
    	PdfPTable tableSk1 = new PdfPTable(8);
    	try {
			tableSk1.setWidths(new int[] {1,5,2,2,2,1,1,1});
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
	    PdfPCell thObj = new PdfPCell(new Phrase("Descrizione sintetica obiettivo"));
	    thObj.setBackgroundColor(Color.LIGHT_GRAY);
	    thObj.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thObj);
	    
	    //
	    PdfPCell thIndic = new PdfPCell(new Phrase("Indicatore"));
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
	    PdfPCell thPeso = new PdfPCell(new Phrase("Peso"));
	    thPeso.setBackgroundColor(Color.LIGHT_GRAY);
	    thPeso.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thPeso);
	    //
	    PdfPCell thRis = new PdfPCell(new Phrase("Risultati"));
	    thRis.setBackgroundColor(Color.LIGHT_GRAY);
	    thRis.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thRis);
	    //
	    PdfPCell thPunteggio = new PdfPCell(new Phrase("Punteggio"));
	    thPunteggio.setBackgroundColor(Color.LIGHT_GRAY);
	    thPunteggio.setHorizontalAlignment(Element.ALIGN_LEFT);
	    tableSk1.addCell(thPunteggio);
    	
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
		    // righe descriz e note
		    PdfPCell cellobj;
		    cellobj = new PdfPCell(new Paragraph(obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote()));		
		    cellobj.setBackgroundColor(Color.WHITE);
		    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableSk1.addCell(cellobj);
		    
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
		 // risultato ???
		    float ris = obj.getPunteggio()/(float)obj.getPeso();
		    PdfPCell cellRis = new PdfPCell(new Phrase(""+ris));		    		    
			tableSk1.addCell(cellRis);					    
		    // punteggio
		    PdfPCell cellPunteggio = new PdfPCell(new Paragraph(""+new DecimalFormat("#.##").format(obj.getPunteggio())));			
		    cellPunteggio.setBackgroundColor(Color.WHITE);
		    cellPunteggio.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableSk1.addCell(cellPunteggio);
		    //
		    
    	} // fine loop obiettivi
    	try {
			doc.add(tableSk1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// riga Totale performance operativa
    	PdfPTable tabletpo = new PdfPTable(3);
    	try {
			tabletpo.setWidths(new int[] {4,1,1});
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    PdfPCell cellatpo = new PdfPCell(new Phrase("Totale performance operativa"));
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
	    PdfPCell cellatpo3 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(inc.getTotPunteggioObiettivi())));
	    cellatpo3.setBackgroundColor(Color.WHITE);
	    cellatpo3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    tabletpo.addCell(cellatpo3);
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
			    PdfPCell cellah2 = new PdfPCell(new Phrase("QUALITA' GESTIONALI-RELAZIONALI "));
		    	cellah2.setBackgroundColor(Color.LIGHT_GRAY);
			    cellah2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellah2);
			    PdfPCell cellah3 = new PdfPCell(new Phrase("Totale conseguibile"));
		    	cellah3.setBackgroundColor(Color.LIGHT_GRAY);
			    cellah3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellah3);
			    PdfPCell cellah4 = new PdfPCell(new Phrase("Punteggio"));
		    	cellah4.setBackgroundColor(Color.LIGHT_GRAY);
			    cellah4.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellah4);
				//
			    
			    // riga Analisi e Programmazione
			    PdfPCell cellaapr1 = new PdfPCell(new Phrase("Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione"));
			    cellaapr1.setBackgroundColor(Color.WHITE);
			    cellaapr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellaapr1);
			    PdfPCell cellaapr2 = new PdfPCell(new Phrase(""+valutazione.getAnalProgrAss()));
			    cellaapr2.setBackgroundColor(Color.WHITE);
			    cellaapr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellaapr2);
			    PdfPCell cellaapr3 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valutazione.getAnalPunteggio())));
			    cellaapr3.setBackgroundColor(Color.WHITE);
			    cellaapr3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellaapr3);
			    // riga Relazione e Coordinamento
			    PdfPCell cellarcr1 = new PdfPCell(new Phrase("Capacità di valorizzare competenze e attitudini dei propri collaboratori"));
			    cellarcr1.setBackgroundColor(Color.WHITE);
			    cellarcr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellarcr1);
			    PdfPCell cellarcr2 = new PdfPCell(new Phrase(""+valutazione.getRelazCoordAss()));
			    cellarcr2.setBackgroundColor(Color.WHITE);
			    cellarcr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellarcr2);
			    PdfPCell cellarcr3 = new PdfPCell(new Phrase(""+valutazione.getRelazPunteggio()));
			    cellarcr3.setBackgroundColor(Color.WHITE);
			    cellarcr3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellarcr3);
			 // riga priorità
			    PdfPCell cellarr1 = new PdfPCell(new Phrase("Capacità di individuazione del livello di priorità degli interventi da realizzare"));
			    cellarr1.setBackgroundColor(Color.WHITE);
			    cellarr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableValutaz.addCell(cellarr1);
			    PdfPCell cellar2 = new PdfPCell(new Phrase(""+valutazione.getPdlAss()));
			    cellar2.setBackgroundColor(Color.WHITE);
			    cellar2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableValutaz.addCell(cellar2);
			    PdfPCell cellar3 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valutazione.getPdlPunteggio())));
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
			    PdfPTable tableSintesi = new PdfPTable(3);
				// define relative columns width p.96
			    try {
					tableSintesi.setWidths(new int[] {4,1,1});
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    // intestazione tabella valutazione
			    PdfPCell cellash1 = new PdfPCell(new Phrase("TABELLA RIASSUNTIVA"));
		    	cellash1.setBackgroundColor(Color.LIGHT_GRAY);
			    cellash1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    cellash1.setColspan(3);
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
			    PdfPCell cellaopr3 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(inc.getTotPunteggioObiettivi())));
			    cellaopr3.setBackgroundColor(Color.WHITE);
			    cellaopr3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellaopr3);
			    
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
			    //
			    float totCompOrgPunt = valutazione.getAnalPunteggio()+
			    		valutazione.getRelazPunteggio()+
			    		valutazione.getGestPunteggio()+
			    		valutazione.getPdlPunteggio();
			    PdfPCell cellacor3 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(totCompOrgPunt)));
			    cellacor3.setBackgroundColor(Color.WHITE);
			    cellacor3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellacor3);
			    // riga Totale Complessivo
			    PdfPCell cellatcr1 = new PdfPCell(new Phrase("Totale Complessivo"));
			    cellatcr1.setBackgroundColor(Color.WHITE);
			    cellatcr1.setHorizontalAlignment(Element.ALIGN_LEFT);
			    tableSintesi.addCell(cellatcr1);
			    PdfPCell cellatcr2 = new PdfPCell(new Phrase(""+valutazione.getTotPeso()));
			    cellatcr2.setBackgroundColor(Color.WHITE);
			    cellatcr2.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellatcr2);
			    PdfPCell cellatcr3 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(valutazione.getTotPunteggio())));
			    cellatcr3.setBackgroundColor(Color.WHITE);
			    cellatcr3.setHorizontalAlignment(Element.ALIGN_CENTER);
			    tableSintesi.addCell(cellatcr3);
			    
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
		this.addFirme(doc, "IL DIRIGENTE","IL RESPONSABILE POP",incaricoApicale.getResponsabile(),inc.getResponsabile());
    } // fine metodo generaSchedaObiettivi
    
    // -----------------------------------------------------
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
 	    PdfPCell cellData = new PdfPCell(new Phrase("___/___/___"));
 	    cellData.setBackgroundColor(Color.WHITE);
 	    cellData.setHorizontalAlignment(Element.ALIGN_CENTER);
 	    cellData.setVerticalAlignment(Element.ALIGN_BOTTOM);
 	    cellData.setFixedHeight(60.0f);
 	    cellData.setBorderColor(Color.WHITE);
 	    tableFirma.addCell(cellData);
 	    PdfPCell cellfdirig = new PdfPCell(new Phrase(""+"_________________________________"));
 	    cellfdirig.setBackgroundColor(Color.WHITE);
 	    cellfdirig.setHorizontalAlignment(Element.ALIGN_CENTER);
 	    cellfdirig.setVerticalAlignment(Element.ALIGN_BOTTOM);
 	    cellfdirig.setFixedHeight(60.0f);
 	    cellfdirig.setBorderColor(Color.WHITE);
 	    tableFirma.addCell(cellfdirig);
 	    PdfPCell cellfdip= new PdfPCell(new Phrase("__________________________________"));
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
     private void generaSchedeValutazDettaglioObiettiviPop(Document doc, IncaricoGeko inc, IncaricoGeko incaricoApicale, int anno){
     	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
     	int nordObj = 0;
     	for(Obiettivo obj : inc.getObiettivi()){
     		//
     		doc.newPage();
     		// creo la scheda di programmazione 2 con tabella azioni / prodotti /scadenze
     		PdfPTable tableTitolo = new PdfPTable(1);
     		// righe descriz e note
 		    PdfPCell cellsk2 = new PdfPCell(new Paragraph("Scheda valutazione 2: dettaglio delle azioni correlate agli obiettivi assegnati"));		
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
 	    	PdfPCell cellResp2 = new PdfPCell(new Paragraph("DIRIGENTE: "+nomeDirig2));
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
 		    PdfPTable table = new PdfPTable(8);
 		    // define relative columns width p.96
 		    try {
 				table.setWidths(new int[] {1,4,1,1,1,1,2,1});
 			} catch (DocumentException e1) {
 				// TODO Auto-generated catch block
 				e1.printStackTrace();
 			}					 
 		    //
 		    PdfPCell cellobj;
 	    	cellobj = new PdfPCell(new Paragraph("Descrizione obiettivo:"+obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote()));		
 		    cellobj.setBackgroundColor(Color.WHITE);
 		    cellobj.setHorizontalAlignment(Element.ALIGN_LEFT);
 		    cellobj.setColspan(8);
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
 		    //
 		    PdfPCell thGradoAct = new PdfPCell(new Phrase("Grado"));
 		    thGradoAct.setBackgroundColor(Color.LIGHT_GRAY);
 		    thGradoAct.setHorizontalAlignment(Element.ALIGN_LEFT);
 		    table.addCell(thGradoAct);
 		    //
 		    PdfPCell thPunteggioAct = new PdfPCell(new Phrase("Punteggio"));
 		    thPunteggioAct.setBackgroundColor(Color.LIGHT_GRAY);
 		    thPunteggioAct.setHorizontalAlignment(Element.ALIGN_LEFT);
 		    table.addCell(thPunteggioAct);
 		    
 		    
 		   
 		    
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
 			    PdfPCell cellr5 = new PdfPCell(new Phrase(""+act.getCompletamento()));
 			    cellr5.setBackgroundColor(Color.WHITE);
 			    cellr5.setHorizontalAlignment(Element.ALIGN_LEFT);
 			    table.addCell(cellr5);
 			    PdfPCell cellr6 = new PdfPCell(new Phrase(""+new DecimalFormat("#.##").format(act.getPunteggio())));
 			    cellr6.setBackgroundColor(Color.WHITE);
 			    cellr6.setHorizontalAlignment(Element.ALIGN_LEFT);
 			    table.addCell(cellr6);
 			    
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
 		    table.addCell(cellVuota);
 		    try {
 				doc.add(table);
 			} catch (DocumentException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		    this.addFirme(doc, "IL DIRIGENTE","IL RESPONSABILE POP",incaricoApicale.getResponsabile(),inc.getResponsabile());
     	} // fine for obiettivi    	
     }
     
    
} // ---------------------------------------------
