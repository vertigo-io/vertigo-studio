package ${packageName}

<#list dtSketchs as dtSketch>
create DtDefinition ${dtSketch.name} {
	<#list dtSketch.idFields as idField>
	id ${idField.name} { label : "${idField.label}" domain: ${idField.domainName} }
	</#list>
	<#list dtSketch.dataFields as dtField>
	field ${dtField.name} { label : "${dtField.label}" domain: ${dtField.domainName} <#if dtField.hasSpecificCardinality() >cardinality : "${dtField.cardinalitySymbol}"</#if>}
	</#list>
	<#list dtSketch.computedFields as dtField>
	computed ${dtField.name} { label : "${dtField.label}" domain: ${dtField.domainName} <#if dtField.hasSpecificCardinality() >cardinality : "${dtField.cardinalitySymbol}"</#if>
		expression : "${dtField.javaCode}"}
	</#list>
}

</#list>
<#list simpleAssociations as associationSimple>
create Association ${associationSimple.name} {
	fkFieldName : "${associationSimple.fkFieldName}"
	
	dtDefinitionA : ${associationSimple.dtDefinitionA}
	roleA : "${associationSimple.roleA}"
	labelA : "${associationSimple.labelA}"
	multiplicityA : "${associationSimple.multiplicityA}"
	navigabilityA : "${associationSimple.navigabilityA}"
	
	dtDefinitionB : ${associationSimple.dtDefinitionB}
	roleB : "${associationSimple.roleB}"
	labelB : "${associationSimple.labelB}"
	multiplicityB : "${associationSimple.multiplicityB}"
	navigabilityB : "${associationSimple.navigabilityB}"
	
}

</#list>

<#list nnAssociations as associationNN>
create AssociationNN ${associationNN.name} {
	tableName : "${associationNN.tableName}"
	
	dtDefinitionA : ${associationNN.dtDefinitionA}
	roleA : "${associationNN.roleA}"
	labelA : "${associationNN.labelA}"
	multiplicityA : "${associationNN.multiplicityA}"
	navigabilityA : "${associationNN.navigabilityA}"
	
	dtDefinitionB : ${associationNN.dtDefinitionB}
	roleB : "${associationNN.roleB}"
	labelB : "${associationNN.labelB}"
	multiplicityB : "${associationNN.multiplicityB}"
	navigabilityB : "${associationNN.navigabilityB}"
	
}

</#list>