import hudson.Extension;
import hudson.Plugin;
import hudson.PluginManager;
import hudson.PluginWrapper;
import jenkins.model.Jenkins;
@Extension
public class MyPlugin extends Plugin {
    public String getResURL() {
        Jenkins jenkins = Jenkins.getInstance();

        PluginManager pluginManager = jenkins.getPluginManager();

        PluginWrapper pluginWrapper = pluginManager.getPlugin("your-plugin-id");

        return pluginWrapper.baseResourceURL + "/js/";
    }
}

