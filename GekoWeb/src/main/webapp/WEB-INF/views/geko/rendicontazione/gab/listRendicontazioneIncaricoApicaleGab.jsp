<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Rendicontazione Obiettivi della Struttura del Dirigente Generale</title>
	<link href="<spring:url value="/resources/bootstrap336/css/bootstrap.min.css" htmlEscape="true" />" rel="stylesheet"/>
    <style>
		.row{
			margin-top:16px;
			margin-left:8px;
		}
		.col{
			padding:8px;
			
		}
	</style>

</head>
    
<body>


<body>
<div class="container" >
<div class="jumbotron">
<div class="row">
<div class="col-lg-12 col"> 

 
<h2 > Rendicontazione Obiettivi Apicali per dipartimento: <em>${struttura} </em>- anno ${anno}</h2>
<h4>Responsabile: ${responsabile}</h4>   
        
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
	<div class="col-lg-1 col" >
		<h3>ID</h3>
		<h4>${obj.codice}</h4> [${obj.idObiettivo}]
	</div>
	<div class="col-lg-8 col">
		<h3>Obiettivo</h3>
		<h4>
		
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
		<c:if test= "${obj.pesoApicale >0}"> ${obj.pesoApicale} </c:if>
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
		<div class="col-lg-1 col"><h4>indicatore previsto</h4>
			${act.indicatore}
		</div>
		<div class="col-lg-1 col"><h4>valore obiettivo</h4>
			${act.prodotti}
		</div>
		<div class="col-lg-1 col"><h4>scadenza</h4>
			<fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
		</div>
		<div class="col-lg-1 col"><h4>peso</h4>
			<c:if test="${act.pesoApicale >0 }" >${act.pesoApicale}</c:if>
		</div>
		<div class="col-lg-2 col"><h4>risultato</h4>
			${act.risultato}
		</div>
	</div> <!-- fine riga azione -->

	<!--  righe documenti -->
	<c:if test="${!empty act.documenti}">
	<div class="row" style="background: lightyellow;">
		<div class="col-lg-1 col"><h4>ID</h4></div>		
		<div class="col-lg-1 col"><h4>Data Creazione</h4></div>
		<div class="col-lg-3 col"><h4>Nome Documento</h4></div>
		<div class="col-lg-3 col"><h4>Descrizione estesa</h4></div>
		<div class="col-lg-4 col"><h4>Nome File</h4></div>
    </div>		        
	<c:forEach items="${act.documenti}" var="docu">
		
		<div class="row" style="background: lightyellow;">
			<div class="col-lg-1 col">[${docu.id}]</div>
			<div class="col-lg-1 col"><fmt:formatDate value="${docu.creato}" pattern="dd/MM/yyyy"/></div>
			<div class="col-lg-3 col">
           	<a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nome} </a>
         	</div>
         	<div class="col-lg-3 col">${docu.descrizione}</div>
         	<div class="col-lg-4 col">${docu.nomefile}</div>
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



<!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_GABINETTO" htmlEscape="true" />">Menu Gabinetto</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
      <td>[view: listRendicontazioneIncaricoApicaleGab.jsp 2019/07/09] </td>
    </tr>
</table>
</div>
  
 </div>
 </div>
    
    
</div>     
</body>
</html>
