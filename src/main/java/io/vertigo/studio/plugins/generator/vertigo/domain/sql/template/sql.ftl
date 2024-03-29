-- ============================================================
--   SGBD      		  :  ${basecible}                     
-- ============================================================

<#if drop>
-- ============================================================
--   Drop                                       
-- ============================================================
<#list nnAssociations as associationDefinition>
drop table IF EXISTS ${associationDefinition.getTableName()} cascade;
</#list>
<#list dtDefinitions as dtDefinition>
drop table IF EXISTS ${dtDefinition.localName} cascade;
<#if dtDefinition.hasSequence()>
drop sequence IF EXISTS SEQ_${dtDefinition.localName};
</#if>
</#list>

</#if>

<#if tableSpaceData?has_content>
-- Tablespace des données.
\set TABLESPACE_NAME_DATA '${tableSpaceData}'
</#if>

<#if tableSpaceIndex?has_content>
-- Tablespace des indexes.
\set TABLESPACE_NAME_INDEX '${tableSpaceIndex}'
</#if>

-- ============================================================
--   Sequences                                      
-- ============================================================
<#list dtDefinitions as dtDefinition>
<#if dtDefinition.hasSequence()>
create sequence SEQ_${dtDefinition.localName}
	start with 1000 cache 1; 
</#if>

</#list>

<#list dtDefinitions as dtDefinition>
-- ============================================================
--   Table : ${dtDefinition.localName}                                        
-- ============================================================
create table ${dtDefinition.localName}
(
	<#list dtDefinition.fields as field>
	<#if field.persistent>
    ${field.constName?right_pad(12)}${"\t"} ${sql(field)?right_pad(12)}${"\t"}<#if field.required>not null</#if>,
    </#if><#-- field.persistent -->
    </#list><#-- fieldCollection -->
    <#list dtDefinition.fields as field>
    <#if field.persistent>
    <#if field.id >
    constraint PK_${dtDefinition.localName} primary key (${field.constName})<#if tableSpaceIndex?has_content> USING INDEX TABLESPACE :TABLESPACE_NAME_INDEX</#if>
    </#if><#-- field.type -->
    </#if><#-- field.persistent -->
    </#list>
)<#if tableSpaceData?has_content>
TABLESPACE :TABLESPACE_NAME_DATA</#if>;

<#list dtDefinition.fields as field>
<#if field.persistent>
<#if field.display?has_content>
comment on column ${dtDefinition.localName}.${field.constName} is
'${field.display?replace("'","''")}';

</#if>
</#if>
</#list>
</#list>

<#list simpleAssociations as associationDefinition>
<#assign associationLocalName = associationDefinition.getName()> 
alter table ${associationDefinition.foreignTable}
	add constraint FK_${associationLocalName}_${associationDefinition.primaryTable} foreign key (${associationDefinition.foreignColumn})
	references ${associationDefinition.primaryTable} (${associationDefinition.primaryIdColumn});

create index ${associationLocalName}_${associationDefinition.primaryTable}_FK on ${associationDefinition.foreignTable} (${associationDefinition.foreignColumn} asc)<#if tableSpaceIndex?has_content>
TABLESPACE :TABLESPACE_NAME_INDEX</#if>;

</#list>

<#list nnAssociations as associationDefinition>
<#assign associationLocalName = associationDefinition.getName()> 
create table ${associationDefinition.tableName}
(
	${associationDefinition.nodeAPKName?right_pad(12)}${"\t"} ${sql(associationDefinition.nodeAPKDomain)?right_pad(12)}${"\t"} not null,
	${associationDefinition.nodeBPKName?right_pad(12)}${"\t"} ${sql(associationDefinition.nodeBPKDomain)?right_pad(12)}${"\t"} not null,
	constraint PK_${associationDefinition.getTableName()} primary key (${associationDefinition.nodeAPKName}, ${associationDefinition.nodeBPKName})<#if tableSpaceIndex?has_content> USING INDEX TABLESPACE :TABLESPACE_NAME_INDEX</#if>,
	constraint FK_${associationLocalName}_${associationDefinition.nodeATableName} 
		foreign key(${associationDefinition.nodeAPKName})
		references ${associationDefinition.nodeATableName} (${associationDefinition.nodeAPKName}),
	constraint FK_${associationLocalName}_${associationDefinition.nodeBTableName} 
		foreign key(${associationDefinition.nodeBPKName})
		references ${associationDefinition.nodeBTableName} (${associationDefinition.nodeBPKName})
)<#if tableSpaceData?has_content>
TABLESPACE :TABLESPACE_NAME_DATA</#if>;

create index ${associationLocalName}_${associationDefinition.nodeATableName}_FK on ${associationDefinition.tableName} (${associationDefinition.nodeAPKName} asc)<#if tableSpaceIndex?has_content>
TABLESPACE :TABLESPACE_NAME_INDEX</#if>;

create index ${associationLocalName}_${associationDefinition.nodeBTableName}_FK on ${associationDefinition.tableName} (${associationDefinition.nodeBPKName} asc)<#if tableSpaceIndex?has_content>
TABLESPACE :TABLESPACE_NAME_INDEX</#if>;

</#list>
