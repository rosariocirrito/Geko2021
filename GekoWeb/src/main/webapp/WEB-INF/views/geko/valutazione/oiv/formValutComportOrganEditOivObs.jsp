<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Edit Comportamenti Organizzativi</title>
        <link href=<spring:url value="/resources/css/listaValut.css"  /> rel="stylesheet"/>
</head>

<body>
<div id="wrapper">



<h3>
	Modifica Valutazione Comportamenti Organizzativi per l'incarico di </br> 
	${valutazione.incarico.responsabile} /
	${valutazione.incarico.denominazioneStruttura}/
	Anno: ${valutazione.anno}
</h3>
            

<form:form modelAttribute="valutazione" method="PUT">
    
<fieldset>
<h2 class="info"> Valutazioni </h2>
<table width="50%">
  	
<tr>
	<td><form:label for="analProgrVal" path="analProgrVal">Capacit� di Analisi e Programmazione Valutazione</form:label></td>
        <td><form:select path="analProgrVal" class="large" >
	        	<form:option value="ECCELLENTE"/>
            	<form:option value="BUONO"/>
            	<form:option value="SUFFICIENTE"/>
            	<form:option value="INSUFFICIENTE"/>
            	<form:option value="DA_VALUTARE"/>
           	</form:select>	
	        <form:errors path="analProgrVal" cssClass="error"/>
	</td>
</tr>
    
<tr>
	<td><form:label for="relazCoordVal" path="relazCoordVal">Capacit� di Relazione e Coordinamento Valutazione</form:label></td>
    <td><form:select path="relazCoordVal" class="large" >
     	<form:option value="ECCELLENTE"/>
        	<form:option value="BUONO"/>
        	<form:option value="SUFFICIENTE"/>
        	<form:option value="INSUFFICIENTE"/>
        	<form:option value="DA_VALUTARE"/>
       	</form:select>
     </td>	
</tr>
    
<tr>
    <td><form:label for="gestRealVal" path="gestRealVal">Capacit� di Gestione e Realizzazione Valutazione</form:label></td>
    <td><form:select path="gestRealVal" class="large" >
     	<form:option value="ECCELLENTE"/>
        	<form:option value="BUONO"/>
        	<form:option value="SUFFICIENTE"/>
        	<form:option value="INSUFFICIENTE"/>
        	<form:option value="DA_VALUTARE"/>
       	</form:select>
     </td>	
</tr>
    
</table>
</fieldset> 
    
    
<fieldset>
<table  width="80%">
<tr>  
	<td><p class="button"><input type="submit" name="update" value="Modifica Valutazione"/></p></td>
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
        <td><p>[view: formValutComportOrganEditOiv.jsp 2017/11/15]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>