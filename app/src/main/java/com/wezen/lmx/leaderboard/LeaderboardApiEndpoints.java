package com.wezen.lmx.leaderboard;

import com.wezen.lmx.model.Team;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by eder on 23/03/2016.
 */
public interface LeaderboardApiEndpoints {

    @GET("/api/team")
    Observable<List<Team>> getTeams();

}
