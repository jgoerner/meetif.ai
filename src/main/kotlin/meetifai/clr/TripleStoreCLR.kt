package meetifai.clr

import meetifai.misc.TripleStoreProperties
import meetifai.service.TripleStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class TripleStoreCLR : CommandLineRunner {

    @Autowired
    lateinit var tripleStoreService: TripleStoreService

    @Autowired
    lateinit var p: TripleStoreProperties

    override fun run(vararg args: String?) {
        // create repository when not already existing
        tripleStoreService.createRepository(p.name)
    }
}