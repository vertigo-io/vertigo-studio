<html>
	<body>
		<script src="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"></script>
		<script>mermaid.initialize({startOnLoad:true});</script>
		<style>
		.mermaid {
			overflow: hidden;
			width: 80%;
		}
		</style>
		<#list dtSketchsByFeature.entrySet() as featureEntry>
			<h1>Module ${featureEntry.getKey()}</h1>
			<div class="mermaid">
			classDiagram
			<#list featureEntry.getValue() as dtSketch>
				<@generateDtSketchClass dtSketch />
			</#list>
	 		</div>
		</#list> 
		<#list dtSketchsByPackage.entrySet() as featureEntry>
			<h1>Package ${featureEntry.getKey()}</h1>
			<div class="mermaid">
			classDiagram
			<#list featureEntry.getValue() as dtSketch>
				<@generateDtSketchClass dtSketch />
			</#list>
	 		</div>
		</#list>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/6.2.0/d3.min.js"></script>
		<script>
			window.addEventListener('load', function(e) {
				var div = d3.selectAll("div.mermaid");
				var drag = d3.drag()
				.on("drag", function(event) {
					let svg = d3.select(this).select("svg")
					let zoomTransform = d3.zoomTransform(svg.node())
					d3.zoom().translateBy(svg, event.dx/zoomTransform.k, event.dy/zoomTransform.k)
					svg.attr("transform", d3.zoomTransform(svg.node()).toString());
				})
				var zoom = d3.zoom()
				.on("zoom", function(event) {
					let svg = d3.select(this).select("svg")
					d3.zoom().scaleTo(svg, event.transform.k)
					svg.attr("transform", d3.zoomTransform(svg.node()).toString());
				});
				div.call(drag);
				div.call(zoom);
				div.on("mousedown.zoom", null)
			})
		</script> 
	</body>
</html>

<#macro generateDtSketchClass dtSketch>
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
</#macro>
