package grp16.tripmate.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static SessionManager sessionManager;

    private static Map<String, Object> session;

    private SessionManager() {
        session = new HashMap<>();
    }

    public static SessionManager Instance() {
        if (sessionManager == null) {
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }

    public void setValue(String key, Object value) {
        session.put(key, value);
    }

    public Object getValue(String key) throws Exception {
        Object value = session.get(key);
        if (value == null)
            throw new SessionManagerException("null value");
        return session.get(key);
    }


}