xquery version "1.0-ml";

declare variable $xml as element() :=
    <xml>
        <id>{xdmp:random()}</id>
    </xml>;

xdmp:document-insert(fn:concat("/",xdmp:random(),".xml"), $xml)