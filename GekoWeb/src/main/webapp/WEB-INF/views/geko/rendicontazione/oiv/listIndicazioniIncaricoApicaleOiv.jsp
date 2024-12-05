<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
<meta charset="ISO-8859-1" />
    <title>Rendicontazione Obiettivi della Struttura del Dirigente</title>
<link href="../../../resources/css/lista.css" rel="stylesheet"/>

</head>

<body>
<div id="wrapper">
<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Rendicontazione Obiettivi con osservazioni per <em>${incarico.denominazioneStruttura} </em>- anno ${anno}</h1>
<hr />  
<h4>Responsabile: ${responsabile}</h4>   
        
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
                
<c:forEach items="${listObiettivi}" var="obj" varStatus ="status">
    <div id="${obj.tipo}">
    <hgroup >            
    <h3 >
        ${obj.codice} - <em>&nbsp; ${obj.descrizione} </em>
    </h3>
        
    <c:if test="${obj.note}"><h4>${obj.note}</h4></c:if> 
    <h4>
        stati: ${obj.statoApprov} / ${obj.statoRealiz}
        <c:if test= "${obj.pesoApicale >0}"> peso: ${obj.pesoApicale} </c:if> 
    </h4> 
    </hgroup>
            
    <table >   
    <tbody>
    <c:forEach items="${obj.azioniOiv}" var="act">
    	<tr><th colspan="6"><h4>AZIONE: ${act.denominazione} - ${act.descrizione}<c:if test="${act.tassativa}">- AZIONE E SCADENZA TASSATIVE</c:if></h4></th></tr> 
        
        <c:if test="${act.note}">
        <tr>
            <td>Note</td>      
            <td colspan="8"> ${act.note}</td>
        </tr>
		</c:if>
        <tr>
            <th class="prod" width="10%">Indicatore</th>
            <th class="prod" width="10%">Valore Obiettivo</th>
            <th class="prod" width="10%">Scadenza</th>
            <th class="prod" width="10%">Peso</th>
            <th class="prod" width="10%">Risultato</th>
            <th class="prod" width="50%">Documenti</th>
            
            
        </tr>

        <tr>    
            <td>${act.indicatore}</td>
            <td>${act.prodotti}</td>
            <td><fmt:formatDate value="${act.scadenzaApicale}" pattern="dd/MM/yyyy"/></td><c:if test="${act.tassativa}">- TASSATIVA</c:if>
            <td>${act.pesoApicale}</td>
            <td>${act.risultato}</td>
            <td> 
                <c:forEach items="${act.documenti}" var="docu">
                    <a href="<spring:url value="/dirigenteDocumenti/download/${docu.id}" htmlEscape="true" />">${docu.nomefile}</a>

                 </c:forEach>
             </td>
             
             
            
    
        </tr>
        <tr>
        	<th class="prod" width="30%">Criticità</th>
            <th class="prod" width="40%">Proposte</th>
            <th class="prod" width="30%" colspan="4">Indicazioni</th>
        </tr>
        <tr>
        <td> 
        
                <c:forEach items="${act.oivAzione}" var="oivAzione">
					<span style="color: #ffffff; background-color: #ff0000;"> 
                            ${oivAzione.memo} </br>
					</span>
                </c:forEach>
            </td>
			<td> 
                <c:forEach items="${act.oivAzione}" var="oivAzione2">
					<span style="color: #ffffff; background-color: #ff0000;"> 
                            ${oivAzione2.proposte} </br>
					</span>
                </c:forEach>
            </td>
            <td colspan="4"> 
                <c:forEach items="${act.oivAzione}" var="oivAzione3">
					<span style="color: #ffffff; background-color: #ff0000;"> 
                            ${oivAzione3.valutazioni} </br>
					</span>
                </c:forEach>
            </td>
        </tr>
        
    </c:forEach>
    </tbody>
    </table>   
            
</div>
</c:forEach>
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
      <td>[listIndicazioniIncaricoApicaleOiv.jsp 2019/07/23] </td>
    </tr>
  </table>
</div>
  
    
</div>    
</body>
</html>
