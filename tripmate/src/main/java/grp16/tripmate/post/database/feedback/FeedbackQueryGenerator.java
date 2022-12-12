package grp16.tripmate.post.database.feedback;

import grp16.tripmate.post.model.feedback.Feedback;
import grp16.tripmate.logger.ILogger;
import grp16.tripmate.logger.MyLoggerAdapter;

public class FeedbackQueryGenerator implements IFeedbackQueryGenerator {

    static IFeedbackQueryGenerator instance;
    private final ILogger logger = new MyLoggerAdapter(this);

    private FeedbackQueryGenerator() {
        //Required empty constructor
    }

    public static IFeedbackQueryGenerator getInstance() {
        if (instance == null) {
            instance = new FeedbackQueryGenerator();
        }
        return instance;
    }

    @Override
    public String createFeedback(Feedback feedback) {

        String query = "INSERT INTO " +
                FeedbackDbColumnNames.TABLE_NAME +
                " (" +
                FeedbackDbColumnNames.POST_ID + "," +
                FeedbackDbColumnNames.USER_ID + "," +
                FeedbackDbColumnNames.FEEDBACK + "," +
                FeedbackDbColumnNames.RATING +
                ")" +
                " VALUES " +
                "(" +
                feedback.getPostId() + "," +
                feedback.getUserId() + "," +
                " '" + feedback.getFeedback() + "' " + "," +
                feedback.getRating() + ")";

        logger.info(query);
        return query;
    }

    public String deleteFeedbackByPostId(int postId) {
        String query = "DELETE FROM " +
                FeedbackDbColumnNames.TABLE_NAME +
                " WHERE " + FeedbackDbColumnNames.POST_ID + " = " + postId;

        logger.info(query);

        return query;
    }

    @Override
    public String getFeedbacksByPostId(int postId) {
        String query = "select * from " + FeedbackDbColumnNames.TABLE_NAME + " where " + FeedbackDbColumnNames.TABLE_NAME + " = " + postId;
        logger.info(query);
        return query;
    }
}