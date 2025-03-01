<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Valutazione del Dirigente Generale</title>
	<link href=<spring:url value="/resources/css/listaValut.css"  /> rel="stylesheet"/>

</head>

<body>
<div id="wrapper">
<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Valutazione Dirigenziale per <em>${incarico.stringa} </em>- anno ${anno}</h1>
<hr />    


<c:if test="${empty lstValutDirig}">
     <p>Richiedi al Gabinetto di aggiungere la Sezione Comportamenti Organizzativi per ${incarico.responsabile}</p>
</c:if>

 
	<div id="incarico">             
	<hgroup >
		<h3 >Struttura  ${incarico.denominazioneStruttura}</h3>
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
	<c:forEach items="${listObiettiviApicali}" var="obj" varStatus ="status">
		
		<tr >    
	      	<th width="70%">Descrizione Obiettivi Apicali</th>
	      	<th width="15%">Peso Apicale</th>
	      	<th width="100">Punteggio</th>
	    </tr>
		<tr >    
	      	<td >${obj.descrizione}</td>
	      	<td >${obj.pesoApicale}</td>
	      	<td >${obj.punteggioApicale}</td>
      	</tr>
		<tr>
      		<td colspan="2">
      		<table id="tableAzioni">
      		<tbody>
      		<tr>
	            <th class="prod" width="50%">Azione</th>
	            <th class="prod" width="5%">Indi- catore</th>
	            <th class="prod" width="10%">Valore Obiettivo</th>	            
	            <th class="prod" width="5%">Scadenza</th>
	            <th class="prod" width="5%">Peso</th>
	            <th class="prod" width="15%">Risultato</th>
	            <th class="prod" width="10%">Valutazione OIV</th>
	            <th class="prod" width="5%">Comple- tamento</th>
	            <th class="prod" width="5%">Punteggio</th>
	        </tr>
      		<c:forEach items="${obj.azioni}" var="act">
		      	
		      	<tr>    
		            <td>${act.descrizione}</td>
		            <td>${act.indicatore}</td>
		            <td>${act.prodotti}</td>		            
		            <td><fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/></td>
		            <td>${act.pesoApicale}</td>
		            <td>${act.risultato}</td>
		             <td>
		            <c:forEach items="${act.oivAzione}" var="oivAzione">
			      		${oivAzione.valutazioni} </br>	             	
		             </c:forEach>
	            </td>
		            <td>${act.completamento}</td>
		            <td>${act.punteggioApicale}</td>
		      	</tr>
      		</c:forEach>
      		</tbody>
      		</table>
	    	</td>
		</tr>
		
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
		<th>Comportamenti Organizzativi</th>
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
	<h2> Sintesi valutazione anno: ${valutazione.anno} per ${incarico.responsabile} </h2>
	<table id="tableSintesi">   
	<tbody>
	<tr>
		<th width= 50%></th>
		<th>Peso Attribuito</th>
		<th>Punteggio</th>
	</tr>
	<tr>
		<td>Raggiungimento Obiettivi Operativi (range 45-65)</td>
		<td>${totPesoApicaleObiettivi}</td>
		<td>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${totPunteggioApicaleObiettivi}
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
		<td>${totPesoApicaleObiettivi+valutazione.pdlAss}</td>
		<td>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${totPunteggioApicaleObiettivi+valutazione.pdlPunteggio}
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
		<td>Totale </td>
		<td>${totPesoApicaleObiettivi+valutazione.pdlAss+valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss}
		<c:if test="${(totPesoApicaleObiettivi+valutazione.pdlAss+valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss) != 100 }" > <font color="red">Peso errato !!! </c:if>
		</td>
		<td>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${totPunteggioApicaleObiettivi+valutazione.pdlPunteggio+valutazione.analPunteggio+valutazione.relazPunteggio+valutazione.gestPunteggio}
		
		" pattern="###.#"/>
		
		</td>
	</tr>
	</tbody>
	</table>
	
	</c:if>
	</c:forEach>
	


</div>


</section>
</article>
    
     <!-- Footer -------------------------->
  <div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_OIV" htmlEscape="true" />">Menu Oiv</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[view:listValutazioneIncaricoApicaleOiv.jsp 2017/10/12] </td>
    </tr>
  </table>
</div>
  
    
</div>    
</body>
</html>
