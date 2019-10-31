package meetifai.service

import com.taxonic.carml.engine.RmlMapper
import com.taxonic.carml.logical_source_resolver.JsonPathResolver
import com.taxonic.carml.util.RmlMappingLoader
import com.taxonic.carml.vocab.Rdf
import khttp.get
import khttp.post
import meetifai.misc.RMLProperties
import meetifai.misc.SparqlProperties
import meetifai.misc.TripleStoreProperties
import org.eclipse.rdf4j.model.Model
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.model.impl.TreeModel
import org.eclipse.rdf4j.model.util.ModelBuilder
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
    lateinit var sparqlProperties: SparqlProperties

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

    fun createDBPediaProxy() {
        val endpoint = "http://${rdfP.host}:${rdfP.port}/rdf4j-workbench/repositories/NONE/create"
        val payload = mapOf(
                "type" to "sparql",
                "Local repository ID" to "dbp",
                "Repository title" to "DBPedia SPARQL proxy",
                "SPARQL query endpoint" to "http://dbpedia.org/sparql"
        )
        when (post(endpoint, data=payload).statusCode) {
            200 -> logger.info("Successfully created DBPedia SPARQL proxy")
            500 -> logger.info("RDF4J Server Error while creating DBPedia SPARQL proxy")
            else -> throw Exception("Something went wrong when trying to create the DBPedia SPARQL proxy")
        }

    }

    fun deleteRepository(name: String) {
        if(!repositoryExists(name)) return
        val endpoint = "http://${rdfP.host}:${rdfP.port}/rdf4j-workbench/repositories/$name/delete"
        when (post(endpoint).statusCode) {
            200 -> logger.info("Deleted repository $name")
            else -> logger.info("Something else happened")
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

    fun initialDataLift() {

        // (0) crete an empty model to be filled w/ statically and dynamically mapped data
        val model: Model = TreeModel()

        // (I) Map JSON w/ static mapping file

        // prepare a mapper
        val staticMapper = RmlMapper
                .newBuilder()
                .setLogicalSourceResolver(Rdf.Ql.JsonPath, JsonPathResolver())
                .fileResolver(File(rmlP.staticDataDir).toPath())
                .build()

        // collect mapping file from RMLProperties
        val mappingFiles = File(rmlP.staticMappingDir).listFiles()
                ?.filter { it.absolutePath.endsWith("ttl") }
                ?.map { it.toPath() }
                ?.toTypedArray()
                ?: emptyArray()

        // prepare the mapping
        val staticMapping = RmlMappingLoader
                .build()
                .load(RDFFormat.TURTLE, *mappingFiles)

        // map em all
        model.addAll(staticMapper.map(staticMapping))

        // (2) Map JSON w/ dynamically created mappings

        // prepare a mapper
        val dynamicMapper = RmlMapper
                .newBuilder()
                .setLogicalSourceResolver(Rdf.Ql.JsonPath, JsonPathResolver())
                .fileResolver(File(rmlP.dynamicDataDir).toPath())
                .build()

        val dynamicMappingLoader = RmlMappingLoader.build()

        // dynamically create mapping files
        File(rmlP.dynamicDataDir).listFiles()
                ?.map{ it.nameWithoutExtension }
                ?.forEach {
                    val dynamicMapping = dynamicMappingLoader.load(getLabMappingModel(it))
                    model.addAll(dynamicMapper.map(dynamicMapping))
                }
        // final persistence
        persistModel(model)
    }

    fun getLabMappingModel(fileName: String = "lab") : Model {
        val vf = SimpleValueFactory.getInstance()
        val logicalSource = vf.createBNode()
        val subjectMap = vf.createBNode()
        val predicateObjectMap = vf.createBNode()

        val m = ModelBuilder()
                .setNamespace("rr", "http://www.w3.org/ns/r2rml#")
                .setNamespace("rml", "http://semweb.mmlab.be/ns/rml#")
                .setNamespace("ql", "http://semweb.mmlab.be/ns/ql#")
                .setNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#")
                .setNamespace("mtfai", "http://meetif.ai/")
                .subject("mtfai:Dynamic_$fileName")
                .add("rml:logicalSource", logicalSource)
                .add("rr:subjectMap", subjectMap)
                .add("rr:predicateObjectMap", predicateObjectMap)
                .subject(logicalSource)
                .add("rml:source", "$fileName.json")
                .add("rml:referenceFormulation", "ql:JSONPath")
                .add("rml:iterator", "$.[*]")
                .subject(subjectMap)
                .add("rr:template", "http://meetif.ai/{member.id}")
                .subject(predicateObjectMap)
                .add("rr:predicate", "mtfai:participatedIn")
                .add("rr:object", "mtfai:$fileName")
                .build()
        return m
    }

    fun persistModel(model: Model) {
        repository.connection.use { it.add(model) }
    }

    fun loadQuery(name: String) = File("${sparqlProperties.queryDir}/$name.sparql").readText()

    val repository
        get() = HTTPRepository("http://${rdfP.host}:${rdfP.port}/rdf4j-server/repositories/${rdfP.name}")

    val dbpediaRepository
        get() = HTTPRepository("http://${rdfP.host}:${rdfP.port}/rdf4j-server/repositories/dbp")

}