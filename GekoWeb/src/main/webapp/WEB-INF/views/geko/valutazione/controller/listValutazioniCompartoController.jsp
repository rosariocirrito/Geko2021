<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Prospetto Valutazioni Comparto del Dipartimento</title>
    <link href=<spring:url value="/resources/css/listaValut.css" htmlEscape="true" /> rel="stylesheet"/>
</head>
    
<body>
<article>
<section >  

<div id="wrapper">      
<p id="titolo-lista">Prospetto Valutazioni Comparto per <em>${dipartimento.denominazione} </em></p>   
<hr />      
        
        
        
<!-- Map<Incarico,Map<OpPersonaFisica,List<AzioneAssegnazione>>> mapDipartimentoAssegnazioni -->        
<c:if test="${empty mapDipartimentoAssegnazioni}">
     <p>Nessuna assegnazione trovata</p>
</c:if>


	
     
	<c:forEach items="${mapDipartimentoAssegnazioni}" var="mapItemIncAssegn" varStatus ="status2">
		<!-- mapItemIncAssegn.key è l'incarico -->
		<!-- filtro l'incarico -->
    	
    		<hgroup >
				<h3 >Struttura  ${mapItemIncAssegn.key.denominazioneStruttura} </h3>
				<h4 class="responsabile"> Responsabile  ${mapItemIncAssegn.key.responsabile} 
				   con incarico dal <fmt:formatDate value="${mapItemIncAssegn.key.dataInizio}" pattern="dd/MM/yyyy"/>
				   al <fmt:formatDate value="${mapItemIncAssegn.key.dataFine}" pattern="dd/MM/yyyy"/>
				   <c:if test="${mapItemIncAssegn.key.interim}">AD INTERIM</c:if>
				</h4>
			    </hgroup>
    
	  		<!-- mapItemIncAssegn.value è Map<PersonaFisica,List<AzioneAssegnazione> -->
			<c:forEach items="${mapItemIncAssegn.value}" var="mapItemPfAssegn" varStatus ="status3">
				<!-- nome dipendente -->
		   		<h3>${mapItemPfAssegn.key.stringa} </h3> 
		   		<!-- valutazione dei risultati -->
			    <table>
			    <thead>
				    <tr>
				        <th colspan="6"> <h3>VALUTAZIONE DEI RISULTATI &nbsp; anno: ${anno}</h3> </th>
				    </tr>   
				    <tr>
				        <th colspan="2">Obiettivo / Azione</th>
				        <th>Peso</th>
				        <th>Valutazione</th>
				        <th>Punteggio</th>
				        <th>Annotazioni</th>
				    </tr>	
				</thead>
				<tbody>
				
				<!--  mapItemPfAssegn.value -->
	    		<c:forEach items="${mapItemPfAssegn.value}" var="assegnazione" varStatus ="status4">
	    		
	    			<tr>
	         			<td colspan="2"> 
			                 ${assegnazione.azione.obiettivo.descrizione} /
			                 ${assegnazione.azione.descrizione} 
			                 <br/>
			                 (scadenza azione: <fmt:formatDate value="${assegnazione.azione.scadenza}" pattern="dd/MM/yyyy"/>)
			                 <br/>
			                 [assegnazione da: <fmt:formatDate value="${assegnazione.dataInizio}" pattern="dd/MM/yyyy"/>
			                 &nbsp; a: <fmt:formatDate value="${assegnazione.dataFine}" pattern="dd/MM/yyyy"/>]
			                
		             	 </td>
			             <td> 
			                 ${assegnazione.peso}
			             </td>
			             <td> 
			                 ${assegnazione.valutazione}
			             </td>
			             <td> 
			                 ${assegnazione.punteggio}
			             </td>
			             <td> 
			                 ${assegnazione.annotazioni}
			             </td>
	         		</tr>
	         	
	    		</c:forEach>
	    		
	    		<!-- totale dei risultati per la persona fisica -->
		 		<tr>
		 			<td colspan="2">totale risultati</td>
		         	<td>${mapItemPfAssegn.key.pesoAssegnazioni}</td>
		         	<td></td>
		            <td>${mapItemPfAssegn.key.punteggioAssegnazioni}</td>
		            <td> 
		                 <c:if test="${mapItemPfAssegn.key.punteggioAssegnazioni ge 49}">
		                 	punteggio risultati nella fascia 49 - 70 (->100%)
		                 </c:if>
		                 <c:if test="${(mapItemPfAssegn.key.punteggioAssegnazioni ge 35) && (mapItem.key.punteggioAssegnazioni lt 49)}">
		                 	punteggio risultati nella fascia 35 - 48 (->85%)
		                 </c:if>
		                 <c:if test="${mapItemPfAssegn.key.punteggioAssegnazioni lt 35}">
		                 	punteggio risultati inferiore a 35 (->70%)
		                 </c:if>
		            </td>
		         </tr>
	    		</tbody>
	    		</table>   
	    
	    
	    		<!-- Map<Incarico,Map<OpPersonaFisica,List<ValutazioneComparto>>> mapDipartimentoAssegnazioni --> 
				<c:forEach items="${mapDipartimentoValutazioneComparto}" var="mapItemIncValut" varStatus ="status5">
					
					    <!-- valutazione delle prestazioni -->
						<c:forEach items="${mapItemIncValut.value}" var="mapItemPfValut" varStatus ="status6">
					    	<c:if test="${mapItemPfValut.key == mapItemPfAssegn.key}">
						    <table>
							    <tr>
							        <th colspan="6"> <h3>Valutazione delle prestazioni &nbsp; anno: ${anno}</h3> </th>
							    </tr>
							    <tr>
							        <th colspan="2">Indicatore</th>
							        <th>Peso</th>
							        <th>Valutazione</th>
							        <th>Punteggio</th>
							        <th>Annotazioni</th>
							    </tr>
							    <tbody>
				    			<c:forEach items="${mapItemPfValut.value}" var="valutazioneComparto" varStatus ="status7">
								    <tr>
							         	<td colspan="2"> 
							                <p>Capacità di gestione della complessità e delle difficoltà</p>
							            </td>
							         	<td> 
							                 ${valutazioneComparto.complessDifficoltaAss}
							             </td>
							             <td> 
							                 ${valutazioneComparto.complessDifficoltaVal}
							             </td>
							             <td> 
							                 ${valutazioneComparto.complessDifficPunteggio}
							             </td>
							             <td> 
							                 ${valutazioneComparto.annot1}
							             </td>
							        </tr>
							        <tr>
							         	<td colspan="2"> 
							                <p> Competenze tecnico-professionali</p>
							            </td>
							         	<td> 
							                 ${valutazioneComparto.competProfAss}
							             </td>
							             <td> 
							                 ${valutazioneComparto.competProfVal}
							             </td>
							             <td> 
							                 ${valutazioneComparto.competProfPunteggio}
							             </td>
							             <td> 
							                 ${valutazioneComparto.annot2}
							             </td>
							         </tr>
		         
							         <tr>
							         	<td colspan="2"> 
							                <p> Capacità di promuovere e gestire l'innovazione</p>
							            </td>
							         	<td> 
							                 ${valutazioneComparto.innovazAss}
							             </td>
							             <td> 
							                 ${valutazioneComparto.innovazVal}
							             </td>
							             <td> 
							                 ${valutazioneComparto.innovazPunteggio}
							             </td>
							             <td> 
							                 ${valutazioneComparto.annot3}
							             </td>
							         </tr>
		         
							 		 <tr>
								 		<td colspan="2"> 
								             totale prestazioni  
							            </td>
							 			<td>
							         		${valutazioneComparto.totPeso}
							         	</td>
							         	<td>
							         		
							         	</td>
							         	<td> 
							                 ${valutazioneComparto.totPunteggio}
							            </td>
								        <td> 
								        	 <c:if test="${valutazioneComparto.totPunteggio ge 21}">
							                 	punteggio prestazioni nella fascia 21 - 30 (->100%)
							                 </c:if>
							                 <c:if test="${(valutazioneComparto.totPunteggio ge 15) && (valutazioneComparto.totPunteggio lt 20)}">
							                 	punteggio prestazioni nella fascia 15 - 20 (->85%)
							                 </c:if>
							                 <c:if test="${valutazioneComparto.totPunteggio lt 15}">
							                 	punteggio prestazioni inferiore a 15 (->70%)
							                 </c:if>
							            </td>
							        </tr>
		        
							        <tr>
								        <th colspan="6"> <h3>Valutazione finale &nbsp; anno: ${anno}</h3> </th>
								    </tr> 
							        <tr>
								 		<td colspan="2"> 
								             totale generale  
							            </td>
							 			<td>
							         		 ${mapItemPfValut.key.pesoAssegnazioni + valutazioneComparto.totPeso}
							         	</td>
							         	<td>
							         		
							         	</td>
							         	<td> 
							                 ${mapItemPfValut.key.punteggioAssegnazioni + valutazioneComparto.totPunteggio}
							            </td>
								        <td> 
							                 
							            </td>
							        </tr>
							        <tr>
								 		<td colspan="4"> 
								             <h5>Percentuale indennità spettante ==></h5> 
							            </td>
							 			
							         	<td> 
							         	
							         	<c:choose>
										    <c:when test="${(mapItemPfValut.key.punteggioAssegnazioni ge 49) && (valutazioneComparto.totPunteggio ge 21)}">
										       100%
										    </c:when>
										    <c:when test="${(mapItemPfValut.key.punteggioAssegnazioni ge 35) && (valutazioneComparto.totPunteggio ge 15)}">
										        85%
										    </c:when>
										    <c:otherwise>
										        70%
										    </c:otherwise>
										</c:choose>
						
							            </td>
								        <td> 
							                 
							            </td>
							        </tr>
		        				<!-- ValutazioneComparto 7 --> 
		        				</c:forEach>
	    						</tbody>
	    					</table>       
	    				</c:if>
	    			<!-- valutazione delle prestazioni 6 -->	
	    			</c:forEach>
				
			<!-- mapDipartimentoValutazioneComparto 5 -->    
			</c:forEach>

		<!-- mapItemPfAssegn.value 3 -->	
		</c:forEach>


	<!-- mapItemIncAssegn 2 -->
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
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:listValutazioniCompartoController.jsp 02/05/2016] </td>
	</tr>
  </table>
</div>
  
    
    
    
    </body>
</html>
