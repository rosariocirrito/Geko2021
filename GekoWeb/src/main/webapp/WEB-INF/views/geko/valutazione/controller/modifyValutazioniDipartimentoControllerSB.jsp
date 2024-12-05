<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
        <title>Modifica Valutazioni Dirigenziali del Dipartimento</title>
    <link href="../../../resources/css/listaValut.css" rel="stylesheet"/>

</head>
    
<body>
<article>
<section >  

<div id="wrapper">      
<p id="titolo-lista">Modifica Valutazioni Dirigenziali per <em>${dipartimento.denominazione} </em>
</p>   
<hr />      
        
<c:if test="${empty listIncarichiDept}">
     <p>Nessun incarico trovato.</p>
</c:if>

            

<c:forEach items="${listIncarichiDept}" var="incarico" varStatus ="status">
	<script type="text/javascript">
		var esisteGia = false;
	</script>
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
      		<th class="prod" width="5%">Indicatore</th>
            <th class="prod" width="10%">Valore Obiettivo</th>
            
            <th class="prod" width="5%">Scadenza</th>
            <th class="prod" width="5%">Peso</th>
            <th class="prod" width="15%">Risultato</th>
            <th class="prod" width="15%">Documenti</th>
            <th class="prod" width="10%">Completamento</th>
            <th class="prod" width="10%">Punt.</th>
        </tr>
      	<c:forEach items="${obj.azioni}" var="act">
	      	
	      	<tr>    
	            <td>${act.descrizione}</td>
	            <td>${act.indicatore}</td>
	            <td>${act.prodotti}</td>
	            
	            <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/></td>
	            <td>${act.peso}</td>
	            <td>${act.risultato}</td>
	            <td>
		            <c:forEach items="${act.documenti}" var="docu">

	                    <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>
	                 <br/>
	                 </c:forEach>
		            </td>
	            <td><a href="../../../controllerAct/${act.idAzione}/editCompletamento"> Edit</a>${act.completamento}</td>
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
	
		
	
	<!-- Tabella dei comportamenti organizzativi -->
	<table id="tableComport">   
	<tbody>
	
	<tr>
				<th>Comportamenti Organizzativi</th>
				<th><a href="../../../controllerCompOrg/${valutazione.id}/editPesiComportOrgan">Edit</a>Peso Assegnato (0 o intero>=5)</th>
				<th><a href="../../../controllerValutazione/${valutazione.id}/editValutComportOrgan">Edit</a>Valutazione</th>
		<th>Punteggio</th>
			</tr>
			<tr>
		    	<td>Capacità di intercettare, gestire risorse e programmare</td>
				<td>${valutazione.gestRealAss}</td>
				<td>${valutazione.gestRealVal}</td>
				<td>${valutazione.gestPunteggio}</td>
			</tr>
			
			<tr>
		    	<td>Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione</td>
				<td>${valutazione.analProgrAss}</td>
				<td>${valutazione.analProgrVal}</td>
				<td>${valutazione.analPunteggio}</td>
			</tr>
			<tr>
		    	<td>Capacita' di valorizzare competenze ed attitudini dei propri collaboratori</td>
				<td>${valutazione.relazCoordAss}</td>
				<td>${valutazione.relazCoordVal}</td>
				<td>${valutazione.relazPunteggio}</td>
			</tr>
			
			<tr>
		    	<td>Capacità di individuazione del livello di priorità degli interventi da realizzare</td>
				<td>${valutazione.pdlAss}</td>
				<td>${valutazione.pdlVal}</td>
				<td>${valutazione.pdlPunteggio}</td>
			</tr>
			<tr>
				<th >Sintesi</th>
				<th>Peso Attribuito</th>
				<th colspan = 2>Punteggio</th>
			</tr>
			<tr>
				<td>Totale programmato Performance operativa (= 70)</td>
				<td>${incarico.totPesoObiettivi}</td>
				<td colspan = 2>
					<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
					${incarico.totPunteggioObiettivi}
					" pattern="###.#"/>
				</td>
			</tr>		
			<tr>
				<td>Totale programmato Comportamento Organizzativo (= 30)</td>
				<td>${valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss+valutazione.pdlAss}</td>
				<td colspan = 2>
					<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
					${valutazione.analPunteggio+valutazione.relazPunteggio+valutazione.gestPunteggio}
					" pattern="###.#"/>
				</td>
			</tr>
			<tr>
		<td>Totale conseguibile (=100)</td>
		<td>${valutazione.totPeso}
		<c:if test="${valutazione.totPeso != 100 }" > <font color="red">Peso errato !!! </c:if>
		</td>
		<td colspan = 2>
		<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
		${valutazione.totPunteggio}
		" pattern="###.#"/>
		<c:if test="${valutazione.totPeso != 100 }" > <font color="red">Punteggio errato !!! </c:if>
		</td>
	</tr>
	</tbody>
	</table>
	
	<!-- Tabella sintesi finale -->
	<hr>
	
	
	
	</c:forEach>
	<c:if test="${empty incarico.valutazioni}">
	<p><a href=\"../../../controllerCompOrg/new/${inc.idIncarico}/${anno}>Aggiungi sezione Comportamenti Organizzativi per ${inc.responsabile} </a></p>
	
	</c:if>
	
	
	<!-- chiude divisione incarico -->
	</div>
</c:forEach>
<!-- chiude divisione wrapper -->
</div>
</section>
</article>
    
     <!-- Footer -------------------------->
  <div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - 
      ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:modifyValutazioniDipartimentoController.jsp 2017/02/23] </td>
	</tr>
  </table>
</div>
  
</body>
</html>
