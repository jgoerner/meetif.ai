package meetifai.repository

import meetifai.misc.Event
import meetifai.service.TripleStoreService
import org.eclipse.rdf4j.query.QueryResults
import org.eclipse.rdf4j.repository.util.Repositories
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class IceBreakerRepository {

    @Autowired
    lateinit var tripleStoreService: TripleStoreService

    fun findCommonEvents(p1: String, p2 : String) : List<Event> {
        val query = tripleStoreService.loadQuery("findCommonEvents")
                .replace("PERSON_1_ID", p1)
                .replace("PERSON_2_ID", p2)
        return Repositories.tupleQuery(
                tripleStoreService.repository,
                query)
        { QueryResults.asList(it)}
                .map { bs -> Event(name = bs.getValue("name").stringValue() ?: "No Party") }
    }

}