package meetifai.controller

import meetifai.service.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController("/persons")
class PersonController {

    @Autowired
    lateinit var personService: PersonService

    @GetMapping
    fun findAll() = personService.findAll()
}