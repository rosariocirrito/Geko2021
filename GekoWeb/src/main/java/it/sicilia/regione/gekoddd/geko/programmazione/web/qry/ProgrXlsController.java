package it.sicilia.regione.gekoddd.geko.programmazione.web.qry;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategica;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.areaStrategica.AreaStrategicaQryService;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategico;
import it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico.ObiettivoStrategicoQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgramma;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazProgramma.AssociazProgrammaQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoQryService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneQryService;

@Controller
@RequestMapping("/qryProgrXls")
public class ProgrXlsController {
	
private Log log = LogFactory.getLog(ProgrXlsController.class);
	
	//
   
	//
	@Autowired
	private ObiettivoQryService objServizi;
	@Autowired
    private FromOrganikoQryService fromOrganikoServizi;
	@Autowired
    private AssociazProgrammaQryService associazProgrammaServizi;
	@Autowired
    private ValutazioneQryService valutazioneDirigServizi;
	
	@GetMapping(value="xlsProgrammazioneApicaleIncarico/{anno}/{idIncarico}")
    public ResponseEntity<ByteArrayResource> xlsProgrammazioneApicaleIncaricoController(@PathVariable("anno") int anno,
    		@PathVariable("idIncarico") int idIncarico,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        	// estraggo le collections che mi servono 
        	final IncaricoGeko incaricoApic = fromOrganikoServizi.findIncaricoById(idIncarico);
        	List<Obiettivo> listObiettiviApicali = objServizi.findObiettiviApicaliDirettiByIncaricoIDAndAnno(idIncarico, anno);
        	incaricoApic.setObiettivi(listObiettiviApicali);
        	List<Valutazione> lstValutDirig = valutazioneDirigServizi.findByIncaricoIDAndAnno(incaricoApic.idIncarico, anno); // una sola in realt�
        	incaricoApic.setValutazioni(lstValutDirig);
        	//
        	ByteArrayOutputStream stream = new ByteArrayOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();; // creates the workbook
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ProgrammazioneApicale"+anno+".xlsx");
            //
            Map<String, CellStyle> styles = createStyles(workbook);
            //            
            this.generaSchedaObiettiviApicali(workbook, styles, incaricoApic, anno);
            this.generaSchedeDettaglioObiettiviApicali(workbook, incaricoApic, anno);
            
            //
            
            workbook.write(stream);
            workbook.close();
            return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()),
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	// ----------- metodi privati ------------------------
	private void generaSchedaObiettiviApicali(XSSFWorkbook workbook, Map<String, CellStyle> styles, IncaricoGeko inc, int anno){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
    	//
    	CellStyle style3 = workbook.createCellStyle();
		Font fontTitle3 = workbook.createFont();
		fontTitle3.setFontName("HELVETICA");
		fontTitle3.setBold(true);
		style3.setFillForegroundColor(HSSFColor.BLUE.index);
		//fontTitle3.setColor(HSSFColor.RED.index);
		style3.setFillBackgroundColor(HSSFColor.GREEN.index);
		style3.setFont(fontTitle3);
    	//
    			//PdfPTable tableProgr = new PdfPTable(1);
    	//
    			//tableProgr.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    	
		// Scheda 1
        XSSFSheet sheet = workbook.createSheet("Scheda1");
        
      //turn off gridlines
        sheet.setDisplayGridlines(true);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        //the following three statements are required only for HSSF
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short)1);
        printSetup.setFitWidth((short)1);
        
        /*
      //the header row: centered text in 48pt font
        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(12.75f);
        */
        
        sheet.setDefaultColumnWidth(10);
        //
        
        //int idxRow = 0;
        //sheet.groupColumn(0, 11);
        Row testata = sheet.createRow(0);
     // verifico se lista vuota
        String value0_0 = "";
        Cell cellaTestata = testata.createCell(0);
        
	    if (inc.getObiettivi().isEmpty()){
	    	value0_0 = "Nessun Obiettivo Apicale trovato per "+inc.denominazioneStruttura+" - anno:"+anno;
	    }
    	// scheda 1
	    else {
	    	value0_0 = "SCHEDA PROGRAMMAZIONE 1: programmazione obiettivi del dirigente generale ai fini della successiva valutazione della performance - Anno "+anno;    
	    }
	    //testata.setRowStyle(styles.get("cell_b_centered"));
	    cellaTestata.setCellValue(value0_0);
	    cellaTestata.setCellStyle(styles.get("cell_b_centered"));
	    //sheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);
	    sheet.addMergedRegion(new CellRangeAddress(0,0,0,11));
	  //group rows for each phase, row numbers are 0-based
        
	    //sheet.groupRow(0,11);
        //
        Row rowStru = sheet.createRow(1);
        rowStru.createCell(0).setCellValue("DIPARTIMENTO/UFFICIO: "+inc.denominazioneStruttura);
        //rowStru.getCell(0).setCellStyle(style3);
        rowStru.getCell(0).setCellStyle(styles.get("cell_bg"));
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,11));
        //
        String nomeResponsabile="";
    	if(inc.responsabile != null){
    		nomeResponsabile = inc.responsabile;
    	}
        Row rowResp = sheet.createRow(2);
        rowResp.createCell(0).setCellValue("DIRIGENTE GENERALE: "+nomeResponsabile+"_");
        //
        Row rowPfOper = sheet.createRow(3);
        rowPfOper.createCell(0).setCellValue("PERFORMANCE OPERATIVA");
        //
        int nrObj = 0;
        // riga intestazione tabella obiettivi
        Row rowTh = sheet.createRow(4);
        // col0
        //thNrObj.setBackgroundColor(Color.LIGHT_GRAY);
	    //thNrObj.setHorizontalAlignment(Element.ALIGN_LEFT);
        rowTh.setRowStyle(styles.get("cell_bg"));
        rowTh.createCell(0).setCellValue("Nr.");
        // col1
        rowTh.createCell(1).setCellValue("Programma");
        // col2
        rowTh.createCell(2).setCellValue("Missione");
        // col3
        rowTh.createCell(3).setCellValue("Descrizione obiettivo operativo");
        // col4
        rowTh.createCell(7).setCellValue("Corr. ob. Strategico");
        // col5
        rowTh.createCell(8).setCellValue("Indicatore previsto");
        // col6
        rowTh.createCell(9).setCellValue("Valore Obiettivo");
        // col7
        rowTh.createCell(10).setCellValue("Data ultima");
        // col8
        rowTh.createCell(11).setCellValue("Peso attrib.");
     // itero sugli obiettivi
    	for(Obiettivo obj : inc.getObiettivi()){
    		nrObj++;
    		Row objRow = sheet.createRow(nrObj+4);
    		// col0
    		objRow.createCell(0).setCellValue(nrObj);
    		// col1 - 2
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
    		objRow.createCell(1).setCellValue(strProgramma);
    		objRow.createCell(2).setCellValue(strMissione);
    		// col3
    		objRow.createCell(3).setCellValue(obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote());
    		objRow.getCell(3).setCellStyle(styles.get("cell_indented"));
    		// col4
    		String strStrat = "";
		    if(!obj.getAssociazObiettivi().isEmpty()){
			    for (AssociazObiettivi ass : obj.getAssociazObiettivi()){
			    	strStrat += (ass.getStrategico().getCodice() + " ");
			    }		    
		    }
		    objRow.createCell(7).setCellValue(strStrat);
		    // col5		    
    		objRow.createCell(8).setCellValue(obj.getIndicatore());
    		// col6
    		objRow.createCell(9).setCellValue(obj.getValObiettivo());
    		// col7
    		objRow.createCell(10).setCellValue(sdf.format(obj.getDataUltima()));
    		// col8
    		objRow.createCell(11).setCellValue(obj.getPeso());
    	}	// fine loop obiettivi
    	
    	Row rowTotPOper = sheet.createRow(5+inc.getObiettivi().size());
    	rowTotPOper.createCell(0).setCellValue("Totale conseguibile performance operativa");
    	rowTotPOper.createCell(8).setCellValue(inc.getTotPesoObiettivi());
        //
    	
    	 // valutazioni
		for (Valutazione valutazione : inc.getValutazioni()){			
			if (valutazione.getAnno() == anno){
				//
				Row rowComOrg = sheet.createRow(6+inc.getObiettivi().size());
				rowComOrg.createCell(0).setCellValue("COMPORTAMENTO ORGANIZZATIVO");
				//
				Row rowComOrg2 = sheet.createRow(7+inc.getObiettivi().size());
				rowComOrg2.createCell(0).setCellValue("QUALITA' GESTIONALI-RELAZIONALI (selezionare 3 su 4)");
				rowComOrg2.createCell(10).setCellValue("Range assegnabile");
				rowComOrg2.createCell(11).setCellValue("Peso attribuito");
				//
				//
				Row rowGestione = sheet.createRow(8+inc.getObiettivi().size());
				rowGestione.createCell(0).setCellValue("Capacità di intercettare, gestire risorse e programmare");
				rowGestione.createCell(10).setCellValue("5-20");
				rowGestione.createCell(11).setCellValue(""+valutazione.getGestRealAss());
				//
				Row rowAnalisi = sheet.createRow(9+inc.getObiettivi().size());
				rowAnalisi.createCell(0).setCellValue("Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione");
				rowAnalisi.createCell(10).setCellValue("5-20");
				rowAnalisi.createCell(11).setCellValue(""+valutazione.getAnalProgrAss());
				//
				Row rowRelaz = sheet.createRow(10+inc.getObiettivi().size());
				rowRelaz.createCell(0).setCellValue("Capacità di valorizzare competenze e attitudini dei propri collaboratori");
				rowRelaz.createCell(10).setCellValue("5-20");
				rowRelaz.createCell(11).setCellValue(""+valutazione.getRelazCoordAss());
				//
				Row rowPdl = sheet.createRow(11+inc.getObiettivi().size());
				rowPdl.createCell(0).setCellValue("Capacità di individuazione del livello di priorità degli interventi da realizzare");
				rowPdl.createCell(10).setCellValue("5-20");
				rowPdl.createCell(11).setCellValue(""+valutazione.getPdlAss());
			
				Row rowRiassunt = sheet.createRow(12+inc.getObiettivi().size());
				rowRiassunt.createCell(0).setCellValue("TABELLA RIASSUNTIVA");
				//
				Row rowTotPO = sheet.createRow(13+inc.getObiettivi().size());
				rowTotPO.createCell(0).setCellValue("Totale performance operativa");
				rowTotPO.createCell(10).setCellValue(""+inc.getTotPesoObiettivi());
				//
				Row rowTotCO = sheet.createRow(14+inc.getObiettivi().size());
				rowTotCO.createCell(0).setCellValue("Totale Comportamento Organizzativo");
				int totCompOrgAss = valutazione.getAnalProgrAss()+
			    		valutazione.getRelazCoordAss()+
			    		valutazione.getGestRealAss()+
			    		valutazione.getPdlAss();
				rowTotCO.createCell(10).setCellValue(""+totCompOrgAss);
				//
				Row rowTot = sheet.createRow(15+inc.getObiettivi().size());
				rowTot.createCell(0).setCellValue("Totale complessivo");
				rowTot.createCell(10).setCellValue(""+valutazione.getTotPeso());
			}
		}	
		
		Row rowFirma1 = sheet.createRow(16+inc.getObiettivi().size());
		rowFirma1.createCell(0).setCellValue("Data");
		rowFirma1.createCell(1).setCellValue("IL PRESIDENTE / L'ASSESSORE");
		rowFirma1.createCell(7).setCellValue("IL DIRIGENTE GENERALE");
		//
		Row rowFirma2 = sheet.createRow(17+inc.getObiettivi().size());
		rowFirma2.createCell(0).setCellValue("");
		rowFirma2.createCell(1).setCellValue("");
		rowFirma2.createCell(7).setCellValue(inc.getResponsabile());
		//
		Row rowFirma3 = sheet.createRow(18+inc.getObiettivi().size());
		rowFirma3.createCell(0).setCellValue("___/___/___");
		rowFirma3.createCell(1).setCellValue("____________________________");
		rowFirma3.createCell(7).setCellValue("____________________________");
		
    } // fine metodo generaSchedaObiettivi
    
	// ------------------------------------------------------------------------------------
    private void generaSchedeDettaglioObiettiviApicali(XSSFWorkbook workbook, IncaricoGeko inc, int anno){
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy"); 
    	int nordObj = 0;
    	for(Obiettivo obj : inc.getObiettivi()){
    		nordObj++;
    		//
    		// Scheda nordObj
            XSSFSheet sheet = workbook.createSheet("SchedaObj_"+(nordObj));
            sheet.setDefaultColumnWidth(10);           
            //
            Row testata = sheet.createRow(0);
            testata.createCell(0).setCellValue("Scheda programmazione 2: Scheda di dettaglio delle azioni correlate agli obiettivi assegnati (scheda di programmazione 1)");		
            //
            Row rowStru = sheet.createRow(1);
            rowStru.createCell(0).setCellValue("DIPARTIMENTO/UFFICIO: "+inc.denominazioneStruttura);
            rowStru.createCell(4).setCellValue("Anno di riferimento della valutazione: "+anno);
            String strStrat="";
		    if(!obj.getAssociazObiettivi().isEmpty()){
			    for (AssociazObiettivi ass : obj.getAssociazObiettivi()){
			    	strStrat += (ass.getStrategico().getDescrizione() + " ");
			    }
		    } 
            rowStru.createCell(8).setCellValue("obiettivo strategico di riferimento: "+strStrat);
            //rowStru.getCell(0).setCellStyle(style3);
            //
            String nomeResponsabile="";
        	if(inc.responsabile != null){
        		nomeResponsabile = inc.responsabile;
        	}
            Row rowResp = sheet.createRow(2);
            rowResp.createCell(0).setCellValue("DIRIGENTE GENERALE: "+nomeResponsabile);
            //
            Row rowObj = sheet.createRow(3);
            rowObj.createCell(0).setCellValue("Descrizione obiettivo:"+obj.getCodice()+ " - "+obj.getDescrizione() + " " + obj.getNote());
            
            // itero sulle azioni
		    int nrAct =0;
		    int totPesoAct =0;
		    // riga intestazione tabella azioni
		    // act descript row
		    // riga intestazione tabella obiettivi
	        Row rowTh = sheet.createRow(4);
	        // col0
	        //thNrObj.setBackgroundColor(Color.LIGHT_GRAY);
		    //thNrObj.setHorizontalAlignment(Element.ALIGN_LEFT);
	        rowTh.createCell(0).setCellValue("Nr.");
	        // col1
	        rowTh.createCell(1).setCellValue("Azioni");	        
	        // col5
	        rowTh.createCell(8).setCellValue("Indicatore previsto");
	        // col6
	        rowTh.createCell(9).setCellValue("Valore Obiettivo");
	        // col7
	        rowTh.createCell(10).setCellValue("Data ultima");
	        // col8
	        rowTh.createCell(11).setCellValue("Peso attrib.");
	        //
	        Row rowTh2 = sheet.createRow(5);
	        //
	        rowTh2.createCell(0).setCellValue("");
	        // col1
	        rowTh2.createCell(1).setCellValue("Colonna B");	        
	        // col5
	        rowTh2.createCell(8).setCellValue("Colonna C");
	        // col6
	        rowTh2.createCell(9).setCellValue("Colonna D");
	        // col7
	        rowTh2.createCell(10).setCellValue("Colonna E");
	        // col8
	        rowTh2.createCell(11).setCellValue("Colonna E");
	        //
	        if(!obj.getAzioni().isEmpty()){
			    for (Azione act : obj.getAzioni()){
			    	nrAct++;
			    	totPesoAct += act.getPesoApicale();
			    	// nr azione
			    	// act descript row
			    	Row actRow = sheet.createRow(nrAct+5);
		    		// col0
			    	actRow.createCell(0).setCellValue(""+ nrAct +")");
		    		// col1 - 2
			    	String strcellActDescr = act.getDenominazione() +" - "+act.getDescrizione() +" - "+ act.getNote();
				    if (act.isTassativa()) strcellActDescr += "AZIONE E SCADENZA TASSATIVE";    
				    actRow.createCell(1).setCellValue(strcellActDescr);
				    //
				    actRow.createCell(8).setCellValue(""+act.getIndicatore());
		    		// col3
				    actRow.createCell(9).setCellValue(act.getProdotti());
		    		// col4
				    String strcellr3 = "";
				    if (act.getScadenza() != null)	strcellr3+=	sdf.format(act.getScadenza());
				    if (act.isTassativa()) strcellr3 += " TASSATIVA";
				    actRow.createCell(10).setCellValue(strcellr3);
		    		// col8
				    actRow.createCell(11).setCellValue(""+act.getPesoApicale());
			    	
			    }
	        }
	        //
	        Row rowTotact = sheet.createRow(6+obj.getAzioni().size());
	        rowTotact.createCell(0).setCellValue("");
	        rowTotact.createCell(7).setCellValue("Totale pesi azioni (=100)");
	        rowTotact.createCell(11).setCellValue(""+totPesoAct);
	        //
	        Row rowFirma1 = sheet.createRow(7+obj.getAzioni().size());
			rowFirma1.createCell(0).setCellValue("Data");
			rowFirma1.createCell(1).setCellValue("IL PRESIDENTE / L'ASSESSORE");
			rowFirma1.createCell(7).setCellValue("IL DIRIGENTE GENERALE");
			//
			Row rowFirma2 = sheet.createRow(8+obj.getAzioni().size());
			rowFirma2.createCell(0).setCellValue("");
			rowFirma2.createCell(1).setCellValue("");
			rowFirma2.createCell(7).setCellValue(inc.getResponsabile());
			//
			Row rowFirma3 = sheet.createRow(9+obj.getAzioni().size());
			rowFirma3.createCell(0).setCellValue("___/___/___");
			rowFirma3.createCell(1).setCellValue("____________________________");
			rowFirma3.createCell(7).setCellValue("____________________________");
    	} 
    }    
    
    /**
     * create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(XSSFWorkbook wb){
        Map<String, CellStyle> styles = new HashMap<>();
        DataFormat df = wb.createDataFormat();

        CellStyle style;
        Font headerFont = wb.createFont();
        headerFont.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("header_date", style);

        Font font1 = wb.createFont();
        font1.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font1);
        styles.put("cell_b", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_b_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_g", style);

        Font font2 = wb.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        font2.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font2);
        styles.put("cell_bb", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_bg", style);

        Font font3 = wb.createFont();
        font3.setFontHeightInPoints((short)14);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        font3.setBold(true);
        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font3);
        style.setWrapText(true);
        styles.put("cell_h", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setWrapText(true);
        styles.put("cell_normal", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        styles.put("cell_normal_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_normal_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setIndention((short)1);
        style.setWrapText(true);
        styles.put("cell_indented", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("cell_blue", style);

        return styles;
    }

    private static CellStyle createBorderedStyle(Workbook wb){
        BorderStyle thin = BorderStyle.THIN;
        short black = IndexedColors.BLACK.getIndex();

        CellStyle style = wb.createCellStyle();
        style.setBorderRight(thin);
        style.setRightBorderColor(black);
        style.setBorderBottom(thin);
        style.setBottomBorderColor(black);
        style.setBorderLeft(thin);
        style.setLeftBorderColor(black);
        style.setBorderTop(thin);
        style.setTopBorderColor(black);
        return style;
    }
    
    
    
}


