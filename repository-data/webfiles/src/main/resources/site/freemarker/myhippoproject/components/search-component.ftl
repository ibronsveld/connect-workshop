<#include "../../include/imports.ftl">

<@hst.link var="actionLink" siteMapItemRefId="root"/>

<form method="get">
  <div class="form-group">
    <label for="search">Search Term</label>
    <input type="text" class="form-control" id="search" name="search" placeholder="query">
  </div>
  <button type="submit" class="btn btn-default">Search</button>
</form>

<#if variables??>
  <#if variables["query"]??>
    <#assign search=variables["query"] />
  </#if>
<h1>Searching for: ${search}</h1>
</#if>

<#if results??>

  <#if searchType??>

    <#switch searchType>

      <#case "search2">
        <#assign values=results.valueMap/>
        <#if values??>
        <div>
          <h2>${values["totalResults"]} Results:</h2>

          <#list values["Search"]["children"]["collection"] as c>
            <#assign result = c.valueMap />
            <div class="row">
              <div class="col-md-4">
                <img src="${result["Poster"]}"/>
              </div>
              <div class="col-md-8">
                <h3>${result["Title"]} (${result["Year"]})</h3>
                <span>ID: ${result["imdbID"]}</span>
              </div>
            </div>
          </#list>
        </#if>
      </div>

        <#break>

      <#case "search1">

      <#--TODO: Template here-->
    <div>
      <h2>${results["childCount"]} Results:</h2>

      <#if results["childCount"] gt 0>
        <#list results["children"]["collection"] as c>
          <#assign result = c.valueMap />
          <div class="row">
            <div class="col-md-4">
              <img src="${result["extendedData"]["valueMap"]["image"]}" style="max-width: 80px"/>
            </div>
            <div class="col-md-8">
              <h3>${result["name"]} (${result["id"]})</h3>
              <span>
              ${result["description"]}
      </span>
            </div>
          </div>
        </#list>
      </#if>
    </div>
        <#break>

    </#switch>
  </#if>
<#else>
<h1>No Results</h1>
</#if>
