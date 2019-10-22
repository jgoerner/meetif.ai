package meetifai.service

import meetifai.repository.IceBreakerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class IceBreakerService {

    @Autowired
    lateinit var iceBreakerRepository: IceBreakerRepository

    fun findCommonEvents(p1: String, p2: String) = iceBreakerRepository.findCommonEvents(p1, p2)
}