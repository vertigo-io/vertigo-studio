<html>
	<body>
		<script src="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"></script>
		<script>mermaid.initialize({startOnLoad:true});</script>

		<#list dtSketchsByFeature.entrySet() as featureEntry>
			<h1>Module ${featureEntry.getKey()}</h1>
			<div class="mermaid">
			classDiagram
			<#list featureEntry.getValue() as dtSketch>
				class ${dtSketch.localName} {
					<#list dtSketch.fields as dtField >
						+${dtField.mermaidType} ${dtField.name}
					</#list>
				}
				<#list dtSketch.simpleAssociations as simpleAssociation >
				${simpleAssociation.foreignDtSketchName} "${simpleAssociation.foreignCardinality}" --> "${simpleAssociation.primaryCardinality}" ${simpleAssociation.primaryDtSketchName} : ${simpleAssociation.role}
				</#list>
				<#list dtSketch.nnAssociations as nnAssociation >
				${nnAssociation.nodeADtSketchName} "*" <--> "*" ${nnAssociation.nodeBDtSketchName}
				</#list>
			</#list>
	 		</div>
		</#list> 
	</body>
</html>