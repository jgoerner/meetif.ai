package meetifai.clr

import meetifai.service.NeoService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(3)
class NeoExtractorCLR : CommandLineRunner {

    @Autowired
    lateinit var neoService: NeoService

    val logger: Logger =  LoggerFactory.getLogger(NeoExtractorCLR::class.java)


    override fun run(vararg args: String?) {
        // delete NEO4j stuff
        logger.info("Deleting all nodes and edges from Neo4j")
        neoService.deleteAll()

        // import
        logger.info("Importing RDF into Neo4j")
        neoService.initialRDFImport()


        logger.info("Got ${neoService.countAllNodes()} nodes in Neo4j")
    }

}