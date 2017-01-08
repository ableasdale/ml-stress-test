<nav class="navbar navbar-toggleable-md navbar-light bg-faded rounded">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <a class="navbar-brand" href="http://0.0.0.0:8001" title="Go To the MarkLogic Server Admin Tool">
        <img src="/vendor/marklogic.png" class="nav-logo" alt="MarkLogic" />
    </a>


    <div class="collapse navbar-collapse" id="navbarNav">
        <div class="navbar-nav">
            <a class="nav-item nav-link<#if title = "Dashboard and Overview"> active</#if>" href="/">Dashboard <span class="sr-only">(current)</span></a>
            <a class="nav-item nav-link<#if title = "Archived Tests"> active</#if>" href="/archive">Archive</a>
            <a class="nav-item nav-link<#if title = "Active Jobs"> active</#if>" href="/jobs">Jobs</a>
            <!-- a class="nav-item nav-link disabled" href="#">Disabled</a -->
        </div>
    </div>
</nav>
