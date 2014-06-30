package org.osiam.addons.administration.config;

import java.util.Collection;

import javax.inject.Inject;

import org.osiam.addons.administration.model.session.GeneralSessionData;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for decide admin-access.
 */
@Component
public class AdminAccessDecisionManager implements AccessDecisionManager {

    @Inject
    private GeneralSessionData session;

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) {
        // if there are no access-token in session
        if (session.getAccesstoken() == null) {
            throw new AccessDeniedException("There is no accesstoken in session!");
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
