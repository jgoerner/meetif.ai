package meetifai.repository

import org.neo4j.driver.internal.InternalPath
import org.springframework.data.neo4j.annotation.Query
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface NeoRepository : Neo4jRepository<Any, Long> {

    @Query("MATCH (n) RETURN COUNT(n)")
    fun countAllNodes() : Int

    @Query("MATCH (n) DETACH DELETE n")
    override fun deleteAll()

    @Query("CREATE INDEX ON :Resource(uri)")
    fun createIndex()

    @Query("CALL " +
            "semantics.importRDF(\"http://rdf4j:8080/rdf4j-server/repositories/meetifai/statements\", " +
            "\"Turtle\", " +
            "{handleVocabUris: \"IGNORE\", " +
            "predicateExclusionList : " +
            "[ \"http://xmlns.com/foaf/0.1/member\", \"http://xmlns.com/foaf/0.1/based_near\", \"http://schema.org/organizer\"]})")
    fun importRDF()

    @Query("MATCH path=shortestPath((from {name:{nameFrom}})-[*]-(to {name:{nameTo}})) RETURN path")
    //@Query("MATCH (from {name:{nameFrom}}) RETURN from")
    fun getShortestPath(
            @Param("nameFrom") nameFrom: String,
            @Param("nameTo") nameTo: String
            ) : List<Map<String, InternalPath>>

}