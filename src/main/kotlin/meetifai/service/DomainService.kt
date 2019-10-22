package meetifai.service

import meetifai.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonService {

    @Autowired
    lateinit var personRepository: PersonRepository

    fun findAll() = personRepository.findAll()

}