package meetifai.misc

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("triplestore")
class TripleStoreProperties{
    lateinit var host: String
    lateinit var port: String
    lateinit var name: String
    lateinit var title: String
    lateinit var type: String
    var persist: Boolean = true
    lateinit var delay: Number
}

@Component
@ConfigurationProperties("rml")
class RMLProperties{
    lateinit var staticDataDir: String
    lateinit var staticMappingDir: String
    lateinit var dynamicDataDir: String
}

@Component
@ConfigurationProperties("sparql")
class SparqlProperties{
    lateinit var queryDir: String
}
