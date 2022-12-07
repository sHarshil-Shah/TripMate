package grp16.tripmate.feedback.controller;

import grp16.tripmate.feedback.model.Feedback;
import grp16.tripmate.feedback.model.IFeedback;
import grp16.tripmate.post.model.IPost;
import grp16.tripmate.post.model.Post;
import grp16.tripmate.session.SessionManager;
import grp16.tripmate.user.model.UserDbColumnNames;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedbackController {

    final private IFeedback feedback;

    public FeedbackController() {
        feedback = new Feedback();
    }

    @GetMapping("/feedback/{id}")
    public String loadFeedbackPage(Model model, @PathVariable("id") int postid) {
        model.addAttribute("post", new Post().getPostByPostId(postid));
        model.addAttribute("currentFeedback", new Feedback());
        model.addAttribute("title", "Feedback");
        return "feedback";
    }


    @PostMapping("/feedback/{id}")
    public String createFeedback(Model model, @PathVariable("id") int postid, @ModelAttribute Feedback feedback) throws Exception {
        feedback.setPost(postid);
        feedback.setUser((Integer) SessionManager.Instance().getValue(UserDbColumnNames.id));
        feedback.createFeedback();
        return "redirect:/dashboard";
    }
}