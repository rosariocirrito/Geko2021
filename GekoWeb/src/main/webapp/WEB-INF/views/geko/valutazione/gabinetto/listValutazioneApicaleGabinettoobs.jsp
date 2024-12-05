<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Prospetto Valutazioni Comportamenti organizzativi</title>
    <link href=<spring:url value="/resources/css/listaValut.css" htmlEscape="true" /> rel="stylesheet"/>
</head>
    
<body>
<article>
<section >  

<div id="wrapper">      
<p id="titolo-lista">Prospetto Valutazione Apicale per <em>${dipartimento.denominazione} </em></p>   
<hr />      
        

<c:if test="${empty lstValutDirig}">
     <p><a href=\"../../../gabinettoValutazione/new/${incarico.idIncarico}/${anno}>Aggiungi Sezione Comportamenti Organizzativi per ${incarico.responsabile} </a></p>
</c:if>

            
<div id="incarico">
<hgroup >            
 <h3 >Struttura  ${incarico.denominazioneStruttura} </h3>
 <h4 class="responsabile"> Responsabile  ${incarico.responsabile}  </h4>
  <h4>
 con incarico dal <fmt:formatDate value="${incarico.dataInizio}" pattern="dd/MM/yyyy"/>
 al <fmt:formatDate value="${incarico.dataFine}" pattern="dd/MM/yyyy"/>
 <c:if test="${incarico.interim}">
     AD INTERIM
</c:if>
 </h4>
</hgroup>
	
	
	<hr>
	
	<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
	 
	<!-- Tabella dei comportamenti organizzativi -->
	<table id="tableComport">   
	<tbody>
	<tr>
		<th>Comportamento Organizzativo</th>
		<th>Peso Assegnato</th>
		<th>Valutazione</th>
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
	<h3> Sintesi valutazione anno: ${valutazione.anno} per ${incarico.responsabile}</h3>
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
	<hr>
	
	
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
      <td><a href="<spring:url value="/ROLE_GABINETTO" htmlEscape="true" />">Menu Gabinetto</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:listValutazioneApicaleGabinetto.jsp 2017/10/11] </td>
	</tr>
  </table>
</div>
  
    
    
    
    </body>
</html>
