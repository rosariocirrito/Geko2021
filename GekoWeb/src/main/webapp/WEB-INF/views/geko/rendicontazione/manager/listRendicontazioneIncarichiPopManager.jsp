<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<!-- <meta charset="ISO-8859-1" />  -->
    <meta charset="UTF-8" />
    <title>Lista Rendicontazione Obiettivi Pop della Struttura</title>
    <link href=<spring:url value="/resources/bootstrap336/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
    <style>
        .row{
                margin-top:16px;
                margin-left:16px;
                background-color:lightgray;
        }
        .col{
                background-color:lightblue;
                padding:8px;
                border:2px solid darkgray;
        }
        .colObj{
                background-color:lightgreen;
                padding:8px;
                border:1px solid darkgray;
        }
    </style>
</head>
    
<body>
<div class="container" >
    
<div class="jumbotron">    
<div class="row">
<div class="col-lg-12 col">     
    <h3>Rendicontazione Obiettivi strutture Pop incardinate su <em>${struttura} </em>- anno ${anno}</h> 
    <c:if test="${empty listIncarichiPop}"><p>Nessun incarico trovato.</p></c:if>
</div>
</div>
</div>
        
<c:forEach items="${listIncarichiPop}" var="inc" varStatus ="status">
    <div class="jumbotron">    
        
    <div class="row">
    <div class="col-lg-12 col"> 
    <hr /> 
    <hgroup >            
    <h4 >Struttura  ${inc.denominazioneStruttura}</h4>
    <h4 class="responsabile">Responsabile  ${inc.responsabile} </h4>
    </hgroup>
    </div>
    </div>
    </div>
    
    <!-- 1. itero sugli obiettivi ------------------------------------------------------->
    <c:forEach items="${inc.obiettivi}" var="obj" varStatus ="status">
        <div class="jumbotron">
        <!-- riga obiettivo -->
        <div class="row" style="background: lightgreen;">
            <div class="col-lg-1 colObj" >
                    [${obj.idObiettivo}] ${obj.codice} 
            </div>
            <div class="col-lg-8 colObj">
                Obj: ${obj.descrizione}
                <c:if test="${obj.note> ''}"> 
                    <br />
                    <em>nota:${obj.note}</em>
                </c:if>
            </div>
            <div class="col-lg-2 colObj">
                ${obj.statoApprov}
            </div>
            <div class="col-lg-1 colObj">
                peso: ${obj.peso}
            </div>
        </div>	<!-- chiudi riga obiettivo -->

        <!-- 1.1.1 iterazione su obiettivi per visualizzare azioni ------------------------------->
        <c:forEach items="${obj.azioni}" var="act">
        <!-- riga azione -->
            <div class="row" style="background: lightblue;">
                <div class="col-lg-1 col" >
                    [${act.idAzione}]  
                </div>
                <div class="col-lg-5 col">
                    ${act.denominazione} - ${act.descrizione}
                    <c:if test="${act.note>''}">
                            <em>nota: ${act.note} </em>
                    </c:if>
                </div>
                <div class="col-lg-1 col">
                    indic.: ${act.indicatore}
                </div>
                <div class="col-lg-1 col">
                    valore: ${act.prodotti}
                </div>
                <div class="col-lg-1 col">
                    scad.: <fmt:formatDate value="${act.scadenza}" pattern="dd/MM/yyyy"/>
                </div>
                <div class="col-lg-1 col">
                    peso: ${act.peso}
                </div>
                <div class="col-lg-2 col">
                    risult.: ${act.risultato}
                </div>
            </div> <!-- fine riga azione -->

            <!--  righe documenti -->
            <div class="row" style="background: lightyellow;">
		<div class="col-lg-1 col"><h4>ID</h4></div>
		<div class="col-lg-1 col"><h4>Data</h4></div>
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
            <!-- fine righe documenti -->
        
        

    		</c:forEach> <!-- fine for azioni -->
        </div>
   	</c:forEach> <!-- inc.obiettivi -->   
                  
</c:forEach><!-- listIncarichiPop -->
    
                
<!-- Footer -------------------------->

<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	
</div>
</div>
</div>


</body>
  
</html>





