package meetifai.clr

import meetifai.misc.TripleStoreProperties
import meetifai.service.TripleStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(0)
@Component
class InitialTripleStoreCleaner : CommandLineRunner {

    @Autowired
    lateinit var tripleStoreService: TripleStoreService

    @Autowired
    lateinit var p: TripleStoreProperties

    override fun run(vararg args: String?) {
        tripleStoreService.deleteRepository(p.name)
    }

}