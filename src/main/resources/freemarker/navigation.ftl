<div class="navbar navbar-default" role="navigation">
    <ul class="nav navbar-nav">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <img style="margin: 0.65em 0.4em 0 0.7em;" src="/vendor/marklogic.png"/>
        </div>

    <#if title = "Dashboard and Overview">
    <li class="active"><#else>
    <li></#if><a href="/">Dashboard</a></li>

    <#if title = "Archived Tests">
    <li class="active"><#else>
    <li></#if><a href="/archive">Archive</a></li>

    <#if title = "Active Jobs">
    <li class="active"><#else>
    <li></#if><a href="/jobs">Jobs</a></li>

    </ul>
</div>
