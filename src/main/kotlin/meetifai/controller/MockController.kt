package meetifai.controller

import meetifai.misc.Event
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class MockController {
    @PostMapping("/mock/icebreaker/commonEvents")
    fun findCommonEvents(@RequestParam p1: String, @RequestParam p2: String) = listOf(Event("One mocked event"), Event("Another one"), Event("And a third one"))
}

