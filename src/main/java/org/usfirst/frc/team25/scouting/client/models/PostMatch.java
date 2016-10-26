package org.usfirst.frc.team25.scouting.client.models;



/** PostMatch object model to deserialize JSON data. 
 *  Modify as necessary for each season.
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

