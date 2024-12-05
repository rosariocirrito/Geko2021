<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<!-- <meta charset="ISO-8859-1" />  -->
    <meta charset="UTF-8" />
    <title>Modifica Valutazione Incarichi Pop della Struttura</title>
    <%@ include file= "/WEB-INF/views/common/bootstrap4_css.jsp" %>
</head>
    
<body>
<div class="container-fluid" >
<div class="row">
	<div class="col-lg-12 col"> 
	    
	<h3>Valutazione Incarichi Pop per <em>${struttura} </em>- anno ${anno}</h3> 
	<hr />  
	
	<c:if test="${empty listIncarichiPop}"><p>Nessun incarico trovato.</p></c:if>
	</div>
</div>

<c:forEach items="${listIncarichiPop}" var="inc" varStatus ="status">
	<div class="row">
		<div class="col-lg-12 col"> 
		<hgroup >            
		<h4 >Struttura  ${inc.denominazioneStruttura}</h4>
		<h4 >${inc.responsabile}</h4>
		
		</hgroup>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 col"><h4>Performance operativa</h4></div>
	</div>
	<hr />
	<!-- 1. itero sugli obiettivi ------------------------------------------------------->
	<c:forEach items="${inc.obiettivi}" var="obj" varStatus ="status">
		<!-- 1.1 tabella obiettivi ------------------------------------------------------->
		<div class="row rowobj">
			<div class="col-1 colObj"><p>Codice</p></div>
			<div class="col-7 colObj"><p>Descrizione Obiettivo</p></div>
			<div class="col-2 colObj"><p>Peso</p></div>	
			<div class="col-2 colObj"><p>Punteggio</p></div>	
		 </div>	
		<div class="row ">				
			<div class="col-1 colObj"><p>${obj.codice}</p></div>
			<div class="col-7 colObj"><p>${obj.descrizione} <c:if test="${obj.note> ''}">- ${obj.note}</c:if></p></div>	    
			<div class="col-2 colObj"><p>${obj.peso} </p></div>
			<div class="col-2 colObj"><p>${obj.punteggio}</p></div>
		</div>	
		
			<!-- 1.1.1 iterazione su obiettivi per visualizzare azioni ------------------------------->
    		
    		<c:forEach items="${obj.azioni}" var="act">
	    		<div class="row rowact">			    		
					<div class="col-1 colAct"><p>Codice</p></div>
					<div class="col-7 colAct"><p>Azione</p></div>
					<div class="col-1 colAct"><p>Indicatore</p></div>
					<div class="col-1 colAct"><p>Valore</p></div>
					<div class="col-1 colAct"><p>Scadenza</p></div>
					<div class="col-1 colAct"><p>peso</p></div>
				</div>				
        		<div class="row">
					<div class="col-1 colAct">${act.denominazione}</div>        
        			<div class="col-7 colAct">${act.descrizione}
				        <c:if test="${act.note>''}">${act.note}</c:if>
				    </div>  
            		<div class="col-1 colAct">${act.indicatore}</div>
        			<div class="col-1 colAct">${act.prodotti}</div>       
			        <div class="col-1 colAct">
				        <fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
			        </div>
        			<div class="col-1 colAct">${act.peso}</div>
        		</div>
        		<div class="row rowact">
	        		<div class="col-2 offset-1 colAct"><p>Risultato</p></div>
					<div class="col-6 colAct"><p>Documenti</p></div>
					<div class="col-2 colAct"><p>Completamento</p></div>
					<div class="col-1 colAct"><p>Punteggio</p></div>
				</div>
				<div class="row">
					<div class="col-2 offset-1 colAct"><p>${act.risultato}</p></div>
					<div class="col-6 ">
						<p><c:forEach items="${act.documenti}" var="docu">
		                    <a class="btn btn-info" href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />" role="button">${docu.nomefile}</a>
		                   </c:forEach>
		                 <br/></p>
					</div>
					<div class="col-2 ">${act.completamento}</div>
					<div class="col-1 ">
						<p>${act.punteggio}</p>
					</div>
				</div>
    		</c:forEach> <!-- fine for azioni -->
    		
   	</c:forEach> <!-- inc.obiettivi --> 
		
	<hr /> 
	<c:forEach items="${inc.valutazioni}" var="valutazione" >	
		<div class="row">
			<div class="col-6 col ">Comportamenti Organizzativi</div>
			<div class="col-2 col ">Peso</div>
			<div class="col-2 col ">Valutazione	</div>
			<div class="col-2 col">Punteggio</div>
		</div>
		
		<div class="row">
			<div class="col-6 col">Promozione di strumenti di analisi e/o adozione di nuove metodologie di semplificazione</div>
			<div class="col-2 col">${valutazione.analProgrAss}</div>
			<div class="col-2 col">${valutazione.analProgrVal}</div>
			<div class="col-2 col">${valutazione.analPunteggio}</div>
		</div>
		<div class="row">
			<div class="col-6 col">Capacità di individuazione del livello di priorità degli interventi da realizzare</div>
			<div class="col-2 col">${valutazione.pdlAss}</div>
			<div class="col-2 col">${valutazione.pdlVal}</div>
			<div class="col-2 col">${valutazione.pdlPunteggio}</div>
		</div>			
		<div class="row">
			<div class="col-6 col ">Capacità di organizzazione del lavoro</div>
			<div class="col-2 col">${valutazione.relazCoordAss}</div>
			<div class="col-2 col">${valutazione.relazCoordVal}</div>
			<div class="col-2 col">${valutazione.relazPunteggio}</div>
		</div>
		<hr />
		
		<div class="row">
			<div class="col-10 offset-1 col"><h3>Sintesi Valutazione</h3></div>
		</div>			
		<div class="row">		
			<div class="col-6 offset-1 col ">Totali</div>	
			<div class="col-2 col ">Peso Attribuito</div>
			<div class="col-2 col ">Punteggio</div>
		</div>
		
		<div class="row">
			<div class="col-6 offset-1 col ">Performance Operativa (peso = 70)</div>
			<div class="col-2 col ">${inc.totPesoObiettivi}</div>
			<div class="col-2 col "><fmt:formatNumber type="number" maxFractionDigits="1" minFractionDigits="1" value="
				${inc.totPunteggioObiettivi} 	" pattern="###.#"/>
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
		
		
	</c:forEach> <!-- inc.valutazioni -->
	
	<c:if test="${empty inc.valutazioni}">
	<p><a href="<spring:url value="/managerCompOrgPop/new/${inc.idIncarico}/${anno}" htmlEscape="true" />">Aggiungi sezione Comportamenti Organizzativi per ${inc.responsabile} </a></p>
	</c:if>
	<hr /> 
                  
</c:forEach><!-- listIncarichiPop -->
    
                
<!-- Footer -------------------------->

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	
</div>

<%@ include file= "/WEB-INF/views/common/bootstrap4_js.jsp" %>





