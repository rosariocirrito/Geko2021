<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Prospetto Valutazioni del Dipartimento</title>
    <link href=<spring:url value="/resources/css/listaValut.css" htmlEscape="true" /> rel="stylesheet"/>
</head>
    
<body>
<article>
<section >  

<div id="wrapper">      
<p id="titolo-lista">Prospetto Valutazioni Dirigenziali per <em>${dipartimento.denominazione} </em></p>   
<hr />      
        
<c:if test="${empty listIncarichiDept}">
     <p>Nessun incarico trovato.</p>
</c:if>

            
<div id="incarico">
<c:forEach items="${listIncarichiDept}" var="incarico" varStatus ="status">

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
	
	<table id="tableObiettivi">   
	<tbody>
    <c:forEach items="${incarico.obiettivi}" var="obj" varStatus ="statusObj">
	 
		<tr >    
	      	<th width="70%">Descrizione Obiettivi Operativi</th>
	      	<th width="15%">Peso</th>
	      	<th width="100">Punteggio</th>
	    </tr>
		<tr >    
	      	<td >${obj.descrizione}</td>
	      	<td >${obj.peso}</td>
	      	<td >${obj.punteggio}</td>
      	</tr>
      	
      	<tr>
      	<td colspan="2">
      	<table id="tableAzioni">
      	<tbody>
      	<tr>
	            <th class="prod" width="50%">Azione</th>
	            <th class="prod" width="5%">Indicatore previsto</th>
	            <th class="prod" width="10%">Valore Obiettivo</th>
	            <th class="prod" width="5%">Scadenza</th>
	            <th class="prod" width="5%">Peso</th>
	            <th class="prod" width="15%">Risultato</th>
	            <th class="prod" width="10%">Completamento</th>
	            <th class="prod" width="10%">Punteggio</th>
	    </tr>
      	<c:forEach items="${obj.azioni}" var="act">
	      	
	      	<tr>    
	            <td>${act.descrizione}</td>
	            <td>${act.indicatore}</td>
	            <td>${act.prodotti}</td>
	            
	            <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/></td>
	            <td>${act.peso}</td>
	            <td>${act.risultato}</td>
	            <td>${act.completamento}</td>
	            <td>${act.punteggio}</td>
	      	</tr>
      	</c:forEach>
      	</tbody>
      	</table>
	    </td>
	    </tr>
	
	</c:forEach>                	
    </tbody>
	</table>
	
	<hr>
	
	<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
	 
	
	<!-- Tabella attuazione piano di lavoro -->
	<table id="tableComport">   
	<tbody>
	<tr>
		<th>Attuazione piano di lavoro</th>
		<th>Peso Assegnato</th>
		<th>Valutazione</th>
		<th>Punteggio</th>
	</tr>
	<tr>
    	<td>Attuazione</td>
		<td>${valutazione.pdlAss}</td>
		<td>${valutazione.pdlVal}</td>
		<td>${valutazione.pdlPunteggio}</td>
	</tr>
	</tbody>
	</table>
	<hr>
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
		<td>Raggiungimento Obiettivi Operativi (range 45-65)</td>
		<td>${incarico.totPesoObiettivi}</td>
		<td>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${incarico.totPunteggioObiettivi}
		" pattern="###.#"/>
		</td>
	</tr>
	<tr>
		<td>Attuazione piano di lavoro (range 20-30)</td>
		<td>${valutazione.pdlAss}</td>
		<td>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${valutazione.pdlPunteggio}
		" pattern="###.#"/>
		</td>
	</tr>
	<tr>
		<td>Totale Performance Operativa (range 75-85)</td>
		<td>${incarico.totPesoObiettivi+valutazione.pdlAss}</td>
		<td>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${incarico.totPunteggioObiettivi+valutazione.pdlPunteggio}
		" pattern="###.#"/>
		</td>
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
	<tr>
		<td>Totale conseguibile (100)</td>
		<td>${valutazione.totPeso}
		<c:if test="${valutazione.totPeso != 100 }" > <font color="red">Peso errato !!! </c:if>
		</td>
		<td>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${valutazione.totPunteggio}
		" pattern="###.#"/>
		<c:if test="${valutazione.totPeso != 100 }" > <font color="red">Punteggio errato !!! </c:if>
		</td>
	</tr>
	</tbody>
	</table>
	<hr>
	
	
	</c:forEach>
	
	<c:if test="${empty incarico.valutazioni}">
	<p><a href=\"../../../controllerValutazione/new/${incarico.idIncarico}/${anno}>Aggiungi sezione Piano di Lavoro e Comportamenti Organizzativi per ${incarico.responsabile} </a></p>
	</c:if>
	
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
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:listValutazioniDipartimentoController.jsp 2017/02/23] </td>
	</tr>
  </table>
</div>
  
    
    
    
    </body>
</html>
