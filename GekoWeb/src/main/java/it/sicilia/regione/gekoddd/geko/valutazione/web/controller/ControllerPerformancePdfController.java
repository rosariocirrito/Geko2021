package it.sicilia.regione.gekoddd.geko.valutazione.web.controller;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
//import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/controllerPerformancePdf")
public class ControllerPerformancePdfController  {
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
    @Autowired
    private ValutazioneQryService valutazioneDirigServizi;
    
    private Log log = LogFactory.getLog(ControllerPerformancePdfController.class);
    
    
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
 
    } // TableBackground end
 
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
    } // CellBackground end
 
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
    } // RoundRectangle end
 
    // ---------------------------- metodi -----------------------------------------
    
    //
    public ControllerPerformancePdfController() { }
    
    
    // pdf valutazioni per anno
    @RequestMapping(value="form/{anno}/{idIncarico}")
    public void form(@PathVariable("anno") int anno,  @PathVariable("idIncarico") int idIncarico,
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
    		
    	Document doc = new Document(PageSize.A4.rotate());
    	//Document doc = new Document(PageSize.A3);
	    // L'oggetto baosPDF conterr� i caratteri che costituiscono il file PDF crea il pdf in memoria
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;
	    // Crea l'associazione tra l'oggetto di tipo ByteArrayOutputStream che rappresenta il PDF e il documento
	    // Ritorna un oggetto di tipo PdfWriter
	    docWriter = PdfWriter.getInstance(doc, baos);
		// Apro il documento
		doc.open();
		//
		
		int numeroDir1 =0;
		int numeroDir2 =0;
		int numeroDir3 =0;
		//
		int numeroDir10 =0;
		int numeroDir20 =0;
		int numeroDir30 =0;
		
		
		int numeroDir170 =0;
		int numeroDir270 =0;
		int numeroDir370 =0;
		
		
		int numeroDir176 =0;
		int numeroDir276 =0;
		int numeroDir376 =0;
		
		int numeroDir181 =0;
		int numeroDir281 =0;
		int numeroDir381 =0;
		//
		
		int numeroDir191 =0;
		int numeroDir291 =0;
		int numeroDir391 =0;
		
		int numeroDir1100 =0;
		int numeroDir2100 =0;
		int numeroDir3100 =0;
		
		int numeroDip0 =0;
		int numeroDipA =0;
		int numeroDipB =0;
		int numeroDipC =0;
		int numeroDipD =0;
		int numeroDipA0 =0;
		int numeroDipB0 =0;
		int numeroDipC0 =0;
		int numeroDipD0 =0;
		//
		int numeroDipA80 =0;
		int numeroDipB80 =0;
		int numeroDipC80 =0;
		int numeroDipD80 =0;
		//
		int numeroDipA90 =0;
		int numeroDipB90 =0;
		int numeroDipC90 =0;
		int numeroDipD90 =0;
		//
		int numeroDipA100 =0;
		int numeroDipB100 =0;
		int numeroDipC100 =0;
		int numeroDipD100 =0;
		
		PdfPTable tableDettaglio = new PdfPTable(2);
	    tableDettaglio.setWidths(new int[] {4,1});
	    
	     
		// 1.
		final IncaricoGeko incaricoApicale = fromOrganikoServizi.findIncaricoById(idIncarico);
		// 2. 
		final PersonaGiuridicaGeko dept = fromOrganikoServizi.findPersonaGiuridicaById(incaricoApicale.pgID);
		final List<PersonaFisicaGeko> dipDept = fromOrganikoServizi.findDipendentiByDipartimentoIDAndAnno(dept.getIdPersona(),  anno);
		
		
		Map<Integer, PunteggioDipendente> mapDipPunt = new LinkedHashMap();   
		Map<Integer, Integer> mapDipNrValut = new LinkedHashMap(); 
		for (PersonaFisicaGeko dip: dipDept){
			mapDipPunt.put(dip.getIdPersona(),new PunteggioDipendente(dip,0.0));
			mapDipNrValut.put(dip.getIdPersona(),0);
		}
		
    	final List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incaricoApicale.pgID, anno);
    	// mappe per il comparto
    	//
    	for (IncaricoGeko inc : listIncarichiDept){
    		double puntTot=0;
    		//
    		List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(inc.idIncarico, anno);
    		inc.setObiettivi(lstObjs);
    		puntTot += inc.getTotPunteggioObiettivi();
    		//
        	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(inc.idIncarico, anno); // una sola in realt�
        	inc.setValutazioni(lstValutDirig);
        	for(Valutazione valutazione : lstValutDirig){
        		puntTot += valutazione.getPdlPunteggio(); // riga priorità
        		puntTot += valutazione.getAnalPunteggio(); // riga Analisi e Programmazione
        		puntTot += valutazione.getRelazPunteggio(); // riga Relazione e Coordinamento
        		puntTot += valutazione.getGestPunteggio(); // riga Gestione
				
        	}
        	log.info(inc.responsabile+ " totale valutazione: "+puntTot);
        	
        	PersonaFisicaGeko dirig = fromOrganikoServizi.findPersonaFisicaById(inc.pfID);
        	dirig.setAnno(anno);
        	//
        	// fare qui la media
        	
        	double oldPunt = mapDipPunt.get(dirig.getIdPersona()).punt;
        	int nrPD = mapDipNrValut.get(dirig.getIdPersona())+1;
        	//
        	double mediaPunt = (oldPunt+puntTot)/nrPD;
        	mapDipPunt.put(dirig.getIdPersona(),new PunteggioDipendente(dirig,mediaPunt));
        	mapDipNrValut.put(dirig.getIdPersona(),nrPD);
        	
        	// sviluppiamo comparto
        	
        	// dipendenti della struttura di cui all'incarico
	        List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiGlobalByStrutturaIDAndAnno(inc.pgID, anno);//struttura.getDipendentiSub();
	        listDipendenti.remove(dirig);
	        //
	         // per ogni dipendente mappo l'assegnazione e la valutazione per quell'incarico e quell'anno
	        if(listDipendenti!=null && !listDipendenti.isEmpty()){
	       
	        for(PersonaFisicaGeko dip : listDipendenti){
	        	dip.setAnno(anno);
	        	dip.setIncaricoValutazioneID(inc.getIdIncarico());
	        	//
	        	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(dip.idPersona, inc.idIncarico, anno);   //.findByOpPersonaFisicaAndIncaricoAndAnno(pf, incarico, anno);
	        	if (assegnazioni!=null && !assegnazioni.isEmpty()) {
	        		dip.setAssegnazioni(assegnazioni);
	        		double puntDipTot=0;
		    		puntDipTot += dip.getPunteggioAssegnazioni();
		    		//
		        	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(dip.idPersona, inc.idIncarico, anno); //.findByOpPersonaFisicaAndIncaricoAndAnno(pf, incarico, anno);
		        	if (valutazioni!=null && !valutazioni.isEmpty()) {
		
		        		dip.setValutazioni(valutazioni);
		        		for (ValutazioneComparto valut : valutazioni){
		        			puntDipTot +=valut.getCompetSvolgAttivPunteggio();
		        			puntDipTot += valut.getAdattContextLavPunteggio();
		        			puntDipTot += valut.getCapacAssolvCompitiPunteggio();		        			
		        			puntDipTot += valut.getInnovazPunteggio();
		        			puntDipTot += valut.getOrgLavPunteggio();
		        		}
		        	}
		        	log.info(dip.stringa+ " totale valutazione: "+puntDipTot);
		        	// fare qui la media
		        	oldPunt = mapDipPunt.get(dip.getIdPersona()).punt;
		        	nrPD = mapDipNrValut.get(dip.getIdPersona())+1;
		        	//
		        	mediaPunt = (oldPunt+puntDipTot)/nrPD;
		        	mapDipPunt.put(dip.getIdPersona(),new PunteggioDipendente(dip,mediaPunt));
		        	mapDipNrValut.put(dip.getIdPersona(),nrPD);
		        	//
		        	//mapDipPunt.put(dip.getIdPersona(),new PunteggioDipendente(dip,puntDipTot));
	        	}
	        	
	    		
	    		
	        } // loop dipendenti comparto
	      
	      } // fine if se l'incarico ha dipendenti
	    }    // loop incarichi dipartimentali
	     
    	
    	// itero sulla mappa
    	/*
      	for (Map.Entry<String, String> entry : map.entrySet()) {
        	System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
    	}
    	*/
    	
    	for (Map.Entry<Integer, PunteggioDipendente> entry : mapDipPunt.entrySet()) {
    		PersonaFisicaGeko dip = entry.getValue().dip;
    		Double puntTot = entry.getValue().punt;
    		
    		if(dip.getCategoria().equals("A")) {
				numeroDipA++;
				if (puntTot <0.1) numeroDipA0++;
				else if (puntTot <80) numeroDipA80++;
				else if (puntTot <90) numeroDipA90++;
				else if (puntTot >=80) numeroDipA100++;
			}
		    else if(dip.getCategoria().equals("B")) {
		    	numeroDipB++;
		    	if (puntTot<0.1) numeroDipB0++;
		    	else if (puntTot <80) numeroDipB80++;
				else if (puntTot <90) numeroDipB90++;
				else if (puntTot >=80) numeroDipB100++;
		    }
		    else if(dip.getCategoria().equals("C")) {
		    	numeroDipC++;
		    	if (puntTot<0.1) numeroDipC0++;
		    	else if (puntTot <80) numeroDipC80++;
				else if (puntTot <90) numeroDipC90++;
				else if (puntTot >=80) numeroDipC100++;
		    }
		    else if(dip.getCategoria().equals("D")) {
		    	numeroDipD++;
		    	if ((puntTot <0.1)) numeroDipD0++;
		    	else if (puntTot <80) numeroDipD80++;
				else if (puntTot <90) numeroDipD90++;
				else if (puntTot >=80) numeroDipD100++;
		    }
		    else {
		    	numeroDip0++;
		    }
    		
    		if(dip.getCategoria().equals("F1")) {
				numeroDir1++;
				if ((puntTot<0.1)) numeroDir10++;
				else if (puntTot <70) numeroDir170++;
				else if (puntTot <76) numeroDir176++;
				else if (puntTot <81) numeroDir181++;
				else if (puntTot <91) numeroDir191++;
				else if (puntTot >=91) numeroDir1100++;
			}
			else if(dip.getCategoria().equals("F2")) { 
				numeroDir2++;
				if (puntTot <0.1) numeroDir20++;
				else if (puntTot <70) numeroDir270++;
				else if (puntTot <76) numeroDir276++;
				else if (puntTot <81) numeroDir281++;
				else if (puntTot <91) numeroDir291++;
				else if (puntTot >=91) numeroDir2100++;
				}
			else if(dip.getCategoria().equals("F3")) {
				numeroDir3++;
				if (puntTot<0.1) numeroDir30++;
				else if (puntTot <70) numeroDir370++;
				else if (puntTot <76) numeroDir376++;
				else if (puntTot <81) numeroDir381++;
				else if (puntTot <91) numeroDir391++;
				else if (puntTot >=91) numeroDir3100++;
			} // fine catena if dirigenti
    		
    		PdfPCell cellash2 = new PdfPCell(new Phrase(dip.stringa));
	    	cellash2.setBackgroundColor(Color.LIGHT_GRAY);
		    cellash2.setHorizontalAlignment(Element.ALIGN_LEFT);
		    tableDettaglio.addCell(cellash2);
		    
		    	
	    	PdfPCell cellash4;
	    	if (puntTot >0.1){
	    		cellash4 = new PdfPCell(new Phrase("punteggio: " +puntTot));
	    	}
	    	else {
	    		cellash4 = new PdfPCell(new Phrase(" - "));
	    	}
	    	cellash4.setBackgroundColor(Color.LIGHT_GRAY);
		    cellash4.setHorizontalAlignment(Element.ALIGN_CENTER);
		    tableDettaglio.addCell(cellash4);
    	}
    	
    	// scrivo sulla prima pagina il titolo del report con il nome della struttura
		//Paragraph titolo = new Paragraph("Valutazione Dirigenziali per "+incaricoApicale.getDenominazioneStruttura()+" - anno:"+anno,fontTitle1);
		Paragraph titolo = new Paragraph("Distribuzione valutazioni personale - anno:"+anno,fontTitle1);
		
		doc.add(titolo);
		// Riga vuota
	    doc.add(new Paragraph("\n")); 
	    
	    PdfPTable tableFinaleDir = new PdfPTable(6);
		tableFinaleDir.setWidths(new int[] {2,1,1,1,1,1});
		//
		PdfPCell cella11 = new PdfPCell(new Phrase("Numero Dirigenti 1: "+numeroDir1));
		tableFinaleDir.addCell(cella11);
		PdfPCell cella12 = new PdfPCell(new Phrase("<70: "+numeroDir170));
		tableFinaleDir.addCell(cella12);
		PdfPCell cella13 = new PdfPCell(new Phrase("<76: "+numeroDir176));
		tableFinaleDir.addCell(cella13);
		PdfPCell cella14 = new PdfPCell(new Phrase("<81: "+numeroDir181));
		tableFinaleDir.addCell(cella14);
		PdfPCell cella15 = new PdfPCell(new Phrase("<91: "+numeroDir191));
		tableFinaleDir.addCell(cella15);
		PdfPCell cella16 = new PdfPCell(new Phrase("<=100: "+numeroDir1100));
		tableFinaleDir.addCell(cella16);
		//
		PdfPCell cella21 = new PdfPCell(new Phrase("Numero Dirigenti 2: "+numeroDir2));
		tableFinaleDir.addCell(cella21);
		PdfPCell cella22 = new PdfPCell(new Phrase("<70: "+numeroDir270));
		tableFinaleDir.addCell(cella22);
		PdfPCell cella23 = new PdfPCell(new Phrase("<76: "+numeroDir276));
		tableFinaleDir.addCell(cella23);
		PdfPCell cella24 = new PdfPCell(new Phrase("<81: "+numeroDir281));
		tableFinaleDir.addCell(cella24);
		PdfPCell cella25 = new PdfPCell(new Phrase("<91: "+numeroDir291));
		tableFinaleDir.addCell(cella25);
		PdfPCell cella26 = new PdfPCell(new Phrase("<=100: "+numeroDir2100));
		tableFinaleDir.addCell(cella26);
		//
		PdfPCell cella31 = new PdfPCell(new Phrase("Numero Dirigenti 3: "+numeroDir3));
		tableFinaleDir.addCell(cella31);
		PdfPCell cella32 = new PdfPCell(new Phrase("<70: "+numeroDir370));
		tableFinaleDir.addCell(cella32);
		PdfPCell cella33 = new PdfPCell(new Phrase("<76: "+numeroDir376));
		tableFinaleDir.addCell(cella33);
		PdfPCell cella34 = new PdfPCell(new Phrase("<81: "+numeroDir381));
		tableFinaleDir.addCell(cella34);
		PdfPCell cella35 = new PdfPCell(new Phrase("<91: "+numeroDir391));
		tableFinaleDir.addCell(cella35);
		PdfPCell cella36 = new PdfPCell(new Phrase("<=100: "+numeroDir3100));
		tableFinaleDir.addCell(cella36);
		//
		doc.add(tableFinaleDir);
		doc.add(new Paragraph("\n")); 
    	
		PdfPTable tableFinaleComparto = new PdfPTable(5);
		tableFinaleComparto.setWidths(new int[] {2,1,1,1,1});
		//
		PdfPCell cella41 = new PdfPCell(new Phrase("Numero Dipendenti A: "+numeroDipA));
		tableFinaleComparto.addCell(cella41);
		PdfPCell cella42 = new PdfPCell(new Phrase("<80: "+numeroDipA80));
		tableFinaleComparto.addCell(cella42);
		PdfPCell cella43 = new PdfPCell(new Phrase("80-90:"+numeroDipA90));
		tableFinaleComparto.addCell(cella43);
		PdfPCell cella44 = new PdfPCell(new Phrase("90-100:"+numeroDipA100));
		tableFinaleComparto.addCell(cella44);
		PdfPCell cella45 = new PdfPCell(new Phrase(" n.v. :"+numeroDipA0));
		tableFinaleComparto.addCell(cella45);
		//
		PdfPCell cella51 = new PdfPCell(new Phrase("Numero Dipendenti B:"+numeroDipB));
		tableFinaleComparto.addCell(cella51);
		PdfPCell cella52 = new PdfPCell(new Phrase("<80: "+numeroDipB80));
		tableFinaleComparto.addCell(cella52);
		PdfPCell cella53 = new PdfPCell(new Phrase("80-90:"+numeroDipB90));
		tableFinaleComparto.addCell(cella53);
		PdfPCell cella54 = new PdfPCell(new Phrase("90-100:"+numeroDipB100));
		tableFinaleComparto.addCell(cella54);
		PdfPCell cella55 = new PdfPCell(new Phrase(" n.v. :"+numeroDipB0));
		tableFinaleComparto.addCell(cella55);
		//
		PdfPCell cella61 = new PdfPCell(new Phrase("Numero Dipendenti C:"+numeroDipC));
		tableFinaleComparto.addCell(cella61);
		PdfPCell cella62 = new PdfPCell(new Phrase("<80: "+numeroDipC80));
		tableFinaleComparto.addCell(cella62);
		PdfPCell cella63 = new PdfPCell(new Phrase("80-90:"+numeroDipC90));
		tableFinaleComparto.addCell(cella63);
		PdfPCell cella64 = new PdfPCell(new Phrase("90-100:"+numeroDipC100));
		tableFinaleComparto.addCell(cella64);
		PdfPCell cella65 = new PdfPCell(new Phrase(" n.v. :"+numeroDipC0));
		tableFinaleComparto.addCell(cella65);
		
		PdfPCell cella71 = new PdfPCell(new Phrase("Numero Dipendenti D:"+numeroDipD));
		tableFinaleComparto.addCell(cella71);
		PdfPCell cella72 = new PdfPCell(new Phrase("<80: "+numeroDipD80));
		tableFinaleComparto.addCell(cella72);
		PdfPCell cella73 = new PdfPCell(new Phrase("80-90:"+numeroDipD90));
		tableFinaleComparto.addCell(cella73);
		PdfPCell cella74 = new PdfPCell(new Phrase("90-100:"+numeroDipD100));
		tableFinaleComparto.addCell(cella74);
		PdfPCell cella75 = new PdfPCell(new Phrase(" n.v. :"+numeroDipD0));
		tableFinaleComparto.addCell(cella75);
		//
		doc.add(tableFinaleComparto);
		
		doc.add(new Paragraph("\n"));
		
		
		
		
		
		
		doc.add(tableDettaglio);
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
		
    } // fine metodo form ----------------------------------------------------------------------------  
    

    
    private static Map sortByComparator(Map unsortMap) {   	 
		List list = new LinkedList(unsortMap.entrySet());
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o2, Object o1) { // discendente o2 , o1
				return ((Comparable) ((Map.Entry) (o1)).getValue())
                                       .compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	} // sortByComparator end


	private class PunteggioDipendente {
		public PersonaFisicaGeko dip;
		public double punt;
	 
		PunteggioDipendente(PersonaFisicaGeko dip, double p){
			this.dip = dip;	
			this.punt = p;
		 }
	 } // PunteggioDipendente end

 
    
    //************************************************************************
    // pdf valutazioni per anno
    @RequestMapping(value="valutazioniDipartimentali/{anno}/{idIncarico}")
    public void valutazioniDipartimentali(@PathVariable("anno") int anno,  @PathVariable("idIncarico") int idIncarico,
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
    		
    	Document doc = new Document(PageSize.A4.rotate());
    	//Document doc = new Document(PageSize.A3);
	    // L'oggetto baosPDF conterr� i caratteri che costituiscono il file PDF crea il pdf in memoria
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter docWriter = null;
	    // Crea l'associazione tra l'oggetto di tipo ByteArrayOutputStream che rappresenta il PDF e il documento
	    // Ritorna un oggetto di tipo PdfWriter
	    docWriter = PdfWriter.getInstance(doc, baos);
		// Apro il documento
		doc.open();
		//
		
		
		PdfPTable tablePunteggiValut = new PdfPTable(4);
		tablePunteggiValut.setWidths(new int[] {4,1,1,1});
	    
	     
		// 1.
		final IncaricoGeko incaricoApicale = fromOrganikoServizi.findIncaricoById(idIncarico);
		// 2. 
		final PersonaGiuridicaGeko dept = fromOrganikoServizi.findPersonaGiuridicaById(incaricoApicale.pgID);
		final List<PersonaFisicaGeko> dipDept = fromOrganikoServizi.findDipendentiByDipartimentoIDAndAnno(dept.getIdPersona(),  anno);
		
		
    	final List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incaricoApicale.pgID, anno);
    	// mappe per il comparto
    	//
    	for (IncaricoGeko inc : listIncarichiDept){
    		double puntTot=0;
    		double puntCOTot = 0.0;
    		//
    		List<Obiettivo> lstObjs = objServizi.findObiettiviPrioritariByIncaricoIDAndAnno(inc.idIncarico, anno);
    		inc.setObiettivi(lstObjs);
    		puntTot += inc.getTotPunteggioObiettivi();
    		//
        	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(inc.idIncarico, anno); // una sola in realt�
        	inc.setValutazioni(lstValutDirig);
        	for(Valutazione valutazione : lstValutDirig){
        		
        		puntCOTot += valutazione.getPdlPunteggio(); // riga priorità
        		puntCOTot += valutazione.getAnalPunteggio(); // riga Analisi e Programmazione
        		puntCOTot += valutazione.getRelazPunteggio(); // riga Relazione e Coordinamento
        		puntCOTot += valutazione.getGestPunteggio(); // riga Gestione
        		puntTot  += puntCOTot;
        	}
        	log.info(inc.responsabile+ " totale valutazione: "+puntTot);
        	
        	PersonaFisicaGeko dirig = fromOrganikoServizi.findPersonaFisicaById(inc.pfID);
        	dirig.setAnno(anno);
        	//
        	// stampare risultato
        	PdfPCell cellad1 = new PdfPCell(new Phrase(dirig.stringa));
        	cellad1.setBackgroundColor(Color.LIGHT_GRAY);
        	cellad1.setHorizontalAlignment(Element.ALIGN_LEFT);
        	tablePunteggiValut.addCell(cellad1);
        	
        	PdfPCell cellad2 = new PdfPCell(new Phrase("punt.oper.: " + inc.getTotPunteggioObiettivi()));
        	cellad2.setBackgroundColor(Color.LIGHT_GRAY);
        	cellad2.setHorizontalAlignment(Element.ALIGN_LEFT);
        	tablePunteggiValut.addCell(cellad2);
        	
        	PdfPCell cellad3 = new PdfPCell(new Phrase("punt.c.o.: " + puntCOTot));
        	cellad3.setBackgroundColor(Color.LIGHT_GRAY);
        	cellad3.setHorizontalAlignment(Element.ALIGN_LEFT);
        	tablePunteggiValut.addCell(cellad3);		    
		    	
        	PdfPCell cellad4 = new PdfPCell(new Phrase("punteggio: " +puntTot));
        	cellad4.setBackgroundColor(Color.LIGHT_GRAY);
        	cellad4.setHorizontalAlignment(Element.ALIGN_LEFT);
        	tablePunteggiValut.addCell(cellad4);
        	
	    	// --------------      	
        	     
        	
        	
        	// sviluppiamo comparto
        	
        	// dipendenti della struttura di cui all'incarico
	        List<PersonaFisicaGeko> listDipendenti = fromOrganikoServizi.findDipendentiGlobalByStrutturaIDAndAnno(inc.pgID, anno);//struttura.getDipendentiSub();
	        listDipendenti.remove(dirig);
	        //
	         // per ogni dipendente mappo l'assegnazione e la valutazione per quell'incarico e quell'anno
	        if(listDipendenti!=null && !listDipendenti.isEmpty()){
	       
	        for(PersonaFisicaGeko dip : listDipendenti){
	        	dip.setAnno(anno);
	        	dip.setIncaricoValutazioneID(inc.getIdIncarico());
	        	//
	        	List<AzioneAssegnazione> assegnazioni = azAssServizi.findByPfIDAndIncaricoIDAndAnno(dip.idPersona, inc.idIncarico, anno);   //.findByOpPersonaFisicaAndIncaricoAndAnno(pf, incarico, anno);
	        	if (assegnazioni!=null && !assegnazioni.isEmpty()) {
	        		dip.setAssegnazioni(assegnazioni);
	        		double puntDipTot=0;
	        		puntCOTot=0;
		    		puntDipTot += dip.getPunteggioAssegnazioni();
		    		//
		        	List<ValutazioneComparto> valutazioni = valutazioneCompartoServizi.findByPfIDAndIncaricoIDAndAnno(dip.idPersona, inc.idIncarico, anno); //.findByOpPersonaFisicaAndIncaricoAndAnno(pf, incarico, anno);
		        	if (valutazioni!=null && !valutazioni.isEmpty()) {
		
		        		dip.setValutazioni(valutazioni);
		        		for (ValutazioneComparto valut : valutazioni){
		        			puntCOTot +=valut.getCompetSvolgAttivPunteggio();
		        			puntCOTot += valut.getAdattContextLavPunteggio();
		        			puntCOTot += valut.getCapacAssolvCompitiPunteggio();		        			
		        			puntCOTot += valut.getInnovazPunteggio();
		        			puntCOTot += valut.getOrgLavPunteggio();
		        			puntDipTot += puntCOTot;
		        		}
		        	}
		        	log.info(dip.stringa+ " totale valutazione: "+puntDipTot);
		        	
		        	
		        	// stampare risultato
		        	cellad1 = new PdfPCell(new Phrase(dip.stringa));
		        	cellad1.setBackgroundColor(Color.LIGHT_GRAY);
		        	cellad1.setHorizontalAlignment(Element.ALIGN_LEFT);
		        	tablePunteggiValut.addCell(cellad1);
		        	
		        	cellad2 = new PdfPCell(new Phrase("punt.oper.: " + dip.getPunteggioAssegnazioni()));
		        	cellad2.setBackgroundColor(Color.LIGHT_GRAY);
		        	cellad2.setHorizontalAlignment(Element.ALIGN_LEFT);
		        	tablePunteggiValut.addCell(cellad2);
		        	
		        	cellad3 = new PdfPCell(new Phrase("punt.c.o.: " + puntCOTot));
		        	cellad3.setBackgroundColor(Color.LIGHT_GRAY);
		        	cellad3.setHorizontalAlignment(Element.ALIGN_LEFT);
		        	tablePunteggiValut.addCell(cellad3);		    
				    	
		        	cellad4 = new PdfPCell(new Phrase("punteggio: " +puntDipTot));
		        	cellad4.setBackgroundColor(Color.LIGHT_GRAY);
		        	cellad4.setHorizontalAlignment(Element.ALIGN_LEFT);
		        	tablePunteggiValut.addCell(cellad4);
	        	} // loop assegnazioni
	        } // loop dipendenti comparto
	      
	      } // fine if se l'incarico ha dipendenti
	    }    // loop incarichi dipartimentali
	     
		
		doc.add(tablePunteggiValut);
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
		
    	}	//valutazioniDipartimentali		

    
    
} // ControllerPerformancePdfController  ---------------------------------------------
