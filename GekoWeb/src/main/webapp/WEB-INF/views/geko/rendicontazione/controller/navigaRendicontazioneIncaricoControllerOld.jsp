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
<div class="col-sm-2" id="wrapCol1" >
<c:if test="${empty listIncarichiDept}">
	<p>Nessun incarico trovato</p>
</c:if>
<ol>
<c:forEach items="${listIncarichiDept}" var="incarico" varStatus ="status">
<li>
<a href="<spring:url value="/controllerRend/navigaRendicontazioneIncaricoController/${anno}/${incarico.idIncarico}" htmlEscape="true" />">
		
${incarico.responsabile}</a>
</li>
</c:forEach>  
</ol>  
</div>
<div class="col-sm-10" id="wrapCol2" >
    

<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Rendicontazione Obiettivi per incarico: <em>${incarico.stringa} </em>- anno ${anno}</h1>
<hr />  
<h4>Competenze</h4>  
<h5>${competenze}</h5>
    
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
            
<%@ include file="/WEB-INF/views/common/tableObjsRend.jsp" %>


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
      <td>[view: navigaRendicontazioneIncaricoController.jsp 21/01/2016] </td>
    </tr>
</table>
</div>
  
 </div>
 </div>
    
    
</div>  

  	
	   
</body>
</html>
