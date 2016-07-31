package com.shaunmccready.controller;

import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/assign", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO assignUser(
            @RequestParam(value = "url" , required = true , defaultValue = "none") String incomingUrl) throws EventException {
        return userService.assignUser(incomingUrl);
    }

    @RequestMapping(value = "/unassign", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO unassignUser(
            @RequestParam(value = "url" , required = true , defaultValue = "none") String incomingUrl) throws EventException {
        return userService.unassignUser(incomingUrl);
    }

}
