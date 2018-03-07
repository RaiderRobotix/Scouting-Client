package org.usfirst.frc.team25.scouting.client.data;

import com.thebluealliance.api.v3.models.Match;
import com.thebluealliance.api.v3.models.SimpleMatch;
import com.thebluealliance.api.v3.models.SimpleTeam;
import com.thebluealliance.api.v3.models.Team;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Collection of static methods to sort and filter ArrayLists of object models
 *
 * @author sng
 */
class Sorters {

    /**
     * Method implementing a Comparator to sort Matches
     *
     * @param matches ArrayList of Match objects
     * @return Same list, sorted by ascending match number
     */
    static ArrayList<Match> sortByMatchNum(ArrayList<Match> matches) {

        matches.sort(Comparator.comparingInt(SimpleMatch::getMatchNumber));
        return matches;
    }

    /**
     * Method implementing a Comparator to sort Teams
     *
     * @param events List of Team objects
     * @return Same list, sorted by ascending team number
     */
    static ArrayList<Team> sortByTeamNum(ArrayList<Team> events) {

        events.sort(Comparator.comparingInt(SimpleTeam::getTeamNumber));
        return events;
    }

    /**
     * Removes all Matches from a list besides qualification matches.
     * Qualification matches denoted by <code>comp_level</code> "qm"
     *
     * @param matches ArrayList of Match objects
     * @return Modified ArrayList
     */
    static ArrayList<Match> filterQualification(ArrayList<Match> matches) {
        for (int i = 0; i < matches.size(); i++) {

            if (!matches.get(i).getCompLevel().equals("qm")) {
                matches.remove(i);
                i--;
            }
        }
        return matches;
    }
}
