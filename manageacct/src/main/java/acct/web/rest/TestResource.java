package acct.web.rest;

import core.core.BaseResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource extends BaseResource {
    @RequestMapping("")
    public String get(){
        return "1";
    }
}
