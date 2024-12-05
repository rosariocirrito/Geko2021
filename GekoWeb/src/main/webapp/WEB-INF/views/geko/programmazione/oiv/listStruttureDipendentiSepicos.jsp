<%@ include file="/WEB-INF/views/common/includes.jsp" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html lang="it">

<head>
	<meta charset="ISO-8859-1" />
    <title>Lista Strutture Dipartimentali </title>
    <link href="../resources/css/lista.css" rel="stylesheet"/>
</head>
    
<body>
    <security:authentication property="name" var="nomeUtente"/>
    <security:authentication property="authorities" var="ruoli" />
    
    
<article>
    <div id="wrapper">
    
<c:forEach items="${mapListStrutture}" var="mapItem">   
    
    <section id="obj-sezione">   
    <h1 id="titolo-lista">Lista Strutture / Dipendenti per <em>${mapItem.key.denominazione} </em></h1>   

    <hr />      
   
    
    <c:forEach items="${mapItem.value}" var="struttura">
	    <table>
	    <tbody>
	    <tr>
	        <th colspan="5"> <h3>[id:${struttura.idpersona}] / ${struttura.codice} / ${struttura.denominazione}</h3> </th>
	    </tr>   
	    
	    <tr>
	    	<th>Id</th>
	        <th>Matricola</th>
	        <th>Cognome</th>
	        <th>Nome</th>
	        <th>Qualifica</th>
	        
	    </tr>
	    <c:forEach items="${struttura.dipendenti}" var="dip" varStatus ="status">
	         <tr>
	         	<td> 
	             	${dip.idPersona}
	            </td>
	         	<td> 
	                 ${dip.matricola}
	            </td>
	         	<td> 
	                 ${dip.cognome}
	            </td>
	            <td> 
	                 ${dip.nome}
	            </td>
	            <td> 
	                 ${dip.opPersonaFisicaTipo.descrizione} - ${dip.opPersonaFisicaTipo.livello}
	            </td>
	         </tr>
	    </c:forEach>
	 
	    </tbody>
	    </table>       
    
	<br/>      
	</c:forEach>
	
</section>
</c:forEach>
</div>
</article>       
           <!-- Footer -------------------------->
<div id="footer">
      
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_SEPICOS" htmlEscape="true" />">Menu Sepicos</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - 
      ing. R. Cirrito </td>
    </tr>
    <tr>
	  	<td>[view:listStruttureDipendentiSepicos.jsp] </td>
	</tr>
  </table>
</div>
    
</body>
</html>
