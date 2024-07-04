package ${packageName};

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.core.lang.Generated;
import io.vertigo.core.lang.ListBuilder;
import io.vertigo.core.node.component.Component;
import io.vertigo.core.node.definition.DefinitionProvider;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.datafactory.collections.definitions.FacetDefinition.FacetOrder;
<#if hasRangeFacet>
import io.vertigo.datafactory.collections.definitions.FacetRangeDefinitionSupplier;
</#if>
import io.vertigo.datafactory.collections.definitions.FacetTermDefinitionSupplier;
<#if hasCustomFacet>
import io.vertigo.datafactory.collections.definitions.FacetCustomDefinitionSupplier;
</#if>
import io.vertigo.datafactory.collections.definitions.FacetedQueryDefinitionSupplier;
import io.vertigo.datafactory.collections.model.FacetedQueryResult;
import io.vertigo.datafactory.collections.model.SelectedFacetValues;
import io.vertigo.datafactory.search.SearchManager;
import io.vertigo.datafactory.search.definitions.SearchIndexDefinition;
import io.vertigo.datafactory.search.definitions.SearchIndexDefinitionSupplier;
import io.vertigo.datafactory.search.model.SearchQuery;
import io.vertigo.datafactory.search.model.SearchQueryBuilder;
import io.vertigo.datamodel.data.model.DtListState;
import io.vertigo.datamodel.data.model.UID;
<#if indexDtDefinition.classCanonicalName != dtDefinition.classCanonicalName && indexDtDefinition.packageName != packageName>
import ${indexDtDefinition.classCanonicalName};
</#if>
import ${dtDefinition.classCanonicalName};

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class ${dtDefinition.classSimpleName}SearchClient implements Component, DefinitionProvider {

	private final SearchManager searchManager;
	private final VTransactionManager transactionManager;

	/**
	 * Contructeur.
	 * @param searchManager Search Manager
	 * @param transactionManager Transaction Manager
	 */
	@Inject
	public ${dtDefinition.classSimpleName}SearchClient(final SearchManager searchManager, final VTransactionManager transactionManager) {
		this.searchManager = searchManager;
		this.transactionManager = transactionManager;
	}

	<#list facetedQueryDefinitions as facetedQueryDefinition>
	/**
	 * Création d'une SearchQuery de type : ${facetedQueryDefinition.simpleName}.
	 * @param criteria Critères de recherche
	 * @param selectedFacetValues Liste des facettes sélectionnées à appliquer
	 * @return SearchQueryBuilder pour ce type de recherche
	 */
	public SearchQueryBuilder createSearchQueryBuilder${facetedQueryDefinition.simpleName}(final ${facetedQueryDefinition.criteriaClassCanonicalName} criteria, final SelectedFacetValues selectedFacetValues) {
		return SearchQuery.builder("${facetedQueryDefinition.queryName}")
				.withCriteria(criteria)
				.withFacet(selectedFacetValues);
	}
	</#list>

	<#list searchIndexDefinitions as searchIndexDefinition>
	/**
	 * Récupération du résultat issu d'une requête.
	 * @param searchQuery critères initiaux
	 * @param listState Etat de la liste (tri et pagination)
	 * @return Résultat correspondant à la requête (de type ${indexDtDefinition.classSimpleName})
	 */
	public FacetedQueryResult<${indexDtDefinition.classSimpleName}, SearchQuery> loadList${searchIndexDefinition.name}(final SearchQuery searchQuery, final DtListState listState) {
		final SearchIndexDefinition indexDefinition = io.vertigo.core.node.Node.getNode().getDefinitionSpace().resolve("${searchIndexDefinition.name}",SearchIndexDefinition.class);
		return searchManager.loadList(indexDefinition, searchQuery, listState);
	}
	</#list>
		
	/**
	 * Récupération du résultat issu d'une requête.
	 * @param searchQuery critères initiaux
	 * @param listState Etat de la liste (tri et pagination)
	 * @return Résultat correspondant à la requête (de type ${indexDtDefinition.classSimpleName})
	 */
	public FacetedQueryResult<${indexDtDefinition.classSimpleName}, SearchQuery> loadList(final SearchQuery searchQuery, final DtListState listState) {
		final List<SearchIndexDefinition> indexDefinitions = List.of( 
				<#list searchIndexDefinitions as searchIndexDefinition>
				io.vertigo.core.node.Node.getNode().getDefinitionSpace().resolve("${searchIndexDefinition.name}",SearchIndexDefinition.class)<sep>,
<sep></#list>);
		return searchManager.loadList(indexDefinitions, searchQuery, listState);
	}

	/**
	 * Mark an entity as dirty. Index of these elements will be reindexed if Tx commited.
	 * Reindexation isn't synchrone, strategy is dependant of plugin's parameters.
	 *
	 * @param entityUID Key concept's UID
	 */
	public void markAsDirty(final UID entityUID) {
		transactionManager.getCurrentTransaction().addAfterCompletion((final boolean txCommitted) -> {
			if (txCommitted) {// reindex only is tx successful
				searchManager.markAsDirty(Arrays.asList(entityUID));
			}
		});
	}

	<#list searchIndexDefinitions as searchIndexDefinition>
	/**
	 * Mark an entity as dirty. Index of these elements will be reindexed if Tx commited.
	 * Reindexation isn't synchrone, strategy is dependant of plugin's parameters.
	 *
	 * @param entity Key concept
	 */
	public void markAsDirty(final ${searchIndexDefinition.keyConceptClassCanonicalName} entity) {
		markAsDirty(UID.of(entity));
	}
	</#list>
	
	<#list searchIndexDefinitions as searchIndexDefinition>
	/**
	 * Simple access to definition, need to call some SearchManager function.
	 * @return SearchDefinition 
	 */
	public SearchIndexDefinition getSearchIndexDefinition${searchIndexDefinition.name}() {
		return io.vertigo.core.node.Node.getNode().getDefinitionSpace().resolve("${searchIndexDefinition.name}",SearchIndexDefinition.class);
	}
	</#list>

	/** {@inheritDoc} */
	@Override
	public List<DefinitionSupplier> get(final DefinitionSpace definitionSpace) {
		return new ListBuilder<DefinitionSupplier>()
				//---
				// SearchIndexDefinition
				//-----
				<#list searchIndexDefinitions as searchIndexDefinition>
				.add(new SearchIndexDefinitionSupplier("${searchIndexDefinition.name}")
						.withIndexDtDefinition("${searchIndexDefinition.indexDtDefinition}")
						.withKeyConcept("${searchIndexDefinition.keyConceptDtDefinition}")
						<#list searchIndexDefinition.copyToModels as copyTo>
						.withCopyToFields("${copyTo.to}", <#list copyTo.from as from>"${from}"<#sep>, </#list>)
						</#list>
						.withLoaderId("${searchIndexDefinition.loaderId}"))
				</#list>
				
				//---
				// FacetTermDefinition
				//-----
				<#list facetDefinitions as facetDefinition>
				.add(new Facet<#if facetDefinition.isRange()>Range<#elseif facetDefinition.isCustom()>Custom<#else>Term</#if>DefinitionSupplier("${facetDefinition.name}")
						.withDtDefinition("${dtDefinition.dtName}")
						.withFieldName("${facetDefinition.fieldName}")
						.withLabel("${facetDefinition.label}")
						<#if facetDefinition.isMultiSelectable()>
						.withMultiSelectable()
						</#if>
						<#if facetDefinition.isRange()>
						<#list facetDefinition.facetValues as facetValue>
						.withRange("${facetValue.code}", "${facetValue.listFilter}", "${facetValue.label}")
						</#list>						
						</#if>
						<#if facetDefinition.isCustom()>
						<#list facetDefinition.facetParams as facetParam>
						.withParams("${facetParam.name}", "${facetParam.value?js_string}")
						</#list>
						</#if>
						.withOrder(FacetOrder.${facetDefinition.order}))
				</#list>

				//---
				// FacetedQueryDefinition
				//-----
				<#list facetedQueryDefinitions as facetedQueryDefinition>
				.add(new FacetedQueryDefinitionSupplier("${facetedQueryDefinition.queryName}")
						<#list facetedQueryDefinition.facetDefinitions as facetDefinition>							
						.withFacet("${facetDefinition.name}")
						</#list>
						.withListFilterBuilderClass(${facetedQueryDefinition.listFilterClassName}.class)
						.withListFilterBuilderQuery("${facetedQueryDefinition.listFilterBuilderQuery}")
						<#if facetedQueryDefinition.hasGeoSearch()>
						.withGeoSearchQuery("${facetedQueryDefinition.geoSearchQuery}")
						</#if>
						.withCriteriaSmartType("${facetedQueryDefinition.criteriaSmartType}"))
				</#list><#lt>
				.build();
	}
}
