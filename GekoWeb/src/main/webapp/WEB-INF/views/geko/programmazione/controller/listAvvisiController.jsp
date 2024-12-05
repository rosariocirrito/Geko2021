<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Lista Avvisi Ge.Ko. - ruolo controller</title>
    <link href=<spring:url value="/resources/bootstrap/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
    <style>
		.row{
			margin-top:16px;
			margin-left:16px;
			background-color:lightgray;
		}
		.col{
			background-color:lightblue;
			padding:8px;
			border:2px solid darkgray;
		}
	</style>
</head>

<body>
        
<div class="container" >
<div class="row">
<div class="col-sm-12 col">    
<article>
<section id="obj-sezione">   
    

<h2>Avvisi di modifiche / miglioramenti</h2>
               
	<table class="table table-bordered table-striped">
		<colgroup>
			<col class="name">
			<col class="desrc">
			<col class="desrc">
		</colgroup>
	<thead>   
	    <tr>
	    	<th scope= "col" >versione</th>
	        <th scope= "col" >data</th>
	        <th scope= "col" >avviso</th>
	    </tr>  
	</thead>
	<tbody>
	<tr>
	    <td>14.2.1</td>
	        <td>18/01/2016</td>
	        <td>Aggiunta la voce di menu Elimina rendicontazione</td>
	</tr>
	<tr>
	    <td>14.1.x</td>
	        <td>07/01/2016</td>
	        <td>Le versioni 14.1.x tengono conto delle indicazioni di cui alla nota S.G. Serv. 7° prot. 60459 del 22.12.2015.
	        </td>
	</tr>
		<tr>
	<tr>
	<tr>
	    <td>13.3.3</td>
	        <td>06/10/2015</td>
	        <td>Versione che calcola il punteggio con le cifre decimali esatte (senza arrotondamenti all'intero).
	        </td>
	    </tr>
		<tr>
	<tr>
	    <td>13.2.14</td>
	        <td>16/09/2015</td>
	        <td>Versione che introduce la possibilità per il controller di clonare una programmazione per un incarico di interim o di nuovo dirigente.</br>
	        Occorre preliminarmente creare il nuovo incarico. Successivamente si attiva la scelta clona programmazione su questo incarico.
	        Vengono mostrati in un menu a tendina gli incarichi già esistenti per la struttura in oggetto</br>
	        Si seleziona l'incarico sorgente e si procede alla duplicazione di tutta la programmazione e rendicontazione esistente.
	        
	        </td>
	    </tr>
		<tr>
	    	<td>13.2.11</td>
	        <td>10/09/2015</td>
	        <td>Versione che introduce la possibilità per il controller di modificare gli obiettivi anche se sono già stati resi definitivi.</br>
	        Questa funzione era fino a qualche mese fa prerogativa del ruolo administrator. In seguito all'incontro del 9/9/15 con i controller dei vari
	        dipartimenti si è deciso di apportare questa modifica per accelerare la correzioni di eventuali errori. </br>
	        Il controller una volta apportate le modifiche porrà l'obiettivo nello stato validato al fine di permettere al manager di prendere visione delle modifiche apportate
	        e rendere definitiva la programmazione.
	        </td>
	    </tr>
		<tr>
	    	<td>13.2.2</td>
	        <td>26/08/2015</td>
	        <td>Versione che introduce la possibilità di modifica del peso delle azioni in sede di valutazione incarico dirigenziale.</br>
	        Questa modifica permette al controller che al momento della valutazione dovesse accorgersi di aver assegnato un peso eccessivo alle azioni degli obiettivi di ridurle fino ad ottenere il peso totale del 100%.</br>
	        In realtà il peso esatto di ciascuna azione avrebbe dovuto essere calcolato correttamente in sede di programmazione; ma quando l'obiettivo diventa definitivo il peso delle sue azioni risulta immodificabile per esigenze di sicurezza. </br> 
	        Questa opzione va quindi usata solo nei casi di effettiva necessità a fronte di peso totale di valutazione diverso dal 100%.</br>
	        Si consiglia alla fine di ristampare la scheda di programmazione con i nuovi pesi.
	        </td>
	    </tr>
	    <tr>
	    	<td>13.1.4</td>
	        <td>16/07/2015</td>
	        <td>Versione che introduce la visualizzazione/modifica degli incarichi dirigenziali nei vari anni.</br>
	        Si è voluto dare la possibilità ai singoli manager di verificare se gli incarichi sono stati assegnati correttamente. 
	        In caso contrario al manager è consentito modificare le date di inizio e fine incarico ma non quella di creare o cancellare incarichi esistenti.
	        Queste due ultime opzioni rimangono prerogativa del controller.</br>
	        Ho lanciato una routine che in base alla struttura precedentemente associata all'obiettivo ha tentato di individuare l'incarico corretto.
	        Quando non è riuscita a trovare un incarico plausibile l'obiettivo è stato dichiarato orfano di incarico.
	        Ho aggiunto per tutti voi la visualizzazione di questi obiettivi orfani con la possibilità di assegnare agli incarichi dirigenziali selezionati in base a struttura ed anno</br>
	        E' necessario che procediate l'assegnazione degli incarichi agli obiettivi orfani.</br>
	        E' in corso di sviluppo una ulteriore procedura per duplicare gli obiettivi non prioritari da un incarico all'altro (per le strutture che cambiano manager) 
	        nonché di variare la assegnazione di un obiettivo tra manager di una stessa struttura (in modo da discrminare tra obiettivi prioritari del manager precedente e di quello subentrante). Spero di riuscirci la la prossima release
	        Si prega di inviare una mail a gekoweb@regione.sicilia.it nel caso riscontriate malfunzionamenti.
	        </td>
	    </tr>
		<tr>
	    	<td>13.1.1</td>
	        <td>08/07/2015</td>
	        <td>Versione finale che focalizza l'attenzione sugli incarichi piuttosto che sulle strutture</br>
	        	Si prega di inviare una mail a gekoweb@regione.sicilia.it nel caso riscontriate malfunzionamenti </br>
	        	E' in corso di preparazione la routine per duplicare la programmazione non prioritaria dal manager uscente a quello subentrante
	        </td>
	    </tr>
		<tr>
	    	<td>13.0.x</td>
	        <td>07/07/2015</td>
	        <td>Versione transitoria della release 13.1.1 che focalizza l'attenzione sugli incarichi piuttosto che sulle strutture</br>
	        E' l'inizio di una radicale revisione di Ge.Ko. per poter gestire rendicontazione e valutazione con incarichi parziali nell'anno</br>
	        Chiedo scusa a tutti voi per i malfunzionamenti che dovessero originarsi prima di giungere (spero in 1 settimana) alla versione stabile 13.1.1
	        </td>
	    </tr>
		<tr>
	
	    	<td>12.2.7</td>
	        <td>06/07/2015</td>
	        <td>Corretto il bug che non modificava l'azione su obiettivo proposto.</br>
	        Inserito il campo inizio Azione che servirà per discriminare il manager responsabile per incarichi variati nel corso dell'anno.
	        </td>
	    </tr>
		<tr>
	    	<td>12.2.6</td>
	        <td>01/07/2015</td>
	        <td>Adottata la tecnologia "bootstrap" per le maschere di visualizzazione programmazione.
	        </td>
	    </tr>  
		<tr>
	    	<td>12.2.5</td>
	        <td>01/07/2015</td>
	        <td>Corretti alcuni "bug" sull'individuazione del responsabile struttura/dipartimento. </br>
	            Viene data la possibilità al manager di ri-proporre un obiettivo definitivo (che altrimenti rimarrebbe immodificabile).</br>
	            Il manager cambia l'obiettivo da definitivo a proposto e può quindi effettuare le modifiche resesi necessarie per. es. per un obiettivo apicale
	            per il quale il Sepicos ha chiesto di modificare qualcosa. </br>
	            L'obiettivo ri-proposto deve comunque esssere rivalidato dal controller e successivamente essere reso di nuovo definitivo dal manager. 
	        </td>
	    </tr> 
		<tr>
	    	<td>12.2.4</td>
	        <td>30/06/2015</td>
	        <td>Adottata la tecnologia "bootstrap" per le maschere di login e dei menu.
	        </td>
	    </tr>  
	    <tr>
	    	<td>12.2.3</td>
	        <td>25/06/2015</td>
	        <td >Sono stati corretti vari pdf della programmazione apicale e della valutazione che adesso dovrebbero visualizzare correttamente i responsabili delle strutture e le scadenze apicali.</br>
	        	La visualizzazione degli incarichi è differenziata su strutture attive e cancellate. La modifica è permessa solo sulle attive.</br>
	        	La tabella incarichi riporta la colonna interim. Non è più necessario duplicare il dirigente per assegnargli l'interim  né per assegnargli più incarichi sulla stessa struttura in periodi diversi.</br>
	            La valutazione dei dirigenti con incarichi parziali nell'anno origina tante schede quanti sono gli incarichi dei dirigenti per quella struttura.
	        </td>
	    </tr>  

      </tbody>
     </table>       


</section>
</article>   
</div>    
           
<!-- Footer -------------------------->
<div id="footer">
      
<p><span class="label label-warning"><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a></span>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito [view:listAvvisiController.jsp]<p>
    
</div>
</div>
</div>
 
</body>
</html>
