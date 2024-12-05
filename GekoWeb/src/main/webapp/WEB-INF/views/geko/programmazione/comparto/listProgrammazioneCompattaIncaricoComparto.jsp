<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="ISO-8859-1" />
    <title>Obiettivi della Struttura del Dirigente</title>
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
	</style>
</head>
    
<body>
    
<div class="container" >
<div class="row">
<div class="col-lg-12 col"> 
    
<article>
<section id="obj-sezione">   
<h1 id="titolo-lista">Programmazione Obiettivi per <em>${struttura} </em>- anno ${anno}</h1>
<hr />  


<h4>Responsabile: ${responsabile}</h4>
<h4>Competenze</h4>  
<h5>${competenze}</h5>   
<c:if test="${empty listObiettivi}">
    <p>Nessun obiettivo trovato.</p>
</c:if>
            

<%@ include file="/WEB-INF/views/common/tableObjsCompatta.jsp" %>
</section>
</article>
    
<!-- Footer -------------------------->

      
<span class="label label-warning"><a href="<spring:url value="/ROLE_COMPARTO" htmlEscape="true" />">Menu Comparto</a></span>
      Ge.Ko. by ing. R. Cirrito [listProgrammazioneCompattaIncaricoComparto.jsp 2019/08/28] 

</div>
</div>
</div>     
</body>
</html>
