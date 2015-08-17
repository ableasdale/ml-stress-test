<div class="navbar navbar-default" role="navigation">
    <ul class="nav navbar-nav">

        <#if title = "Dashboard and Overview">
        <li class="active"><#else>
        <li></#if><a href="/">Dashboard</a></li>
<!--
        <#if title = "Orphaned Properties Fragments">
        <li class="active"><#else>
        <li></#if><a href="/orphans">Orphans</a></li>

        <#if title = "Details by Date">
            <li class="dropdown active"><#else><li class="dropdown"></#if>
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">All Dates (${allKnownDates?size})<span class="caret"></span></a>
            <ul class="dropdown-menu scrollable-menu">
            <#list allKnownDates as date>
                <li><a href="/date/${date}">${date}</a></li>
            </#list>
            </ul>
        </li>

        <#if title = "Details by Forest">
        <li class="dropdown active"><#else><li class="dropdown"></#if>
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Forest <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
            <#list dataSet?keys as key><li><a href="/forest/${key}">${key}</a></li></#list>
            </ul>
        </li>

        <#if title = "Details by Host">
            <li class="dropdown active"><#else><li class="dropdown"></#if>
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Host <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
            <#list hostData as h><li><a href="/host/${h}">${h}</a></li></#list>
            </ul>
        </li-->

</div>
