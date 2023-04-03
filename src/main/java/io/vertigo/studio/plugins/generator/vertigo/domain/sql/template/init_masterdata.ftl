-- ============================================================
--   Insert MasterData values : ${masterdata.tableName}                                        
-- ============================================================
<#list masterdata.values as value>
insert into ${masterdata.tableName}(<#list masterdata.definition.fields as field><#if field.persistent>${field.constName}<#sep>, </#sep></#if></#list>) values (<#list masterdata.definition.fields as field><#if field.persistent>${value.getFieldValue(field)!"null"}<#sep>, </#sep></#if></#list>);
</#list>