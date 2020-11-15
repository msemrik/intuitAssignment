package com.intuit.controller;

import com.intuit.controller.requestobjects.CaseSearchFilter;
import com.intuit.domain.Case;
import com.intuit.requesthandler.ApplicationRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ApplicationController {

    @Autowired
    private ApplicationRequestHandler applicationRequestHandler;

    @PostMapping(value = "/cases")
    public ResponseEntity getCases(@RequestBody CaseSearchFilter caseSearchFilter) {
        try {
            System.out.println(caseSearchFilter);
            return ResponseEntity.status(HttpStatus.OK).build();
//            return ResponseEntity.status(HttpStatus.OK).body(tvShowHandler.addTVShowToUser(userId, tvShowId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception while trying to search " +
                    "cases : " + caseSearchFilter + ". Error: " + e.getMessage());
        }
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity refresh() {
        try {
            List<Case> casesList = applicationRequestHandler.refreshData();
            System.out.println(casesList);

//            return ResponseEntity.status(HttpStatus.OK).build();
            return ResponseEntity.status(HttpStatus.OK).body(casesList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception while trying to refresh " +
                    "cases. Error: " + e.getMessage());
        }
    }

//    @DeleteMapping("/{userId}/{tvShowId}")
//    public ResponseEntity removeTVShowFromUser(@PathVariable Long userId, @PathVariable Long tvShowId) {
//        try {
//            tvShowHandler.removeTVShowFromUser(userId, tvShowId);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to remove tv show: " + tvShowId + " from user: " + userId + ". Error: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity getTVShowsForUser(@PathVariable Long userId, @RequestParam(required = false) ShowFilter filter, @RequestParam(required = false) ShowOrdering ordering) {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(tvShowHandler.getTVShowsForUser(userId, filter, ordering));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to get show for user: " + userId + ". Error: " + e.getMessage());
//        }
//    }
//
//
//    @GetMapping("/{userId}/recommendations")
//    public ResponseEntity getRecommendationBasedOnScheduledShows(@PathVariable Long userId) {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(tvShowHandler.getRecommendationBasedOnScheduledShows(userId));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to get recommendation shows for user: " + userId + ". Error: " + e.getMessage());
//        }
//    }
}
