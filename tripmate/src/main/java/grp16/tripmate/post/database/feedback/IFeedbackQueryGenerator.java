package grp16.tripmate.post.database.feedback;

import grp16.tripmate.post.model.feedback.Feedback;

public interface IFeedbackQueryGenerator {
    String createFeedback(Feedback feedback);

    String deleteFeedbackByPostId(int postId);

    String getFeedbacksByPostId(int postId);
}