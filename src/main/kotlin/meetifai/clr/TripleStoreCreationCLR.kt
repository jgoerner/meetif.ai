package meetifai.clr

import meetifai.misc.TripleStoreProperties
import meetifai.service.TripleStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class TripleStoreCreationCLR : CommandLineRunner {

    @Autowired
    lateinit var tripleStoreService: TripleStoreService

    @Autowired
    lateinit var p: TripleStoreProperties

    override fun run(vararg args: String?) {
        tripleStoreService.createRepository(p.name)
    }
}