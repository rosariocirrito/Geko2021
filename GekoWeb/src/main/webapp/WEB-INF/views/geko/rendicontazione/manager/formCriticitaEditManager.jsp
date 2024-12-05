<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Criticità</title>
        
        <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">
<h3>
	Criticità da Modificare
    per ${criticita.azione.descrizione}
    
</h3>



<form:form modelAttribute="criticita" method="put">
    
    <fieldset>
        <h2 class="info"> Dati descrittivi </h2>
  <table width="80%">
    <tr>
        <td><form:label for="descrizione" path="descrizione">descrizione</form:label></td>
        <td><form:textarea path="descrizione" size="100" maxlength="200"/> </td>
        <td><form:errors path="descrizione" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="proposte" path="proposte">proposte</form:label></td>
        <td><form:textarea path="proposte" size="100" maxlength="200"/> </td>
        <td><form:errors path="proposte" cssClass="errors"/></td>
    </tr>
    
    </table>
    </fieldset> 
    
    
    <fieldset>
        <table  width="80%">
    <tr>  
        
       
            <td>
           <p class="button"><input type="submit" name="update" value="Modifica Criticità"/></p>
           </td>
           <td>
           <p class="button"><input type="submit" name="elimina" value="Cancella Criticità"/></p>
           </td>
       
           
        <td>
            <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
        </td>
      
    </tr>
  </table>
    </fieldset>
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito 2019/06/18 </td>
    </tr>
  </table>

  </div>
</body>

</html>