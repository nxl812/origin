package com.nxl.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.util.StringUtils;

public class NxlDefaultWebSessionManager extends DefaultWebSessionManager {
    private String sessionIdName = null;

    public NxlDefaultWebSessionManager(String sessionIdName) {
        super();
        this.sessionIdName = sessionIdName;
        if (!StringUtils.isEmpty(sessionIdName)) {
            super.getSessionIdCookie().setName(sessionIdName);
        }
    }

    @Override
    protected void onStart(Session session, SessionContext context) {
        if (!StringUtils.isEmpty(sessionIdName)) {
            super.getSessionIdCookie().setName(sessionIdName);
        }

        super.onStart(session, context);
    }
}
