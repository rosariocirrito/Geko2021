<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Valutazione del Dirigente</title>
<link href="../../resources/css/listaValut.css" rel="stylesheet"/>

</head>

<body>
<div id="wrapper">
<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Lista Incarichi / Valutazione Dirigenziali per <em>${nomeStruttura} </em>- anno ${anno}</h1>
<hr />    
        
<c:if test="${empty listIncarichi}">
    <p>Nessun incarico trovato.</p>
</c:if>
  
<c:forEach items="${listIncarichi}" var="incarico" varStatus ="status">   
	<div id="incarico">             
	<hgroup >
		<h3 >Struttura  ${incarico.opPersonaGiuridica.denominazione}</h3>
		<h2 >Responsabile  ${incarico.opPersonaFisica.stringa}</h2>  
	</hgroup>
 
	<table id="tableObiettivi">   
	<tbody> 
	<c:forEach items="${incarico.opPersonaGiuridica.obiettivi}" var="obj" varStatus ="status">
		<c:if test="${obj.anno == anno && obj.tipo == 'OBIETTIVO' && obj.statoApprov == 'DEFINITIVO' }" > 
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
      		<c:forEach items="${obj.azioni}" var="act">
		      	<tr>
		            <th class="prod" width="60%">Prodotti</th>
		            <th class="prod" width="5%">Q.tà</th>
		            <th class="prod" width="5%">Scadenza</th>
		            <th class="prod" width="5%">Peso</th>
		            <th class="prod" width="15%">Risultato</th>
		            <th class="prod" width="10%">Completamento</th>
		            <th class="prod" width="10%">Punteggio</th>
		        </tr>
		      	<tr>    
		            <td>${act.prodotti}</td>
		            <td>${act.quantita}</td>
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
		</c:if>
	</c:forEach>                	
    </tbody>
	</table>
	

<!-- Valutazione -->
<hr>
	
	<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
	<c:if test="${valutazione.anno == anno }" > 
	
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
	<p> Peso totale: ${valutazione.totPeso}</p>
	<p> Punteggio totale: ${valutazione.totPunteggio}</p>
	</c:if>
	</c:forEach>
	
	
	
	


</div>
</c:forEach>

</section>
</article>
    
     <!-- Footer -------------------------->
  <div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - 
      ing. Rosario Cirrito </td>
    </tr>
    <tr>
      <td>[listValutazioneStrutturaManager.jsp 2019/10/18] </td>
    </tr>
  </table>
</div>
  
    
</div>    
</body>
</html>
