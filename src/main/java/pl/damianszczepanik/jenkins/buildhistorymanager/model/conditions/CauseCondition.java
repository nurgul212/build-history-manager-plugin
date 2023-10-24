package pl.damianszczepanik.jenkins.buildhistorymanager.model.conditions;

import hudson.model.Cause;
import hudson.model.Run;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import pl.damianszczepanik.jenkins.buildhistorymanager.model.RuleConfiguration;

/**
 * Matches builds for which class name of {@link Cause} matches given class.
 *
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class CauseCondition extends Condition {

    private String causeClass;

    @DataBoundConstructor
    public CauseCondition() {
        // Jenkins stapler requires to have public constructor with @DataBoundConstructor
    }

    public String getCauseClass() {
        return causeClass;
    }

    @DataBoundSetter
    public void setCauseClass(String causeClass) {
        this.causeClass = causeClass;
    }

    @Override
    public boolean matches(Run<?, ?> run, RuleConfiguration configuration, int buildPosition) {
        for (Cause cause : run.getCauses()) {
            // use contains() method to avoid problem with class name
            // for causes which are often inner class and name contains $ character
            if (cause.toString().contains(causeClass)) {
                return true;
            }
        }
        return false;
    }

    // Add an overloaded version of the 'matches' method to set the default value for buildPosition
    public boolean matches(Run<?, ?> run, RuleConfiguration configuration) {
        // Set a default value for buildPosition, e.g., -1
        int buildPosition = -1;
        return matches(run, configuration, buildPosition);
    }
}
