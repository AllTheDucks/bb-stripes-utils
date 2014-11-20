package com.alltheducks.bb.stripes;


import blackboard.persist.Id;
import blackboard.platform.intl.BbResourceBundle;
import blackboard.platform.intl.BundleManager;
import blackboard.platform.intl.BundleManagerFactory;
import blackboard.platform.plugin.PlugIn;
import blackboard.platform.plugin.PlugInManager;
import blackboard.platform.plugin.PlugInManagerFactory;
import java.lang.Exception;import java.lang.Override;import java.lang.String;import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.localization.LocalizationBundleFactory;

/**
 * Created by wiley on 20/11/14.
 */
public class BbLocalizationBundleFactory implements LocalizationBundleFactory {

    private PlugIn plugin;
    private String vendorId;
    private String handle;


    @Override
    public ResourceBundle getErrorMessageBundle(Locale locale) throws MissingResourceException {
        BundleManager bm = BundleManagerFactory.getInstance();
        BbResourceBundle bundle = bm.getPluginBundle(getPlugin(vendorId, handle).getId());
        return bundle.getResourceBundle();
    }

    @Override
    public ResourceBundle getFormFieldBundle(Locale locale) throws MissingResourceException {
        BundleManager bm = BundleManagerFactory.getInstance();
        Id pluginId = getPlugin(vendorId, handle).getId();
        BbResourceBundle bundle = bm.getPluginBundle(pluginId);
        return bundle.getResourceBundle();
    }

    @Override
    public void init(Configuration configuration) throws Exception {
        vendorId = configuration.getBootstrapPropertyResolver().getProperty("blackboard.plugin.vendor");
        handle = configuration.getBootstrapPropertyResolver().getProperty("blackboard.plugin.handle");
    }

    private PlugIn getPlugin(String vendorId, String handle) {
        if (plugin == null) {
            PlugInManager pluginMgr = PlugInManagerFactory.getInstance();
            plugin = pluginMgr.getPlugIn(vendorId, handle);
        }
        return plugin;
    }

}
