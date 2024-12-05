<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Programmazione Obiettivi della Struttura del Dirigente</title>
	<!-- Bootstrap -->
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap.min.css"  htmlEscape="true" /> rel="stylesheet"/>
	<link href=<spring:url value="/resources/bootstrap336/css/bootstrap-theme.min.css"  htmlEscape="true" /> rel="stylesheet"/>
	
	
	<style>
		#wrapCont {
		padding-top: 25px;
		padding-bottom: 25px;
		padding-right: 5px;
		padding-left: 5px;
		
		}
		
		#wrapCol1 {
		padding-top: 25px;
		padding-bottom: 25px;
		padding-right: 5px;
		border-style: solid;
		border-color: lightgray;
		background: lightgreen;
		}
		
		#wrapCol2 {
		padding-top: 25px;
		padding-bottom: 25px;
		padding-right: 5px;
		border-style: solid;
		border-color: lightgray;
		background: lightblue;
		}
		
	</style>
</head>
    
<body>
    
<div class="container" id="wrapCont">
<div class="row">
<div class="col-xl-2" id="wrapCol1" >
<c:if test="${empty listIncarichiDept}">
	<p>Nessun incarico trovato</p>
</c:if>
<ol>
<c:forEach items="${listIncarichiDept}" var="incarico" varStatus ="status">
<li>
<a href="<spring:url value="/controllerRend/navigaRendicontazioneIncaricoController/${anno}/${incarico.idIncarico}" htmlEscape="true" />">
		
${incarico.stringa}</a>
</li>
</c:forEach>  
</ol>  
</div>
<div class="col-xl-10" id="wrapCol2" >
    
<div class="jumbotron">
<div class="row">
<div class="col-xl-12 col"> 

 
<h4 > Rendicontazione Obiettivi per: <em>${incarico.stringa} </em>- anno ${anno}</h4>
 
        
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
</div>           
</div>  
</div>
        
<!-- per ogni obiettivo --> 
<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
<div class="jumbotron">


<!-- riga obiettivo -->
<div class="row" style="background: lightgreen;">
	<div class="col-lg-2 col" >
		<h3>ID</h3>
		<h4>${obj.codice}</h4> [${obj.idObiettivo}]
	</div>
	<div class="col-lg-7 col">
		<h3>Obiettivo</h3>
		<h4>
		<c:if test="${obj.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
		    Descr_Proposta - <em> ${obj.descrizioneProp} </em> <br />    
	    </c:if>
	    <c:if test="${obj.apicale}">APICALE</c:if>  
	    	${obj.descrizione} 
	    </h4>	
	    <c:if test="${obj.note> ''}"> 
	    	<br />
	    	<em>nota:${obj.note}</em>
	    </c:if>
	</div>
	<div class="col-lg-2 col">
		<h3>stato</h3>${obj.statoApprov}
	</div>
	<div class="col-lg-1 col">
		<h3>peso</h3>
		<c:if test= "${obj.peso >0}"> ${obj.peso} </c:if>
	</div>
</div>	<!-- chiudi riga obiettivo -->

<!-- per ogni azione -->
	<c:forEach items="${obj.azioni}" var="act">
		<!-- riga azione -->
		<div class="row" style="background: lightblue;">
			<div class="col-lg-1 col" >
			<h4>ID</h4>
			${act.denominazione} [${act.idAzione}]
		</div>
		<div class="col-lg-5 col"><h4>Azione</h4>			
			<c:if test="${act.descrizioneProp>'' && obj.statoApprov == 'INTERLOCUTORIO' }">       
			        ${act.descrizioneProp}
		    </c:if>
	       	${act.descrizione}
	       	
		<c:if test="${act.note>''}">
			<em>nota: ${act.note} </em>
		</c:if>
		</div>
		<div class="col-lg-1 col"><h4>indicat. previsto</h4>
			${act.indicatore}
		</div>
		<div class="col-lg-1 col"><h4>valore obiettivo</h4>
			${act.prodotti}
		</div>
		<div class="col-lg-1 col"><h4>scadenza</h4>
			<fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
		</div>
		<div class="col-lg-1 col"><h4>peso</h4>
			<c:if test="${act.peso >0 }" >${act.peso}</c:if>
		</div>
		<div class="col-lg-2 col"><h4>risultato</h4>
			${act.risultato}
		</div>
	</div> <!-- fine riga azione -->

	<!--  righe documenti -->
	<c:if test="${!empty act.documenti}">
	<div class="row" style="background: lightyellow;">
		<div class="col-lg-1 col"><h4>ID</h4></div>
		<div class="col-lg-3 col"><h4>Nome Documento</h4></div>
		<div class="col-lg-8 col"><h4>Descrizione estesa</h4></div>
		
    </div>		        
	<c:forEach items="${act.documenti}" var="docu">
		
		<div class="row" style="background: lightyellow;">
			<div class="col-lg-1 col">[${docu.id}]</div>
			<div class="col-lg-3 col">
           	<a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nome} </a>
         	</div>
         	<div class="col-lg-8 col">${docu.descrizione}</div>
         	
        </div>
     </c:forEach>
     </c:if>
      <!-- fine righe documenti -->
        
        <!-- criticità -->
        <c:if test="${!empty act.criticita}">
        <div class="row" style="background: orange;">
			<div class="col-lg-4 col"><h4>criticità</h4></div>
			<div class="col-lg-4 col"><h4>proposte</h4></div>
			<div class="col-lg-4 col"><h4>indicazioni</h4></div>
			<c:forEach items="${act.criticita}" var="critic">
				<div class="row" style="background: orange;">
				<div class="col-lg-4 col">${critic.descrizione}</div>
				<div class="col-lg-4 col">${critic.proposte}</div>
				<div class="col-lg-4 col">${critic.indicazioni}</div>	
				</div>			
		    </c:forEach>
		</div>
		</c:if>

	</c:forEach> <!-- fine loop azioni -->

	</div>
</c:forEach> <!-- fine loop obiettivo -->






</div>
    
<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[view: navigaRendicontazioneIncaricoController.jsp 2019/06/18] </td>
    </tr>
</table>
</div>
  
 
 </div>
    
    
</div>  

  	
	   
</body>
</html>
