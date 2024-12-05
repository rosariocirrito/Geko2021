<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Richiesta Obiettivo</title>
        <%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
		<%@ include file="/WEB-INF/views/common/CKEditor.jsp" %>
</head>

<body>
<div id="wrapper">
    

<h2>Richiesta obiettivo</h2>

<security:authorize access="hasRole('GABINETTO')">
<form:form  modelAttribute="obiettivo" method="post">
<script type="text/javascript">
	$(function(){
		
	});
</script>
<fieldset>
    <h2 class="info"> Dati descrittivi  </h2>
    <ul>
	    <li>
	        <form:label for="codice" path="codice">Codice (es. OPR_1)</form:label>
	        <form:textarea path="codice" size="16" maxlength="16"/>
	        <form:errors path="codice" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="descrizione" path="descrizione">Descrizione</form:label>
	        <form:textarea path="descrizione" size="1000" maxlength="1000"/>
	        <form:errors path="descrizione" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="note" path="note">Note</form:label>
	        <form:textarea path="note" size="1000" maxlength="1000"/>
	        <form:errors path="note" cssClass="error"/>
	    </li>
	    
	    <li>
	        <form:label for="peso" path="peso">peso</form:label>
	        <form:input path="peso" size="2" maxlength="2"/>
	        <form:errors path="peso" cssClass="error"/>
	    </li>
	    
    </ul>  
</fieldset>


        
        
<fieldset>
    <h2 class="comandi">Comandi</h2>
    <table  width="80%">
        <tr>
            <td colspan="2">
                <p class="button"><input type="submit" name="add" value="Richiedi Obiettivo"/></p>
            </td>
            <td>
            <p class="button"><input type="submit" name="cancel" value="Rinuncia Richiesta Obiettivo"/></p>
            </td>
        </tr>
    </table>  
</fieldset>
      
</form:form>     
</security:authorize>    
        
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_GABINETTO" htmlEscape="true" />">Menu Gabinetto</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito 
      </td>
    </tr>
    <tr>
        <td><p>[view: formObiettivoRichiedeGabinetto.jsp 2019/07/22]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>
