package meetifai.controller

import meetifai.service.IceBreakerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class IcebreakerController {

    @Autowired
    lateinit var iceBreakerService: IceBreakerService

    @PostMapping("/icebreaker/commonEvents")
    fun findCommonEvents(@RequestParam p1: String, @RequestParam p2: String) = iceBreakerService.findCommonEvents(p1, p2)
}