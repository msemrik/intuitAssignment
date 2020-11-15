package com.intuit.requesthandler;

//import com.bizzabo.assignment.controller.queryparams.ShowFilter;
//import com.bizzabo.assignment.controller.queryparams.ShowOrdering;
//import com.bizzabo.assignment.domain.*;
//import com.bizzabo.assignment.exceptions.ApiCallException;
//import com.bizzabo.assignment.exceptions.ConvertingResponseToDomainException;
//import com.bizzabo.assignment.repositories.*;
//import com.bizzabo.assignment.tvguide.TvGuide;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import com.intuit.crmHandler.BananaHandler;
//import com.intuit.crmHandler.StrawberryHandler;
import com.intuit.domain.Case;
import com.intuit.repositories.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
//
//import javax.transaction.Transactional;
//import java.util.*;

@Component
public class ApplicationRequestHandler {
//    @Autowired
//    ShowRepository showRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ShowUserRepository showUserRepository;
//
//    @Autowired
//    CastRepository castRepository;
//
//    @Autowired
//    TvGuide tvGuide;
//
//    @Value("${thresholdOfRecommendedShows}")
//    private int THRESHOLD_AMOUNT_OF_RECOMMENDED_SHOWS;
//
//    @Transactional
//    public Show addTVShowToUser(Long userId, Long tvShowId) throws ConvertingResponseToDomainException, ApiCallException {
//        User user = userRepository.findByUserId(userId);
//        if(user == null) {
//            user = userRepository.save(new User(userId, "Jonhy Workaround"));
//        }
//
//        List<Episode> episodes;
//
//        Show show = showRepository.findByShowId(tvShowId);
//        if(show == null) {
//            show = tvGuide.getShowInformation(tvShowId);
//            episodes = tvGuide.getShowEpisodes(show);
//            List<Cast> cast = tvGuide.getShowCast(show);
//            show.setEpisodes(episodes);
//            show.addCast(cast);
//            show = showRepository.save(show);
//        } else {
//            episodes = show.getEpisodes();
//        }
//
//        showUserRepository.save(new ShowUser(show, user, episodes.get(0)));
//
//        return show;
//    }
//
    @Autowired
    BananaHandler bananaHandler;

//    @Autowired
//    StrawberryHandler strawberryHandler;

    @Autowired
    CaseRepository caseRepository;

    @Transactional
    public List<Case> refreshData() throws Exception {

        List<Case> casesList = bananaHandler.getListOfCases();
        System.out.println(casesList);
        List<Case> casesStored = (List<Case>) caseRepository.saveAll(casesList);
        List<Case> casesRetrieved = (List<Case>) caseRepository.findAll();
        return casesList;
    }
//
//    public List<Show> getTVShowsForUser(Long userId, ShowFilter showFilter, ShowOrdering showOrdering) throws Exception {
//        List<Show> shows = new ArrayList<>();
//
//        if(showFilter == null || ShowFilter.ALL.equals(showFilter)) {
//            shows = showRepository.findByShowUsers_User_UserId(userId);
//        } else if (ShowFilter.ONLY_WITH_UNWATCHED_EPISODES.equals(showFilter)){
//            shows = showRepository.findByShowUsers_User_UserIdAndShowUsers_EpisodeIsNotNull(userId);
//        }
//
//        if(showOrdering != null) {
//            if(ShowOrdering.BY_FIRST_EPISODE_DATE.equals(showOrdering)) {
//                shows.sort(Comparator.comparing(show -> show.getEpisodes().get(0).getAirDate()));
//            } else if(ShowOrdering.BY_NEXT_UNWATCHED_EPISODE_DATE.equals(showOrdering)) {
//                shows.sort(Comparator.comparing(show -> show.getShowUsers().get(0).getEpisode().getAirDate()));
//            }
//        }
//
//        return shows;
//    }
//
//    public List<Show> getRecommendationBasedOnScheduledShows(Long userId) throws Exception {
//        List<Cast> casts = castRepository.findMostRepeatedCastInShowsScheduledByUser(userId);
//        List<Show> alreadySeeingShows = showRepository.findByShowUsers_User_UserId(userId);
//
//        return new LinkedList<>(getShowsForRecommendation(casts, alreadySeeingShows));
//    }
//
//    private Set<Show> getShowsForRecommendation(List<Cast> casts, List<Show> alreadySeeingShows) throws ApiCallException, ConvertingResponseToDomainException {
//        Set<Show> listOfShowsToReturn = new HashSet<>();
//        for(int i = 0; i < casts.size() && listOfShowsToReturn.size() < THRESHOLD_AMOUNT_OF_RECOMMENDED_SHOWS; i++) {
//            List<Show> showList = tvGuide.getCastShows(casts.get(i).getCastId());
//            showList.removeAll(alreadySeeingShows);
//            listOfShowsToReturn.addAll(showList);
//        }
//        return listOfShowsToReturn;
//    }
}
