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

import org.acegisecurity.GrantedAuthority;

/**
 * Authority class, represents a group receved in SAML response
 *
 * @see org.acegisecurity.GrantedAuthority
 */
public class SamlGroupAuthority implements GrantedAuthority {

  private final String group;

  public SamlGroupAuthority(String group) {
    this.group = group;
  }

  public String getAuthority() {
    return this.group;
  }

  @Override
  public String toString() {
    return this.group;
  }
}
