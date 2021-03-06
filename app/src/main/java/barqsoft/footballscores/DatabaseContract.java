package barqsoft.footballscores;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yehya khaled on 2/25/2015.
 */
public class DatabaseContract
{
    //URI data
    public static final String CONTENT_AUTHORITY = "barqsoft.footballscores";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_SCORES = "scores";

    public static final String SCORES_TABLE = "scores_table";
    public static final class scores_table implements BaseColumns
    {
        //Table data
        public static final String LEAGUE_COL = "league";
        public static final String DATE_COL = "date";
        public static final String TIME_COL = "time";
        public static final String HOME_COL = "home";
        public static final String AWAY_COL = "away";
        public static final String HOME_GOALS_COL = "home_goals";
        public static final String AWAY_GOALS_COL = "away_goals";
        public static final String MATCH_ID = "match_id";
        public static final String MATCH_DAY = "match_day";

        //public static Uri SCORES_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCORES)
                //.build();

        //Types
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCORES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCORES;

        public static Uri buildScoreWithLeague()
        {
            final String LEAGUE = "league";
            return BASE_CONTENT_URI.buildUpon().appendPath(LEAGUE).build();
        }

        public static Uri buildScoreWithId()
        {
            final String ID = "id";
            return BASE_CONTENT_URI.buildUpon().appendPath(ID).build();
        }

        public static Uri buildScoreWithDate()
        {
            final String DATE = "date";
            return BASE_CONTENT_URI.buildUpon().appendPath(DATE).build();
        }
    }
}
