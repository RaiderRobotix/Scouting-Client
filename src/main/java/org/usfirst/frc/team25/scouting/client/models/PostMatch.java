package org.usfirst.frc.team25.scouting.client.models;



/**
 * Created by sng on 6/30/2016.
 */
public class PostMatch {

    String comment;


 

    final transient  String[] quickCommentValues = {
            "did not do much",
            "accurate shooter",
            "lost communications",
            "flipped over",
            "helped teammates",
            "defense",
            "good human player",
            "good driver",
            "efficient",
            "inefficient",
            "good pick",
            "bad pick",
            "to be replayed",
            "INCORRECT DATA"
    };

    public PostMatch(String comment) {
        this.comment = comment;
        
    }
}

