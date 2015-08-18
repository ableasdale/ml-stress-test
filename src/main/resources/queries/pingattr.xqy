xquery version "1.0-ml";

(: Simple "ping" script - identifies the number of fragments inserted within a space of a minute and also returns the time taken to execute the query :)

let $start := xs:dateTime(fn:current-dateTime()) - xs:dayTimeDuration('PT2M')
let $end := xs:dateTime(fn:current-dateTime()) - xs:dayTimeDuration('PT1M')

return

fn:count(
    cts:search(doc(),
        cts:and-query((
            cts:element-attribute-range-query(xs:QName("case"), xs:QName("updated_at"), ">", $start),
            cts:element-attribute-range-query(xs:QName("case"), xs:QName("updated_at"), "<", $end)
        ))
    )
), xdmp:elapsed-time()
