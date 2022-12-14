package grp16.tripmate.postrequest.persistence;

import grp16.tripmate.postrequest.model.PostRequestStatus;

import java.util.List;
import java.util.Map;

public interface IMyPostRequestPersistence {
    List<Map<String, Object>> getMyPostRequests() throws Exception;
    boolean createJoinRequest(int postId) throws Exception;
    List<Map<String, Object>> getPostOwnerDetails(int postId);
    boolean updateRequest(int requestId, PostRequestStatus postRequestStatus);
    List<Map<String, Object>> getPostRequesterDetails(int requestId);
}