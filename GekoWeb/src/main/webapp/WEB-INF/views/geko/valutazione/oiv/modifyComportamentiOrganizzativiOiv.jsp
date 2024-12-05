<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
        <title>Modifica Valutazione Dirigenziale Apicale</title>
    <link href=<spring:url value="/resources/css/listaValut.css" /> rel="stylesheet"/>

</head>
    
<body>
<article>
<section >  

<div id="wrapper">      
<p id="titolo-lista">Modifica Valutazione Dirigenziale Apicale per <em>${dipartimento.denominazione} </em></p>
</p>   
<hr />      
        
<c:if test="${empty lstValutDirig}">
     <p><a href=\"../../../gabinettoValutazione/new/${incarico.idIncarico}/${anno}>Aggiungi Sezione Comportamenti Organizzativi per ${incarico.responsabile} </a></p>
</c:if>


	<div id="incarico">
	 <hgroup >            
		 <h3 >
		 Struttura  ${incarico.denominazioneStruttura}
		 </h3>
		 <h4 class="responsabile"> Responsabile  ${incarico.responsabile}  </h4>
		  <h4>
		 con incarico dal <fmt:formatDate value="${incarico.dataInizio}" pattern="dd/MM/yyyy"/>
		 al <fmt:formatDate value="${incarico.dataFine}" pattern="dd/MM/yyyy"/>
		 <c:if test="${incarico.interim}">
		     AD INTERIM
		</c:if>
		 </h4>
	 </hgroup>
	
	
	
	
	
	<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
	
	
	
	
	<!-- Tabella dei comportamenti organizzativi -->
	<table id="tableComport">   
	<tbody>
	<tr>
		<th>Comportamenti Organizzativi</th>
		<th><a href="../../../oivValutazione/${valutazione.id}/editPesiComportOrgan">Edit</a>Peso Assegnato (5-10)</th>
		<th><a href="../../../oivValutazione/${valutazione.id}/editValutComportOrgan">Edit</a>Valutazione</th>
		<th>Punteggio</th>
	</tr>
	<tr>
    	<td>Analisi e Programmazione </td>
		<td>${valutazione.analProgrAss}</td>
		<td>${valutazione.analProgrVal}</td>
		<td>${valutazione.analPunteggio}</td>
	</tr>
	<tr>
    	<td>Relazione e Coordinamento </td>
		<td>${valutazione.relazCoordAss}</td>
		<td>${valutazione.relazCoordVal}</td>
		<td>${valutazione.relazPunteggio}</td>
	</tr>
	<tr>
    	<td>Gestione e Realizzazione </td>
		<td>${valutazione.gestRealAss}</td>
		<td>${valutazione.gestRealVal}</td>
		<td>${valutazione.gestPunteggio}</td>
	</tr>
	</tbody>
	</table>
	
	<!-- Tabella sintesi finale -->
	<hr>
	<h2> Sintesi valutazione anno: ${valutazione.anno} per ${incarico.responsabile} </h2>
	<table id="tableSintesi">   
	<tbody>
	<tr>
		<th width= 50%></th>
		<th>Peso Attribuito</th>
		<th>Punteggio</th>
	</tr>
	
	
	
	<tr>
		<td>Totale conseguibile Comportamento Organizzativo (range 15-25)</td>
		<td>${valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss}</td>
		<td>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${valutazione.analPunteggio+valutazione.relazPunteggio+valutazione.gestPunteggio}
		" pattern="###.#"/>
		</td>
	</tr>
	
	</tbody>
	</table>
	
	
	</c:forEach>
	
	
	
	<!-- chiude divisione incarico -->
	</div>
<!-- chiude divisione wrapper -->
</div>
</section>
</article>
    
     <!-- Footer -------------------------->
  <div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_OIV" htmlEscape="true" />">Menu Oiv</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - 
      ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:modifyComportamentiOrganizzativiOiv.jsp 2017/11/15] </td>
	</tr>
  </table>
</div>
  
</body>
</html>
