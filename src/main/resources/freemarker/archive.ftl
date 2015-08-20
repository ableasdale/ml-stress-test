<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">
    <div class="row">
        <h2>Stress Test <small>${title}</small></h2>
        <#include "navigation.ftl">
    </div>

    <h3>Saved Stress Tests</h3>
    <div class="row">
        <div class="list-group">
            <#list filenames as fname>
                <a href="/archive/load/${fname}"><button type="button" class="list-group-item">${fname}</button></a>
            </#list>
        </div>
    </div>

</div>

<#include "footer.ftl">

</body>
</html>