/* Licensed to Jenkins CI under one or more contributor license
agreements.  See the NOTICE file distributed with this work
for additional information regarding copyright ownership.
Jenkins CI licenses this file to you under the Apache License,
Version 2.0 (the "License"); you may not use this file except
in compliance with the License.  You may obtain a copy of the
License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License. */

package org.jenkinsci.plugins.saml;

import hudson.Extension;
import hudson.Util;

import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import static org.jenkinsci.plugins.saml.SamlSecurityRealm.ERROR_NOT_VALID_NUMBER;
import static org.jenkinsci.plugins.saml.SamlSecurityRealm.ERROR_ONLY_SPACES_FIELD_VALUE;

/**
 * Simple immutable data class to hold the optional advanced configuration data section
 * of the plugin's configuration page
 */
public class SamlAdvancedConfiguration extends AbstractDescribableImpl<SamlAdvancedConfiguration> {
    private final Boolean forceAuthn;
    private final String authnContextClassRef;
    private final String spEntityId;
    private final Integer maximumSessionLifetime;
    private final String groupRegexPattern;
    private final String groupRegexReplace;

    @DataBoundConstructor
    public SamlAdvancedConfiguration(Boolean forceAuthn,
                                     String authnContextClassRef,
                                     String spEntityId,
                                     Integer maximumSessionLifetime,
                                     String groupRegexPattern,
                                     String groupRegexReplace) {
        this.forceAuthn = (forceAuthn != null) ? forceAuthn : false;
        this.authnContextClassRef = Util.fixEmptyAndTrim(authnContextClassRef);
        this.spEntityId = Util.fixEmptyAndTrim(spEntityId);
        this.maximumSessionLifetime = maximumSessionLifetime;
        this.groupRegexPattern = groupRegexPattern;
        this.groupRegexReplace = groupRegexReplace;
    }

    public SamlAdvancedConfiguration(Boolean forceAuthn,
                                     String authnContextClassRef,
                                     String spEntityId,
                                     Integer maximumSessionLifetime) {
        this(forceAuthn, authnContextClassRef, spEntityId, maximumSessionLifetime, null, null);
    }
    public Boolean getForceAuthn() {
        return forceAuthn;
    }

    public String getAuthnContextClassRef() {
        return authnContextClassRef;
    }

    public String getSpEntityId() {
        return spEntityId;
    }

    public Integer getMaximumSessionLifetime() {
        return maximumSessionLifetime;
    }
    public String getGroupRegexPattern() { return groupRegexPattern; }
    public String getGroupRegexReplace() { return groupRegexReplace; }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SamlAdvancedConfiguration{");
        sb.append("forceAuthn=").append(getForceAuthn());
        sb.append(", authnContextClassRef='").append(StringUtils.defaultIfBlank(getAuthnContextClassRef(), "none")).append('\'');
        sb.append(", spEntityId='").append(StringUtils.defaultIfBlank(getSpEntityId(), "none")).append('\'');
        sb.append(", maximumSessionLifetime=").append(getMaximumSessionLifetime() != null ? getMaximumSessionLifetime() : "none");
        sb.append(", groupRegexPattern=").append(groupRegexPattern != null ? groupRegexPattern : "none");
        sb.append(", groupRegexReplace=").append(groupRegexReplace != null ? groupRegexPattern : "none");
        sb.append('}');
        return sb.toString();
    }

    @Extension
    public static final class DescriptorImpl extends Descriptor<SamlAdvancedConfiguration> {
        public DescriptorImpl() {
            super();
        }

        public DescriptorImpl(Class<? extends SamlAdvancedConfiguration> clazz) {
            super(clazz);
        }

        @Override
        public String getDisplayName() {
            return "Advanced Configuration";
        }

        public hudson.util.FormValidation doCheckAuthnContextClassRef(@org.kohsuke.stapler.QueryParameter String authnContextClassRef) {
            if (StringUtils.isEmpty(authnContextClassRef)) {
                return hudson.util.FormValidation.ok();
            }

            if (StringUtils.isBlank(authnContextClassRef)) {
                return hudson.util.FormValidation.error(ERROR_ONLY_SPACES_FIELD_VALUE);
            }

            return hudson.util.FormValidation.ok();
        }


        public hudson.util.FormValidation doCheckSpEntityId(@org.kohsuke.stapler.QueryParameter String spEntityId) {
            if (StringUtils.isEmpty(spEntityId)) {
                return hudson.util.FormValidation.ok();
            }

            if (StringUtils.isBlank(spEntityId)) {
                return hudson.util.FormValidation.error(ERROR_ONLY_SPACES_FIELD_VALUE);
            }

            return hudson.util.FormValidation.ok();
        }

        public hudson.util.FormValidation doCheckMaximumSessionLifetime(@org.kohsuke.stapler.QueryParameter String maximumSessionLifetime) {
            if (StringUtils.isEmpty(maximumSessionLifetime)) {
                return hudson.util.FormValidation.ok();
            }

            long i = 0;
            try {
                i = Long.parseLong(maximumSessionLifetime);
            } catch (NumberFormatException e) {
                return hudson.util.FormValidation.error(ERROR_NOT_VALID_NUMBER, e);
            }

            if (i < 0) {
                return hudson.util.FormValidation.error(ERROR_NOT_VALID_NUMBER);
            }

            if (i > Integer.MAX_VALUE) {
                return hudson.util.FormValidation.error(ERROR_NOT_VALID_NUMBER);
            }

            return hudson.util.FormValidation.ok();
        }

    }
}
