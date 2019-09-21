package meetifai.service

import khttp.get
import khttp.post
import meetifai.misc.TripleStoreProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TripleStoreService {

    @Autowired
    lateinit var p: TripleStoreProperties

    val logger: Logger =  LoggerFactory.getLogger(TripleStoreService::class.java)

    fun createRepository(name: String)  {
        if(repositoryExists(name)) {
            logger.info("RDF Repository with the name '$name' already exists")
        } else {
            val endpoint = "http://${p.host}:${p.port}/rdf4j-workbench/repositories/NONE/create"
            val payload = mapOf(
                    "type" to p.type,
                    "Repository ID" to name,
                    "Repository title" to p.title,
                    "Persist" to p.persist,
                    "Sync delay" to p.delay.toInt()
            )
            when (post(endpoint, data=payload).statusCode) {
                200 -> logger.info("Successfully created RDF Repository with the name '$name'")
                500 -> logger.info("RDF4J Server Error while creating '$name")
                else -> throw Exception("Something went wrong when trying to create the RDF Repository '$name")
            }
        }
    }

    fun repositoryExists(name: String) : Boolean {
        val endpoint = "http://${p.host}:${p.port}/rdf4j-server/repositories/$name/size"
        return when(get(endpoint).statusCode) {
            404 -> false
            200 -> true
            else -> throw Exception("Something went wrong")
        }
    }
}