/*
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://probe.jstripe.com/d/license.shtml
 *
 *  THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 *  WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.jstripe.tomcat.probe.controllers.wrapper;

import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.ModelAndView;
import org.tanukisoftware.wrapper.WrapperManager;
import org.jstripe.tomcat.probe.model.wrapper.WrapperInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WrapperInfoController extends ParameterizableViewController {
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WrapperInfo wi = new WrapperInfo();

        try {
            Class.forName("org.tanukisoftware.wrapper.WrapperManager");
            wi.setVersion(WrapperManager.getVersion());
            wi.setBuildTime(WrapperManager.getBuildTime());
            wi.setUser(WrapperManager.getUser(false) != null ? WrapperManager.getUser(false).getUser() : null);
            wi.setInteractiveUser(WrapperManager.getInteractiveUser(false) != null ? WrapperManager.getInteractiveUser(false).getUser() : null);
            wi.setJvmPid(WrapperManager.getJavaPID());
            wi.setWrapperPid(WrapperManager.getWrapperPID());
            wi.setProperties(WrapperManager.getProperties().entrySet());
            wi.setControlledByWrapper(WrapperManager.isControlledByNativeWrapper());
            wi.setDebugEnabled(WrapperManager.isDebugEnabled());
            wi.setLaunchedAsService(WrapperManager.isLaunchedAsService());
        } catch (ClassNotFoundException e) {
            logger.info("Could not find WrapperManager class. Is wrapper.jar in the classpath?");
            wi.setControlledByWrapper(false);
        }
        return new ModelAndView(getViewName(), "wrapperInfo", wi);
    }
}
