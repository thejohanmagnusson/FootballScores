package barqsoft.footballscores.widget;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private final String[] SCORE_COLUMNS = {
        DatabaseContract.scores_table.HOME_COL,
        DatabaseContract.scores_table.AWAY_COL,
        DatabaseContract.scores_table.HOME_GOALS_COL,
        DatabaseContract.scores_table.AWAY_GOALS_COL,
        DatabaseContract.scores_table.LEAGUE_COL,
        DatabaseContract.scores_table.MATCH_DAY,
        DatabaseContract.scores_table.TIME_COL,
        DatabaseContract.scores_table.MATCH_ID
    };

    static final int COL_HOME = 0;
    static final int COL_AWAY = 1;
    static final int COL_HOME_GOALS = 2;
    static final int COL_AWAY_GOALS = 3;
    static final int COL_LEAGUE = 4;
    static final int COL_MATCHDAY = 5;
    static final int COL_MATCHTIME = 6;
    static final int COL_MATCHID = 7;

    Cursor data = null;

    Context context;
    Intent intent;

    private void InitData(){
        //check if any old data
        if(data != null)
            data.close();

        final long identityToken = Binder.clearCallingIdentity();

        Uri uri = DatabaseContract.scores_table.buildScoreWithDate();

        SimpleDateFormat simpleDate = new SimpleDateFormat(context.getString(R.string.date_format));
        String currentDate = simpleDate.format(new Date());

        data = context.getContentResolver().query(
                uri,
                SCORE_COLUMNS,
                null,
                new String[] {currentDate},
                null);

        Binder.restoreCallingIdentity(identityToken);
    }

    public WidgetDataProvider(Context context, Intent intent){
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        InitData();
    }

    @Override
    public void onDataSetChanged() {
        InitData();
    }

    @Override
    public void onDestroy() {
        if(data != null)
            data.close();
    }

    @Override
    public int getCount() {
        return data != null ? data.getCount() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (data == null || position == AdapterView.INVALID_POSITION || !data.moveToPosition(position))
            return null;

        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        String homeTeam = data.getString(COL_HOME);
        String awayTeam = data.getString(COL_AWAY);
        String score = Utilies.getScores(data.getInt(COL_HOME_GOALS), data.getInt(COL_AWAY_GOALS));
        String time = data.getString(COL_MATCHTIME);
        int imgHomeTeam = Utilies.getTeamCrestByTeamName(homeTeam);
        int imgAwayTeam = Utilies.getTeamCrestByTeamName(awayTeam);

        remoteView.setImageViewResource(R.id.widget_img_home_team, imgHomeTeam);
        remoteView.setTextViewText(R.id.widget_name_home_team, homeTeam);
        remoteView.setImageViewResource(R.id.widget_img_away_team, imgAwayTeam);
        remoteView.setTextViewText(R.id.widget_name_away_team, awayTeam);
        remoteView.setTextViewText(R.id.widget_score, score);
        remoteView.setTextViewText(R.id.widget_time, time);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (data.moveToPosition(position))
            return data.getLong(COL_MATCHID);

        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
