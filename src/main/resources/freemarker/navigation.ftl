<div class="navbar navbar-default" role="navigation">
    <div class="container-fluid">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
            </button>
            <img style="max-width:150px; padding-top:8px;" id="nav-logo" src="/vendor/marklogic.png"/>
        </div>

        <div class="navbar-collapse collapse">
            <ul style="padding-left:1em;" class="nav navbar-nav">

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
    </div>
</div>
