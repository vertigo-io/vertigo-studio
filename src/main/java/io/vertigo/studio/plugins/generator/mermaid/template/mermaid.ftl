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
		<script type="module">
		  import mermaid from 'https://cdn.jsdelivr.net/npm/mermaid@10/dist/mermaid.esm.min.mjs';
		  mermaid.initialize({startOnLoad:false, flowchart:{useMaxWidth:false}});
		  window.mermaid = mermaid;
		</script>
		<script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
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
						<div ref="graphDiv" class="graph"></div>
					</div>
				</section>
			</div>
		</div>
		<script>
			var graphData = {};
			<#list dtSketchsByFeature.entrySet() as featureEntry>
			graphData.${featureEntry.getKey()} = `classDiagram
					<#list featureEntry.getValue() as dtSketch>
						<@generateDtSketchClass dtSketch />
					</#list>
				`
			</#list> 
			<#list dtSketchsByPackage.entrySet() as featureEntry>
			graphData.${featureEntry.getKey()} = `classDiagram
					direction BT
					<#list featureEntry.getValue() as dtSketch>
						<@generateDtSketchClass dtSketch />
					</#list>
			 	`
			</#list>
		</script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/6.2.0/d3.min.js"></script>
		<script>
			function zoomAndPan() {
				var div = d3.selectAll("div.graph");
				var svg = div.select("svg");
				svg.attr('width', "100%");
				svg.attr('height',  "100%");
				
				var viewBoxVal = svg.node().viewBox.baseVal;
				var initW = viewBoxVal.width;
				var initH =  viewBoxVal.height;
				var screenW = div.node().clientWidth;
				var screenH = div.node().clientHeight
				var initK = Math.min(screenW/initW,screenH/initH);
				var g = svg.select("g");
				
				//remove listeners
				div.on(".zoom", null);
				div.on(".drag", null);
				
				var zoom = d3.zoom()
				.scaleExtent([0.5, 2/initK])
				//.translateExtent([[-0.5*screenW/initK, -0.5*screenH/initK], [1.5*screenW/initK, 1.5*screenH/initK]])
				.on("zoom", 
					function zoomed({transform}) {
					g.attr("transform", transform);
				});				
				svg.call(zoom);
				
				//reset zoom
			    svg.call(zoom.scaleTo, 1)
				.call(zoom.translateTo, 0.5 * initW, 0.5 * initH);
			}
		</script> 
		<script>
			var app = new Vue({
			  el: '#app',
			  data: {
			    activePanel: ''
			  },
			  methods : {
			  	changeActivePanel : function(panelName) {
			  		this.$data.activePanel = panelName
			  		element = this.$refs.graphDiv;
			  		window.mermaid.render('graphDiv', graphData[panelName]).then(({ svg, bindFunctions }) => {
					  element.innerHTML = svg;
					  bindFunctions?.(element);
					  window.zoomAndPan();
					});
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
${nnAssociation.nodeADtSketchName} "*" <#if nnAssociation.isNodeANavigable()><</#if>--<#if nnAssociation.isNodeBNavigable()>></#if> "*" ${nnAssociation.nodeBDtSketchName} : ${nnAssociation.nodeARole} / ${nnAssociation.nodeBRole}
</#list>
</#macro>
