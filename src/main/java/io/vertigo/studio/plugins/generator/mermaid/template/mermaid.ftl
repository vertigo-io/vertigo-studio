<html>
	<head>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.9.1/css/bulma.min.css" integrity="sha512-ZRv40llEogRmoWgZwnsqke3HNzJ0kiI0+pcMgiz2bxO6Ew1DVBtWjVn0qjrXdT3+u+pSN36gLgmJiiQ3cQtyzA==" crossorigin="anonymous" />
		<style>
			[v-cloak] {
			  display: none;
			}
		
			.main-content {
				height: 100vh;
			}
			
			.fullheigth {
				height:100%
			}
		
			body {
				background: white;
			}
			
			g.classGroup text .title {
				font-weight: bolder;
				font-size: 10px;
				line-height: 1;
			}
			
			.mermaid[data-processed="true"] {
				overflow: hidden;
				width: 100%;
				height: 100%;
				display : block;
			}
			
			.mermaid{
				display: none;
			}
			
			div.mermaid svg {
				font-family: "trebuchet ms", verdana, arial;
				font-size: 16px;
				fill: #333;
			}

			div.mermaid svg .error-icon {
				fill: #552222;
			}

			div.mermaid svg .error-text {
				fill: #552222;
				stroke: #552222;
			}

			div.mermaid svg .edge-thickness-normal {
				stroke-width: 2px;
			}

			div.mermaid svg .edge-thickness-thick {
				stroke-width: 3.5px;
			}

			div.mermaid svg .edge-pattern-solid {
				stroke-dasharray: 0;
			}

			div.mermaid svg .edge-pattern-dashed {
				stroke-dasharray: 3;
			}

			div.mermaid svg .edge-pattern-dotted {
				stroke-dasharray: 2;
			}

			div.mermaid svg .marker {
				fill: #333333;
			}

			div.mermaid svg .marker.cross {
				stroke: #333333;
			}

			div.mermaid svg svg {
				font-family: "trebuchet ms", verdana, arial;
				font-size: 16px;
			}

			div.mermaid svg g.classGroup text {
				fill: #9370DB;
				fill: #131300;
				stroke: none;
				font-family: "trebuchet ms", verdana, arial;
				font-size: 10px;
			}

			div.mermaid svg g.classGroup text .title {
				font-weight: bolder;
			}

			div.mermaid svg .classTitle {
				font-weight: bolder;
			}

			div.mermaid svg .node rect,
			div.mermaid svg .node circle,
			div.mermaid svg .node ellipse,
			div.mermaid svg .node polygon,
			div.mermaid svg .node path {
				fill: #ECECFF;
				stroke: #9370DB;
				stroke-width: 1px;
			}

			div.mermaid svg .divider {
				stroke: #9370DB;
				stroke: 1;
			}

			div.mermaid svg g.clickable {
				cursor: pointer;
			}

			div.mermaid svg g.classGroup rect {
				fill: #ECECFF;
				stroke: #9370DB;
			}

			div.mermaid svg g.classGroup line {
				stroke: #9370DB;
				stroke-width: 1;
			}

			div.mermaid svg .classLabel .box {
				stroke: none;
				stroke-width: 0;
				fill: #ECECFF;
				opacity: 0.5;
			}

			div.mermaid svg .classLabel .label {
				fill: #9370DB;
				font-size: 10px;
			}

			div.mermaid svg .relation {
				stroke: #333333;
				stroke-width: 1;
				fill: none;
			}

			div.mermaid svg .dashed-line {
				stroke-dasharray: 3;
			}

			div.mermaid svg #compositionStart,
			div.mermaid svg .composition {
				fill: #333333 !important;
				stroke: #333333 !important;
				stroke-width: 1;
			}

			div.mermaid svg #compositionEnd,
			div.mermaid svg .composition {
				fill: #333333 !important;
				stroke: #333333 !important;
				stroke-width: 1;
			}

			div.mermaid svg #dependencyStart,
			div.mermaid svg .dependency {
				fill: #333333 !important;
				stroke: #333333 !important;
				stroke-width: 1;
			}

			div.mermaid svg #dependencyStart,
			div.mermaid svg .dependency {
				fill: #333333 !important;
				stroke: #333333 !important;
				stroke-width: 1;
			}

			div.mermaid svg #extensionStart,
			div.mermaid svg .extension {
				fill: #333333 !important;
				stroke: #333333 !important;
				stroke-width: 1;
			}

			div.mermaid svg #extensionEnd,
			div.mermaid svg .extension {
				fill: #333333 !important;
				stroke: #333333 !important;
				stroke-width: 1;
			}

			div.mermaid svg #aggregationStart,
			div.mermaid svg .aggregation {
				fill: #ECECFF !important;
				stroke: #333333 !important;
				stroke-width: 1;
			}

			div.mermaid svg #aggregationEnd,
			div.mermaid svg .aggregation {
				fill: #ECECFF !important;
				stroke: #333333 !important;
				stroke-width: 1;
			}

			div.mermaid svg .edgeTerminals {
				font-size: 11px;
			}

			div.mermaid svg:root {
				--mermaid-font-family: "trebuchet ms", verdana, arial;
			}

			div.mermaid svg class {
				fill: apa;
			}
		</style>
	</head>
	<body>
		<script src="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
		<script>mermaid.initialize({startOnLoad:true});</script>
		<div id="app">
			<div class="main-content row">
				<section class="columns fullheigth">
					<aside class="menu column is-2 is-narrow-mobile section fullheigth">
						<#if !dtSketchsByFeature.isEmpty() >
					  <p class="menu-label">
						Modules
					  </p>
					  	</#if>
					  <ul class="menu-list">
					  	<#list dtSketchsByFeature.entrySet() as featureEntry>
							<li><a @click="changeActivePanel('${featureEntry.getKey()}')">${featureEntry.getKey()}</a></li>
						</#list>
					  </ul>
					  	<#if !dtSketchsByPackage.isEmpty() >
					  <p class="menu-label">
						Packages
					  </p>
					  	</#if>
					  <ul class="menu-list">
					  	<#list dtSketchsByPackage.entrySet() as featureEntry>
							<li><a @click="changeActivePanel('${featureEntry.getKey()}')">${featureEntry.getKey()}</a></li>
						</#list>
					  </ul>
					</aside>
					<div class="column is-10 is-marginless fullheigth">
						<#list dtSketchsByFeature.entrySet() as featureEntry>
							<div v-if="activePanel === '${featureEntry.getKey()}' " >
								<h1 v-cloak >Module ${featureEntry.getKey()}</h1>
								<div class="mermaid">
								classDiagram
								<#list featureEntry.getValue() as dtSketch>
									<@generateDtSketchClass dtSketch />
								</#list>
						 		</div>
						 	</div>
						</#list> 
						<#list dtSketchsByPackage.entrySet() as featureEntry>
							<div v-if="activePanel === '${featureEntry.getKey()}' " >
								<h1 v-cloak >Module ${featureEntry.getKey()}</h1>
								<div class="mermaid">
								classDiagram
								<#list featureEntry.getValue() as dtSketch>
									<@generateDtSketchClass dtSketch />
								</#list>
						 		</div>
						 	</div>
						</#list>
					</div>
				</section>
			</div>
		</div>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/6.2.0/d3.min.js"></script>
		<script>
			function zoomAndPan() {
				var div = d3.selectAll("div.mermaid");
				var svg = div.select("svg");
				
				//remove listeners
				div.on(".zoom", null);
				div.on(".drag", null);
				
				//reset zoom
				svg.attr("transform", d3.zoomIdentity.toString())
				// can't find any better solution
				svg.node().__zoom = d3.zoomIdentity
				
				// add new ones
				var drag = d3.drag()
				.on("drag", function(event) {
					let zoomTransform = d3.zoomTransform(svg.node())
					d3.zoom().translateBy(svg, event.dx/zoomTransform.k, event.dy/zoomTransform.k)
					svg.attr("transform", d3.zoomTransform(svg.node()).toString());
				})
				var zoom = d3.zoom()
				.on("zoom", function(event) {
					let maxX = parseInt(svg.style("width"), 10)-20; //-20 from margin svg to inner g g
					let maxY = parseInt(svg.style("height"), 10)-20;
					let zoomTransform = d3.zoomTransform(svg.node())
					var mousePos = d3.pointer(event);
					var newTx = ((maxX-mousePos[0])/zoomTransform.k-(maxX-mousePos[0])/event.transform.k);
					var newTy = ((maxY-mousePos[1])/zoomTransform.k-(maxY-mousePos[1])/event.transform.k);
					d3.zoom().scaleTo(svg, event.transform.k)
					d3.zoom().translateBy(svg, newTx, newTy)					
					svg.attr("transform", d3.zoomTransform(svg.node()).toString());
				});
				div.call(drag);
				div.call(zoom);
				div.on("mousedown.zoom", null)
			}
		</script> 
		<script>
			mermaid.init({noteMargin: 10}, ".mermaid");
			zoomAndPan();
			var app = new Vue({
			  el: '#app',
			  data: {
			    activePanel: ''
			  },
			  methods : {
			  	changeActivePanel : function(panelName) {
			  		this.$data.activePanel = panelName
			  		this.$nextTick(function() {
			  			window.zoomAndPan();
			  		})
			  	}
			  }
			})
		</script>
		
		
		
	</body>
</html>

<#macro generateDtSketchClass dtSketch>
class ${dtSketch.localName}:::${dtSketch.stereotypeInterfaceName?uncap_first} {
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
