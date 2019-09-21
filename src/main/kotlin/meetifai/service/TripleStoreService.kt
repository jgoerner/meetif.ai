package meetifai.service

import com.taxonic.carml.engine.RmlMapper
import com.taxonic.carml.logical_source_resolver.JsonPathResolver
import com.taxonic.carml.util.RmlMappingLoader
import com.taxonic.carml.vocab.Rdf
import khttp.get
import khttp.post
import meetifai.misc.RMLProperties
import meetifai.misc.TripleStoreProperties
import org.eclipse.rdf4j.model.Model
import org.eclipse.rdf4j.repository.http.HTTPRepository
import org.eclipse.rdf4j.rio.RDFFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class TripleStoreService {

    @Autowired
    lateinit var rdfP: TripleStoreProperties

    @Autowired
    lateinit var rmlP: RMLProperties

    val logger: Logger =  LoggerFactory.getLogger(TripleStoreService::class.java)

    fun createRepository(name: String)  {
        if(repositoryExists(name)) {
            logger.info("RDF Repository with the name '$name' already exists")
        } else {
            val endpoint = "http://${rdfP.host}:${rdfP.port}/rdf4j-workbench/repositories/NONE/create"
            val payload = mapOf(
                    "type" to rdfP.type,
                    "Repository ID" to name,
                    "Repository title" to rdfP.title,
                    "Persist" to rdfP.persist,
                    "Sync delay" to rdfP.delay.toInt()
            )
            when (post(endpoint, data=payload).statusCode) {
                200 -> logger.info("Successfully created RDF Repository with the name '$name'")
                500 -> logger.info("RDF4J Server Error while creating '$name")
                else -> throw Exception("Something went wrong when trying to create the RDF Repository '$name")
            }
        }
    }

    fun repositoryExists(name: String) : Boolean {
        val endpoint = "http://${rdfP.host}:${rdfP.port}/rdf4j-server/repositories/$name/size"
        return when(get(endpoint).statusCode) {
            404 -> false
            200 -> true
            else -> throw Exception("Something went wrong")
        }
    }

    fun liftPeople() {

        // collect mapping file from RMLProperties
        val mappingFiles = File(rmlP.mappingDir).listFiles()
                ?.filter { it.absolutePath.endsWith("ttl") }
                ?.map { it.toPath() }
                ?.toTypedArray()
                ?: emptyArray()

        // prepare the mapping
        val mapping = RmlMappingLoader
                .build()
                .load(RDFFormat.TURTLE, *mappingFiles)

        // prepare the mapper
        val mapper = RmlMapper
                .newBuilder()
                .setLogicalSourceResolver(Rdf.Ql.JsonPath, JsonPathResolver())
                .fileResolver(File(rmlP.dataDir).toPath())
                .build()

        val model = mapper.map(mapping)

        persistModel(model)

    }

    fun persistModel(model: Model) {
        HTTPRepository("http://${rdfP.host}:${rdfP.port}/rdf4j-server/repositories/${rdfP.name}").connection.use { it.add(model) }
    }
}