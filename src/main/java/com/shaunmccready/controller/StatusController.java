package com.shaunmccready.controller;

import com.shaunmccready.dto.StatusDTO;
import com.shaunmccready.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatusDTO getStatus(
            @PathVariable("id") Integer sid) throws Exception {
        return statusService.getStatus(sid);
    }

}
