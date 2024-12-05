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
	OivObiettivo da Modificare
    per ${oivObiettivo.obiettivo.descrizione}
    
</h3>



<form:form modelAttribute="oivObiettivo" method="put">
    
    <fieldset>
        <h2 class="info"> Dati descrittivi </h2>
  <table width="80%">
    <tr>
        <td><form:label for="memo" path="memo">memo</form:label></td>
        <td><form:textarea path="memo" size="200" maxlength="1000"/> </td>
        <td><form:errors path="memo" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="proposte" path="proposte">proposte</form:label></td>
        <td><form:textarea path="proposte" size="200" maxlength="1000"/> </td>
        <td><form:errors path="proposte" cssClass="errors"/></td>
    </tr>
    <tr>
        <td><form:label for="valutazioni" path="valutazioni">valutazioni</form:label></td>
        <td><form:textarea path="valutazioni" size="200" maxlength="1000"/> </td>
        <td><form:errors path="valutazioni" cssClass="errors"/></td>
    </tr>
    </table>
    </fieldset> 
    
    
    <fieldset>
        <table  width="80%">
    <tr>  
        
       
            <td>
           <p class="button"><input type="submit" name="update" value="Modifica"/></p>
           </td>
           <td>
           <p class="button"><input type="submit" name="elimina" value="Cancella"/></p>
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
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito 09/06/2017 </td>
    </tr>
  </table>

  </div>
</body>

</html>