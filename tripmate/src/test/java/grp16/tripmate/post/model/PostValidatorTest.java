package grp16.tripmate.post.model;

import grp16.tripmate.post.model.exception.MinAgeGreaterThanMaxAgeException;
import grp16.tripmate.post.model.exception.StartDateAfterEndDateException;
import grp16.tripmate.post.model.exception.StartDateBeforeTodayException;
import grp16.tripmate.post.model.factory.IPostFactory;
import grp16.tripmate.post.model.factory.PostFactory;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class PostValidatorTest {

    IPostFactory factory = PostFactory.getInstance();
    PostValidator validator = factory.getPostValidator();

    @Test
    void isStarDateBeforeToday() throws ParseException {
        Post post = (Post) factory.getNewPost();
        String startDate = "2022-12-05";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setValidator(validator);
        assertThrows(StartDateBeforeTodayException.class, () -> post.validatePost());
    }

    @Test
    void isStartDateBeforeEndDate() throws ParseException {
        Post post = (Post) factory.getNewPost();
        String startDate = "2030-12-31";
        String endDate = "2030-12-01";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        post.setValidator(validator);
        assertThrows(StartDateAfterEndDateException.class, () -> post.validatePost());
    }

    @Test
    void isMinAgeLessThanMaxAge() throws ParseException {
        Post post = (Post) factory.getNewPost();
        post.setMinAge(5);
        post.setMaxAge(4);
        String startDate = "2030-12-31";
        String endDate = "2031-12-31";
        post.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
        post.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));
        post.setValidator(validator);
        assertThrows(MinAgeGreaterThanMaxAgeException.class, () -> post.validatePost());
    }
}