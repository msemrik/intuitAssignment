package com.intuit.controller;

import com.intuit.controller.requestobjects.CaseSearchFilter;
import com.intuit.domain.AggregatedResult;
import com.intuit.domain.Case;
import com.intuit.requesthandler.ApplicationRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ApplicationController {

    @Autowired
    private ApplicationRequestHandler applicationRequestHandler;

    @GetMapping(value = "/myAggregatedHub")
    public ResponseEntity getCases(@RequestBody CaseSearchFilter caseSearchFilter) {
        try {
            List<AggregatedResult> aggregatedResults = applicationRequestHandler.getCasesForRequest(caseSearchFilter);
            return ResponseEntity.status(HttpStatus.OK).body(aggregatedResults);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception while trying to search " +
                    "cases : " + caseSearchFilter + ". Error: " + e.getMessage());
        }
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity refresh() {
        try {
            List<Case> casesList = applicationRequestHandler.refreshDataForRequest();
            return ResponseEntity.status(HttpStatus.OK).body(casesList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception while trying to refresh " +
                    "cases. Error: " + e.getMessage());
        }
    }
}
