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

    fun countryMatch(city1: String, city2: String) : Boolean {
        val query = tripleStoreService.loadQuery("matchingCountries")
                .replace("CITY_1_NAME", city1)
                .replace("CITY_2_NAME", city2)

        tripleStoreService.dbpediaRepository.connection.use {con ->
            //con.begin(IsolationLevels.NONE)
            val result = con.prepareTupleQuery(query).evaluate()
            return if (result.hasNext()) {
                result.next().let {
                    it.getValue("country1").stringValue() == it.getValue("country2").stringValue()
                }
            } else {
                false
            }
        }
    }

}