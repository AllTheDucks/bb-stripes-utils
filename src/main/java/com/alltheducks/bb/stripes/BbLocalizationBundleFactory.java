package com.alltheducks.bb.stripes;


import blackboard.persist.Id;
import blackboard.platform.intl.BbResourceBundle;
import blackboard.platform.intl.BundleManager;
import blackboard.platform.intl.BundleManagerFactory;
import blackboard.platform.plugin.PlugInManager;
import blackboard.platform.plugin.PlugInManagerFactory;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.localization.LocalizationBundleFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by wiley on 20/11/14.
 */
public class BbLocalizationBundleFactory implements LocalizationBundleFactory {

    public static final String VENDOR_CONTEXT_PARAM = "blackboard.plugin.vendor";
    public static final String PLUGIN_HANDLE_CONTEXT_PARAM = "blackboard.plugin.handle";

    private Id pluginId;


    @Override
    public ResourceBundle getErrorMessageBundle(Locale locale) throws MissingResourceException {
        BundleManager bm = BundleManagerFactory.getInstance();
        BbResourceBundle bundle = bm.getPluginBundle(pluginId);
        return bundle.getResourceBundle();
    }

    @Override
    public ResourceBundle getFormFieldBundle(Locale locale) throws MissingResourceException {
        BundleManager bm = BundleManagerFactory.getInstance();
        BbResourceBundle bundle = bm.getPluginBundle(pluginId);
        return bundle.getResourceBundle();
    }

    @Override
    public void init(Configuration configuration) throws Exception {
        String vendorId = configuration.getBootstrapPropertyResolver().getProperty(VENDOR_CONTEXT_PARAM);
        String handle = configuration.getBootstrapPropertyResolver().getProperty(PLUGIN_HANDLE_CONTEXT_PARAM);

        PlugInManager pluginMgr = PlugInManagerFactory.getInstance();
        pluginId = pluginMgr.getPlugIn(vendorId, handle).getId();
    }

}
