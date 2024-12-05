<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
        <title>Modifica Valutazioni Dirigenziali del Dipartimento</title>
    <%@ include file= "/WEB-INF/views/common/bootstrap4_css.jsp" %>
</head>
    
<body>
<div class="container-fluid" >
<div class="jumbotron" > 

    
<h1>Modifica Valutazioni Dirigenziali per <em>${dipartimento.denominazione} </em></h1>   
<hr />      
        
<c:if test="${empty listIncarichiDept}">
     <p>Nessun incarico trovato.</p>
</c:if>

            

<c:forEach items="${listIncarichiDept}" var="incarico" varStatus ="status">

<div class="row">
	<div class="col-12 col">    
	
	<h3>Modifica Valutazione dirigenziale Obiettivi per incarico <em>${incarico.stringa} </em>- anno ${anno}</h3>     	
	</div>
</div>	


	<script type="text/javascript">
		var esisteGia = false;
	</script>
	<div id="incarico">
	 
	
    <c:forEach items="${incarico.obiettivi}" var="obj" varStatus ="statusObj">
	<div class="row">
	<div class="col-12 col">    
	
	<h4>Performance Operativa</h4>     	
	</div>
	</div>	
		<hr />
	<div class="row rowobj">
		<div class="col-1 colobj"> 
			<p>Codice</p>
		</div>
		<div class="col-7 colobj"> 
			<p>Descrizione Obiettivo Operativo</p>
		</div>
		<div class="col-2 colobj">
			<p>Peso</p>
		</div>
		<div class="col-2 colobj">
			<p>Punteggio</p>
		</div>
	</div>
	
	<div class="row rowobj">
		<div class="col-1 colobj"> 
			<p>${obj.codice}</p>
		</div>
		<div class="col-7 colobj"> 
			<p>${obj.descrizione}</p>
		</div>
		<div class="col-2 colobj">
			<p>${obj.peso}</p>
		</div>
		<div class="col-2 colobj">
			<p>${obj.punteggio}</p>
		</div>
	</div>
		
	<!-- azioni -->
	<c:forEach items="${obj.azioni}" var="act">
		<div class="row rowact">
			<div class="col-1 colact"> 
				<p>Codice</p>
			</div>
			<div class="col-7 colact"> 
				<p>Azione</p>
			</div>
			<div class="col-1 colact">
				<p>Indicatore</p>
			</div>
			<div class="col-1 colact">
				<p>Valore Ob.</p>
			</div>
			<div class="col-1 colact"> 
				<p>Scadenza</p>
			</div>
			<div class="col-1 colact">
				<p>Peso</p>
			</div>
		</div>
		<div class="row ">
			<div class="col-1 "> 
				<p>${act.denominazione}</p>
			</div>
			<div class="col-7 "> 
				<p>${act.descrizione}</p>
			</div>
			<div class="col-1 ">
				<p>${act.indicatore}</p>
			</div>
			<div class="col-1 ">
				<p>${act.prodotti}</p>
			</div>
			<div class="col-1 "> 
				<p><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/></p>
			</div>
			<div class="col-1 ">
				<p>${act.peso}</p>
			</div>
		</div>	
		<div class="row">
			<div class="col-2 offset-1 colact"> 
				<p>Risultato</p>
			</div>
			<div class="col-6 colact"> 
				<p>Documenti</p>
			</div>
			<div class="col-2 colact"> 
				<p>Completamento</p>
			</div>
			<div class="col-1 colact"> 
				<p>Punteggio</p>
			</div>
		</div>
		<div class="row">
			
			<div class="col-2 offset-1 ">
				<p>${act.risultato}</p>
			</div>
			<div class="col-6 ">
				<p><c:forEach items="${act.documenti}" var="docu">
                    <a class="btn btn-info" href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />" role="button">${docu.nomefile}</a>
                   </c:forEach>
                 <br/></p>
			</div>
			<div class="col-2 ">
				
				<a class="btn btn-info" href="<spring:url value="/controllerAct/${act.idAzione}/editCompletamento" htmlEscape="true" />"role="button"> Edit </a>${act.completamento}
				
			</div>
			<div class="col-1 ">
				<p>${act.punteggio}</p>
			</div>
		</div>
	</c:forEach> <!-- azioni -->
	
</c:forEach> <!-- obiettivi -->
	
	<hr>

	
	<c:forEach items="${incarico.valutazioni}" var="valutazione" >	
	
	<script type="text/javascript">
			esisteGia = true;
		</script>
		
		
		<!-- Comportamenti organizzativi -->
		<div class="row">
		<div class="col-6 col ">Comportamenti Organizzativi</div>
		<div class="col-2 col ">
		<a class="btn btn-info" href="<spring:url value="/controllerCompOrg/${valutazione.id}/editPesiComportOrgan" htmlEscape="true" />"role="button">Edit Peso </a>
		</div>
		<div class="col-2 col ">
			<a class="btn btn-info" href="<spring:url value="/controllerValutazione/${valutazione.id}/editValutComportOrgan" htmlEscape="true" />"role="button">Edit Valutazione </a>
		</div>
		<div class="col-2 col ">
			Punteggio
		</div>
		</div>
		
		<div class="row">
			<div class="col-6 ">Capacità di intercettare, gestire risorse e programmare</div>
			<div class="col-2 ">
				${valutazione.gestRealAss}
			</div>
			<div class="col-2 ">
				${valutazione.gestRealVal}
			</div>
			<div class="col-2 ">
				${valutazione.gestPunteggio}
			</div>
		</div>
		<div class="row">
			<div class="col-6 ">
				Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione
			</div>
			<div class="col-2 ">
				${valutazione.analProgrAss}
			</div>
			<div class="col-2 ">
				${valutazione.analProgrVal}
			</div>
			<div class="col-2 ">
				${valutazione.analPunteggio}
			</div>
		</div>
		<div class="row">
			<div class="col-6 ">
				Capacita' di valorizzare competenze ed attitudini dei propri collaboratori
			</div>
			<div class="col-2 ">
				${valutazione.relazCoordAss}
			</div>
			<div class="col-2 ">
				${valutazione.relazCoordVal}
			</div>
			<div class="col-2 ">
				${valutazione.relazPunteggio}
			</div>
		</div>
		<div class="row">
			<div class="col-6 ">
				Capacità di individuazione del livello di priorità degli interventi da realizzare
			</div>
			<div class="col-2 ">
				${valutazione.pdlAss}
			</div>
			<div class="col-2 ">
				${valutazione.pdlVal}
			</div>
			<div class="col-2 ">
				${valutazione.pdlPunteggio}
			</div>
		</div>
		
		
		<!-- sintesi finale -->
		<hr>
		<div class="row">
			<div class="col-10 offset-1 col ">
				<h3> Sintesi valutazione anno: ${valutazione.anno} per ${incarico.responsabile} </h3>
			</div>
		</div>
		
		<div class="row">		
			<div class="col-6 offset-1 col ">Totali</div>	
			<div class="col-2 col ">Peso Attribuito</div>
			<div class="col-2 col ">Punteggio</div>
		</div>
		
		<div class="row">
			<div class="col-6 offset-1 col ">Performance Operativa (peso = 70)</div>
			<div class="col-2 col ">${incarico.totPesoObiettivi}</div>
			<div class="col-2 col "><fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
				${incarico.totPunteggioObiettivi} 	" pattern="###.#"/>
			</div>
		</div>
		
		
		<div class="row">
			<div class="col-6 offset-1 col ">
				Comportamento Organizzativo ( peso = 30)
			</div>
			<div class="col-2 col ">
				${valutazione.gestRealAss+valutazione.analProgrAss+valutazione.relazCoordAss+valutazione.gestRealAss+valutazione.pdlAss}
			</div>
			<div class="col-2 col ">
				<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
				${valutazione.gestPunteggio+valutazione.analPunteggio+valutazione.relazPunteggio+valutazione.pdlPunteggio} " pattern="###.#"/>
			</div>
		</div>
		<div class="row">
			<div class="col-6 offset-1 col ">
				Performance individuale
			</div>
			<div class="col-2 col ">
				${valutazione.totPeso}
				<c:if test="${valutazione.totPeso != 100 }" > <font color="red">Peso errato !!! <font color="black"></c:if>
			</div>
			<div class="col-2 col ">
			<c:if test="${valutazione.totPeso != 100 }" ><span> <font color="red"> errato !!! <font color="black"> </span></c:if>
			<fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
				${valutazione.totPunteggio} 		" pattern="###.#"/>
				
			</div>
		</div>
		<div class="row">
			<div class="col-11 offset-1  ">
		<br /> <hr /> <br />
		</div>
		</div>
		
	
	</c:forEach>
	<c:if test="${empty incarico.valutazioni}">
	<p><a href=\"../../../controllerCompOrg/new/${inc.idIncarico}/${anno}>Aggiungi sezione Comportamenti Organizzativi per ${inc.responsabile} </a></p>
	
	</c:if>
	
	
	<!-- chiude divisione incarico -->
	</div>
</c:forEach>




    
<div class="row">
<div class="col-12 col ">
<hr/>
Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito [${pageName}] [ultima modifica: 2019/11/28]
<hr/>
</div>
</div>

 <%@ include file="/WEB-INF/views/common/footer.jsp" %>  
 
</div>
</div> <!-- container -->   

<script src="<spring:url value="/resources/bootstrap4.0.0/js/bootstrap.js" htmlEscape="true" />"></script>
</body>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</html>