<#-- Template pour générer une classe Java à partir d'une Entity -->

<#assign hasList = false>
<#list attributes as attr>
  <#if attr.domainType.isMultivalued?? && attr.domainType.isMultivalued>
    <#assign hasList = true>
  </#if>
</#list>
<#if hasList>
import java.util.List;
</#if>
<#list attributes as attr>
  <#if attr.domainType.baseType == "Decimal">
import java.math.BigDecimal;
    <#break>
  </#if>
</#list>
<#list attributes as attr>
  <#if attr.domainType.baseType == "Date">
import java.time.LocalDate;
    <#break>
  </#if>
</#list>

public final class ${name} {

<#list attributes as attr>
    public final ${attr.domainType.javaType} ${attr.name};
</#list>

    public ${name}(
<#list attributes as attr>
        ${attr.domainType.javaType} ${attr.name}<#if attr_has_next>,</#if>
</#list>
    ) {
<#list attributes as attr>
        this.${attr.name} = ${attr.name};
</#list>
    }
}
