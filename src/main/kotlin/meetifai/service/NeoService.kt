package meetifai.service

import meetifai.misc.Event
import meetifai.misc.Person
import meetifai.repository.NeoRepository
import org.neo4j.driver.internal.InternalPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
@Service
class NeoService {

    @Autowired
    lateinit var neoRepository: NeoRepository

    fun deleteAll()  = neoRepository.deleteAll()

    fun initialRDFImport() {
        neoRepository.createIndex()
        neoRepository.importRDF()
    }

    fun countAllNodes() = neoRepository.countAllNodes()

    fun getShortestPath(fromName: String, toName: String) = neoRepository.getShortestPath(fromName, toName).map {mapPath(it["path"])}

    fun mapPath(path: InternalPath?) : Map<Int, Any>{
        val result = mutableMapOf<Int, Any>()
        val p = path ?: return result
        var cnt = 0;
        p.nodes().forEach{ node ->
            val props = node.asMap()
            when {
                node.hasLabel("Person") -> result[cnt++] = Person(-1, props["name"].toString(), "", props["thumbnail"].toString())
                node.hasLabel("Event") -> result[cnt++] = Event(props["label"].toString())
            }
        }
        return result.toMap()
    }

}