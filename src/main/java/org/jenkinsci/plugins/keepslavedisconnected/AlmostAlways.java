package org.jenkinsci.plugins.keepslavedisconnected;

import hudson.Extension;
import hudson.model.Descriptor;

import hudson.slaves.RetentionStrategy;
import hudson.slaves.SlaveComputer;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * {@link RetentionStrategy} that tries to keep the node online all the time unless marked offline.
 */
public class AlmostAlways extends RetentionStrategy<SlaveComputer> {
    /**
     * Constructs a new AlmostAlways.
     */
    @DataBoundConstructor
    public AlmostAlways() {
    }

    public long check(SlaveComputer c) {
        if (c.isOffline() && !c.isConnecting() && c.isLaunchSupported() && !c.isTemporarilyOffline())
            c.tryReconnect();
        return 1;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<RetentionStrategy<?>> {
        public String getDisplayName() {
            return Messages.RetentionStrategy_AlmostAlways_displayName();
        }
    }
}
