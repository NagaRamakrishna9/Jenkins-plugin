package io.jenkins.plugins.sample;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;

import hudson.util.ListBoxModel;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String name;
    private boolean useFrench;


    public List<String> getBrowserOptions() {
        return Arrays.asList("Chrome", "Edge", "Firefox");
    }

    @DataBoundConstructor
    public HelloWorldBuilder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isUseFrench() {
        return useFrench;
    }

    @DataBoundSetter
    public void setUseFrench(boolean useFrench) {
        this.useFrench = useFrench;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, EnvVars env, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {
        run.addAction(new HelloWorldAction(name));

        if (useFrench) {
            listener.getLogger().println("Bonjour, " + name + "!");
        } else {
            listener.getLogger().println("Hello, " + name + "!");
        }
    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

//        @Autowired
        private RestTemplate restTemplate = new RestTemplate();

        public FormValidation doCheckName(@QueryParameter String value, @QueryParameter boolean useFrench)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());
            if (value.length() < 4)
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_tooShort());
            if (!useFrench && value.matches(".*[éáàç].*")) {
                return FormValidation.warning(Messages.HelloWorldBuilder_DescriptorImpl_warnings_reallyFrench());
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.HelloWorldBuilder_DescriptorImpl_DisplayName();
        }


        public ListBoxModel doFillBrowserItems() {
            fetchDataFromExternalService();
            ArrayList<String> data = new ArrayList<>();
            data.add("Chrome");
            data.add("Edge");
            data.add("Firefox");

            ListBoxModel items = new ListBoxModel();
            data.forEach(d -> {
                items.add(d, d);
            });

            return items;
        }

        public void fetchDataFromExternalService() {
            String url ="nuvve tana ";
            String responseData = restTemplate.getForObject(url, String.class);
            System.out.println("data ::::" + responseData);
            // Process the response data here
        }

//        public ListBoxModel doFillDataItems() {
//        ArrayList<String> data = new ArrayList<>();
//            data.add("raj");
//            data.add("ram");
//            data.add("bheem");
//
//            ListBoxModel items = new ListB voxModel();
//        data.forEach(d -> {
//            items.add(d, d);
//        });
//
//            return items;
//        }
    }

}
