<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Lista Avvisi Ge.Ko. - ruolo Manager</title>
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
<section>   
    

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
		    <td>14.2.5</td>
		        <td>08/02/2016</td>
		        <td>Si reintroduce il blocco della programmazione una volta che l'obiettivo sia stato dichiarato proposto. 
		        Se avte necessità di modificarlo chiedete al vostro controller di riportarlo allo stato interlocutorio.
		        </td>
		</tr>
		<tr>
		    <td>14.1.x</td>
		        <td>07/01/2016</td>
		        <td>Le versioni 14.1.x tengono conto delle indicazioni di cui alla nota S.G. Serv. 7° prot. 60459 del 22.12.2015.
		        </td>
		</tr>
		<tr>
	    	<td>13.1.4</td>
	        <td>16/07/2015</td>
	        <td>Versione che introduce la visualizzazione/modifica dei propri incarichi dirigenziali nei vari anni.</br>
	        Siete invitati caldamente a visualizzare i vostri incarichi e a modificarne le date se riscontrate errori nelle date iniziali e finali.
	        Badate che la correttezza di questa tabella influenza il corretto comportamento di Ge.ko. che adesso estrae i dati di programmazione, 
	        rendicontazione e valutazione comparto basandosi su questa tabella</br>
	        Nel caso mancassero incarichi, soprattutto a coprire gli anni 2013,2014 e 2015 rivolgetevi al vostro controller e fateli aggiungere 
	        nella tabella incarichi dirigenziali del dipartimento.</br>
	        Si prega di inviare una mail a gekoweb@regione.sicilia.it nel caso riscontriate malfunzionamenti.
	        </td>
	    </tr>
		<tr>
	    	<td>13.1.1</td>
	        <td>08/07/2015</td>
	        <td>Versione finale che focalizza l'attenzione sugli incarichi piuttosto che sulle strutture</br>
	        Si prega di inviare una mail a gekoweb@regione.sicilia.it nel caso riscontriate malfunzionamenti. 
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
	        <td>Viene data la possibilità al manager di ri-proporre un obiettivo definitivo (che altrimenti rimarrebbe immodificabile).</br>
	            Il manager cambia l'obiettivo da definitivo a proposto e può quindi effettuare le modifiche resesi necessarie per. es. per un obiettivo apicale
	            per il quale il Sepicos ha chiesto di modificare qualcosa. L'obiettivo ri-proposto deve comunque esssere rivalidato dal controller e successivamente essere reso di nuovo definitivo dal manager. 
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
  
           
<!-- Footer -------------------------->

<p><span class="label label-warning"><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a></span>
      Ge.Ko. by ing. R. Cirrito [view:listAvvisiManager.jsp 2015/07/20]</p>
    
</div>
</div>
</div>
 
</body>
</html>
