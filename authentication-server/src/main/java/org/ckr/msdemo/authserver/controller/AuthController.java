package org.ckr.msdemo.authserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/11/24.
 */
@RestController
public class AuthController {

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public Object retrieveAccess(@PathVariable("userId") String userName) {
        return "asdfsfd";
    }


//    @RequestMapping(value = "/error")
//    public Object error() {
//        return null;
//    }
}
