package meetifai.repository

import meetifai.misc.Person
import meetifai.misc.SparqlProperties
import meetifai.service.TripleStoreService
import org.eclipse.rdf4j.query.QueryResults
import org.eclipse.rdf4j.repository.util.Repositories
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class PersonRepository {

    @Autowired
    lateinit var sparqlProperties: SparqlProperties

    @Autowired
    lateinit var tripleStoreService: TripleStoreService

    fun findAll() = Repositories.tupleQuery(
            tripleStoreService.repository,
            tripleStoreService.loadQuery("findAllPersons"))
            { QueryResults.asList(it)}
                .map { bs -> Person(
                        id =  bs.getValue("id").stringValue()?.toInt() ?: 0,
                        name = bs.getValue("name").stringValue() ?: "John Doe",
                        location = bs.getValue("location").stringValue() ?: "Nowhere",
                        imgURL = bs.getValue("imgURL").stringValue() ?: ""
                )}

}