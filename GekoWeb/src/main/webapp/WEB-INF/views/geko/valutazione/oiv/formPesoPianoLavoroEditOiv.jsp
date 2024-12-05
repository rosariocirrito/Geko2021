<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Edit Peso Piano di Lavoro</title>
        
        <link href="../../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">



<h3>
	Modifica Peso Piano di Lavoro per l'incarico di </br> 
	${valutazione.incarico.responsabile} /
	${valutazione.incarico.denominazioneStruttura} /
	Anno: ${valutazione.anno}
</h3>
            

<form:form modelAttribute="valutazione" method="PUT">
    
<fieldset>
<h2 class="info"> Peso Attribuito </h2>
<table width="50%">
	<tr>
       <td><form:label for="pdlAss" path="pdlAss">Piano di Lavoro</form:label></td>
       <td><form:input path="pdlAss" size="4" maxlength="4"/> </td>
       <td><form:errors path="pdlAss" cssClass="errors"/></td>
       <td>range (20-30)</td>
        
    </tr>
    

    
</table>
</fieldset> 
    
    
<fieldset>
<table  width="80%">
  <tr>  
	<td><p class="button"><input type="submit" name="update" value="Modifica Peso"/></p></td>
	<td><p class="button"><input type="submit" name="cancel" value="Esci"/></p></td>
    
  </tr>
</table>
</fieldset>
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_OIV" htmlEscape="true" />">Menu Oiv</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: formPesoPianoLavoroEditOiv.jsp 2017/10/12]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>