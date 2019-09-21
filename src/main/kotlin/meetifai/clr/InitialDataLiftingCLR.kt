package meetifai.clr

import meetifai.service.TripleStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
class InitialDataLiftingCLR : CommandLineRunner {

    @Autowired
    lateinit var tripleStoreService: TripleStoreService

    override fun run(vararg args: String?) {
        println("Now I am doing some stuff")
        tripleStoreService.liftPeople()
    }

}