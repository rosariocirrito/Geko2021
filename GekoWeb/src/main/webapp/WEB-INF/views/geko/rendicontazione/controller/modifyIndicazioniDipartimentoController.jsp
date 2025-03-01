<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Lista Rendicontazione Obiettivi del Dipartimento</title>
    <link href="../../resources/css/lista.css" rel="stylesheet"/>
</head>
    
<body>
<div id="wrapper">
<article>
<section id="obj-sezione"> 
    
<h1 id="titolo-lista">Rendicontazione Obiettivi per <em>${dipartimento.denominazione} </em>- anno ${anno}</h1> 
<hr />  

<c:if test="${empty mapObiettiviDept}">
    <p>Nessun obiettivo trovato per il dipartimento.</p>
</c:if>

<!--  mapObiettiviDept<Struttura,List<Obiettivi>> -->
<c:forEach items="${mapObiettiviDept}" var="mapItem">

<div id="wrapper2">

<h3>${mapItem.key.opPersonaGiuridica.denominazione}</h3>
<h4>${mapItem.key.opPersonaFisica.stringa}</h4>

    <c:forEach items="${mapItem.value}" var="obj" varStatus ="status">
        <div id="${obj.tipo}">
        <hgroup >            
        <h3 >
        	${obj.codice} - <em>&nbsp; ${obj.descrizione} </em>
    	</h3>
        
        <c:if test="${obj.note}"><h4>${obj.note}</h4></c:if>    
        
        <h4>
	    <c:if test="${obj.tipo == 'OBIETTIVO'}">
	    Priorit�      
	    <c:if test="${obj.priorita == 'ALTA'}"> ALTA</c:if>
	    <c:if test="${obj.priorita == 'BASSA'}"> BASSA</c:if>
	    </c:if>
	     &nbsp;&nbsp; stato: ${obj.statoApprov} &nbsp;&nbsp; 
		<c:if test= "${obj.peso >0}"> peso: ${obj.peso} </c:if> 
	    </h4>  
        </hgroup>
            
            <c:forEach items="${obj.azioni}" var="act">
            	<table>
                <tbody>
					<tr>
			            <th class="prod" width="5%">Indicatore</th>
			            <th class="prod" width="20%">Valore Obiettivo</th>
			            <th class="prod" width="5%">Scadenza</th>
			            <c:if test="${act.peso >0 }" > 
			            	<th class="prod" width="5%">Peso</th>
			            </c:if>
			            <th class="prod" width="15%">Risultato</th>
			            <th class="prod" width="10%">Documenti</th>
			            <th class="prod" width="20%">
			                <c:if test="${obj.statoApprov == 'DEFINITIVO'}" >
			                    <a href="../../controllerCritic/new/${act.idAzione}">Nuova </a>
			                </c:if>
			                Criticit�
			            </th>
			            <th class="prod" width="20%">Proposte</th>
			            <th class="prod" width="20%">Indicazioni</th>
			        </tr>

                    <tr>    
			            <td>${act.indicatore}</td>
			            <td>${act.prodotti}</td>
			            <td><fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/></td>
			            <c:if test="${act.peso >0 }" > 
			            	<td>${act.peso}</td>
			            </c:if>
			            <td>${act.risultato}</td>
			            <td> 
			                <c:forEach items="${act.documenti}" var="docu">
			                    <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>
			
			                 </c:forEach>
			             </td>
			            <td> 
                <c:forEach items="${act.criticita}" var="critic">

                            <c:if test="${obj.statoApprov == 'DEFINITIVO'}" >
                                    <a href="../../controllerCritic/${critic.id}/edit">Edit </a>
                                </c:if>
                                ${critic.descrizione}</br>

                </c:forEach>
            </td>
			<td> 
                <c:forEach items="${act.criticita}" var="critic">

                            ${critic.proposte} </br>

                </c:forEach>
            </td>
			
			            <td> 
			                <c:forEach items="${act.criticita}" var="critic3">
			
			                            ${critic3.indicazioni} </br>
			
			                </c:forEach>
			            </td>
			    
			        </tr>

                </tbody>
                </table> 
                <hr />
                
            </c:forEach>
         
        </div>
                  
    </c:forEach>
    
    </div>  
    <hr />                              
</c:forEach>
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
	  	<td>[view:modifyIndicazioniDipartimentoController.jsp 25/08/2015] </td>
	</tr>
</table>
</div>
    
</div>
</body>
  
</html>
