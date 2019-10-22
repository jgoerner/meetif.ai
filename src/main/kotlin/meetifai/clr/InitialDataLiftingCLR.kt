package meetifai.clr

import meetifai.service.LabService
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

    @Autowired
    lateinit var labService: LabService

    override fun run(vararg args: String?) {
        // to be deleted
        labService.getLabMappingModel()
        tripleStoreService.initialDataLift() }

}