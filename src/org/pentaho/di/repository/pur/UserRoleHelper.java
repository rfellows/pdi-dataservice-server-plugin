/*!
 * PENTAHO CORPORATION PROPRIETARY AND CONFIDENTIAL
 *
 * Copyright 2002 - 2014 Pentaho Corporation (Pentaho). All rights reserved.
 *
 * NOTICE: All information including source code contained herein is, and
 * remains the sole property of Pentaho and its licensors. The intellectual
 * and technical concepts contained herein are proprietary and confidential
 * to, and are trade secrets of Pentaho and may be covered by U.S. and foreign
 * patents, or patents in process, and are protected by trade secret and
 * copyright laws. The receipt or possession of this source code and/or related
 * information does not convey or imply any rights to reproduce, disclose or
 * distribute its contents, or to manufacture, use, or sell anything that it
 * may describe, in whole or in part. Any reproduction, modification, distribution,
 * or public display of this information without the express written authorization
 * from Pentaho is strictly prohibited and in violation of applicable laws and
 * international treaties. Access to the source code contained herein is strictly
 * prohibited to anyone except those individuals and entities who have executed
 * confidentiality and non-disclosure agreements or other agreements with Pentaho,
 * explicitly covering such access.
 */

package org.pentaho.di.repository.pur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.pur.model.IEEUser;
import org.pentaho.di.repository.pur.model.IRole;
import org.pentaho.di.ui.repository.pur.services.IRoleSupportSecurityManager;
import org.pentaho.platform.api.engine.security.userroledao.UserRoleInfo;
import org.pentaho.platform.security.userroledao.ws.IUserRoleWebService;
import org.pentaho.platform.security.userroledao.ws.ProxyPentahoRole;
import org.pentaho.platform.security.userroledao.ws.ProxyPentahoUser;
import org.pentaho.platform.security.userroledao.ws.UserRoleException;
import org.pentaho.platform.security.userroledao.ws.UserRoleSecurityInfo;
import org.pentaho.platform.security.userroledao.ws.UserToRoleAssignment;

public class UserRoleHelper implements java.io.Serializable {

  private static final long serialVersionUID = -8850597631894280354L; /* EESOURCE: UPDATE SERIALVERUID */

  public static List<IUser> convertFromProxyPentahoUsers(UserRoleSecurityInfo info, IRoleSupportSecurityManager rsm) {
    List<IUser> userList = new ArrayList<IUser>();
    List<ProxyPentahoUser> users = info.getUsers();
    List<UserToRoleAssignment>  assignments = info.getAssignments();
    for(ProxyPentahoUser user:users) {
      userList.add(convertFromProxyPentahoUser(user, assignments, rsm));
    }
    return userList;
  }

  public static List<IUser> convertFromNonPentahoUsers(UserRoleInfo info, IRoleSupportSecurityManager rsm) {
    List<IUser> userList = new ArrayList<IUser>();
    List<String> users = info.getUsers();
    for(String user:users) {
      userList.add(convertFromNonPentahoUser(user, rsm));
    }
    return userList;
  }

  public static List<IRole> convertToListFromProxyPentahoRoles(UserRoleSecurityInfo info, IRoleSupportSecurityManager rsm) {
    List<IRole> roleList = new ArrayList<IRole>();
    List<ProxyPentahoRole> roles = info.getRoles();
    List<UserToRoleAssignment>  assignments = info.getAssignments();
    for (ProxyPentahoRole role : roles) {
      roleList.add(convertFromProxyPentahoRole(role, assignments, rsm));
    }
    return roleList;
  }

  public static List<IRole> convertToListFromNonPentahoRoles(UserRoleInfo info, IRoleSupportSecurityManager rsm) {
    List<IRole> roleList = new ArrayList<IRole>();
    List<String> roles = info.getRoles();
    for (String role : roles) {
      roleList.add(convertFromNonPentahoRole(role, rsm));
    }
    return roleList;
  }
  
  public static List<IRole> convertToListFromProxyPentahoDefaultRoles(UserRoleSecurityInfo info, IRoleSupportSecurityManager rsm) {
    List<IRole> roleList = new ArrayList<IRole>();
    List<ProxyPentahoRole> roles = info.getDefaultRoles();
    List<UserToRoleAssignment>  assignments = info.getAssignments();
    for (ProxyPentahoRole role : roles) {
      roleList.add(convertFromProxyPentahoRole(role, assignments, rsm));
    }
    return roleList;
  }

  public static ProxyPentahoRole getProxyPentahoRole(IUserRoleWebService userRoleWebService, String name)
      throws UserRoleException {
    ProxyPentahoRole roleToFind = null;
    ProxyPentahoRole[] roles = userRoleWebService.getRoles();
    if (roles != null && roles.length > 0) {
      for (ProxyPentahoRole role : roles) {
        if (role.getName().equals(name)) {
          roleToFind = role;
          break;
        }
      }
    }
    return roleToFind;
  }

  public static List<IUser> convertToListFromProxyPentahoUsers(ProxyPentahoUser[] users,
      IUserRoleWebService userRoleWebService, UserRoleLookupCache lookupCache, IRoleSupportSecurityManager rsm) {
    List<IUser> userList = new ArrayList<IUser>();
    for (ProxyPentahoUser user : users) {
      userList.add(convertFromProxyPentahoUser(userRoleWebService, user, lookupCache, rsm));
    }
    return userList;
  }

  public static List<IRole> convertToListFromProxyPentahoRoles(ProxyPentahoRole[] roles,
      IUserRoleWebService userRoleWebService, UserRoleLookupCache lookupCache, IRoleSupportSecurityManager rsm) {
    List<IRole> roleList = new ArrayList<IRole>();
    for (ProxyPentahoRole role : roles) {
      roleList.add(convertFromProxyPentahoRole(userRoleWebService, role, lookupCache, rsm));
    }
    return roleList;
  }

  public static ProxyPentahoUser[] convertToPentahoProxyUsers(Set<IUser> users) {
    ProxyPentahoUser[] proxyUsers = new ProxyPentahoUser[users.size()];
    int i = 0;
    for (IUser user : users) {
      proxyUsers[i++] = convertToPentahoProxyUser(user);
    }
    return proxyUsers;
  }

  public static ProxyPentahoUser[] convertToPentahoProxyUsers(List<IUser> users) {
    ProxyPentahoUser[] proxyUsers = new ProxyPentahoUser[users.size()];
    int i = 0;
    for (IUser user : users) {
      proxyUsers[i++] = convertToPentahoProxyUser(user);
    }
    return proxyUsers;
  }

  public static ProxyPentahoUser convertToPentahoProxyUser(IUser userInfo) {
    ProxyPentahoUser user = new ProxyPentahoUser();
    user.setName(userInfo.getLogin());
    // Since we send the empty password to the client, if the client has not modified the password then we do change it
    if(!StringUtils.isEmpty(userInfo.getPassword())) {
      user.setPassword(userInfo.getPassword());      
    }
    user.setDescription(userInfo.getDescription());
    return user;
  }

  public static ProxyPentahoRole[] convertToPentahoProxyRoles(Set<IRole> roles) {
    ProxyPentahoRole[] proxyRoles = new ProxyPentahoRole[roles.size()];
    int i = 0;
    for (IRole role : roles) {
      proxyRoles[i++] = convertToPentahoProxyRole(role);
    }
    return proxyRoles;
  }

  public static ProxyPentahoRole[] convertToPentahoProxyRoles(List<IRole> roles) {
    ProxyPentahoRole[] proxyRoles = new ProxyPentahoRole[roles.size()];
    int i = 0;
    for (IRole role : roles) {
      proxyRoles[i++] = convertToPentahoProxyRole(role);
    }
    return proxyRoles;
  }

  public static ProxyPentahoRole convertToPentahoProxyRole(IRole roleInfo) {
    ProxyPentahoRole role = new ProxyPentahoRole();
    role.setName(roleInfo.getName());
    role.setDescription(roleInfo.getDescription());
    return role;
  }

  private static Set<IRole> convertToSetFromProxyPentahoRoles(ProxyPentahoRole[] roles,
      UserRoleLookupCache lookupCache) {
    Set<IRole> roleSet = new HashSet<IRole>();
    for (ProxyPentahoRole role : roles) {
      roleSet.add(lookupCache.lookupRole(role));
    }
    return roleSet;
  }

  public static IRole convertFromProxyPentahoRole(IUserRoleWebService userRoleWebService, ProxyPentahoRole role,
      UserRoleLookupCache lookupCache, IRoleSupportSecurityManager rsm) {
    IRole roleInfo = null;
    try {
      roleInfo = rsm.constructRole();
    } catch (KettleException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    roleInfo.setDescription(role.getDescription());
    roleInfo.setName(role.getName());
    try {
      roleInfo.setUsers(convertToSetFromProxyPentahoUsers(userRoleWebService.getUsersForRole(role), lookupCache));
    } catch (UserRoleException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return roleInfo;
  }

  public static IUser convertFromProxyPentahoUser(IUserRoleWebService userRoleWebService, ProxyPentahoUser user,
      UserRoleLookupCache lookupCache, IRoleSupportSecurityManager rsm) {
    IUser userInfo = null;
    try {
      userInfo = rsm.constructUser();
      userInfo.setDescription(user.getDescription());
      userInfo.setPassword(user.getPassword());
      userInfo.setLogin(user.getName());
      userInfo.setName(user.getName());
      try {
        if(userInfo instanceof IEEUser)
        ((IEEUser)userInfo).setRoles(convertToSetFromProxyPentahoRoles(userRoleWebService.getRolesForUser(user), lookupCache));
      } catch (UserRoleException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }      
    } catch (KettleException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return userInfo;
  }

  private static Set<IUser> convertToSetFromProxyPentahoUsers(ProxyPentahoUser[] users,
      UserRoleLookupCache lookupCache) {
    Set<IUser> userSet = new HashSet<IUser>();
    for (ProxyPentahoUser user : users) {
      userSet.add(lookupCache.lookupUser(user));
    }
    return userSet;
  }
  private static Set<IRole> convertToSetFromProxyPentahoRoles(ProxyPentahoRole[] roles, IRoleSupportSecurityManager rsm) {
    Set<IRole> roleSet = new HashSet<IRole>();
    for (ProxyPentahoRole role : roles) {
      IRole roleInfo = null;
      try {
        roleInfo = rsm.constructRole();
      } catch (KettleException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      roleInfo.setDescription(role.getDescription());
      roleInfo.setName(role.getName());
      roleSet.add(roleInfo);
    }
    return roleSet;
  }
  public static IUser convertToUserInfo(ProxyPentahoUser user, ProxyPentahoRole[] roles, IRoleSupportSecurityManager rsm) {
    IUser userInfo = null;
    try {
      userInfo = rsm.constructUser();
      userInfo.setDescription(user.getDescription());
      userInfo.setPassword(user.getPassword());
      userInfo.setLogin(user.getName());
      userInfo.setName(user.getName());
      if(userInfo instanceof IEEUser){
        ((IEEUser)userInfo).setRoles(convertToSetFromProxyPentahoRoles(roles, rsm));  
      }
    } catch(KettleException ke) {
      ke.printStackTrace();
    }
    return userInfo;
  }

  public static IRole convertFromProxyPentahoRole(ProxyPentahoRole role, List<UserToRoleAssignment> assignments, IRoleSupportSecurityManager rsm) {
    IRole roleInfo = null;
    try {
      roleInfo = rsm.constructRole();
      roleInfo.setDescription(role.getDescription());
      roleInfo.setName(role.getName());
      roleInfo.setUsers(getUsersForRole(role.getName(), assignments, rsm));      
    } catch (KettleException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return roleInfo;
  }
  
  public static IRole convertFromNonPentahoRole(String role, IRoleSupportSecurityManager rsm) {
    IRole roleInfo = null;
    try {
      roleInfo = rsm.constructRole();
      roleInfo.setName(role);
    } catch (KettleException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return roleInfo;
  }
  
  public static IUser convertFromProxyPentahoUser(ProxyPentahoUser user, List<UserToRoleAssignment> assignments, IRoleSupportSecurityManager rsm) {
    IUser userInfo = null;
    try {
      userInfo = rsm.constructUser();
      userInfo.setDescription(user.getDescription());
      userInfo.setPassword(user.getPassword());
      userInfo.setLogin(user.getName());
      userInfo.setName(user.getName());
      if(userInfo instanceof IEEUser) {
        ((IEEUser)userInfo).setRoles(getRolesForUser(user.getName(), assignments, rsm));
      }
    } catch (KettleException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
    return userInfo;
  }

  public static IUser convertFromNonPentahoUser(String user, IRoleSupportSecurityManager rsm) {
    IUser userInfo = null;
    try {
      userInfo = rsm.constructUser();
      userInfo.setLogin(user);
      userInfo.setName(user);
    } catch (KettleException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
    return userInfo;
  }
  public static Set<IUser> getUsersForRole(String name, List<UserToRoleAssignment> assignments, IRoleSupportSecurityManager rsm) {
    Set<IUser> users = new HashSet<IUser>();
    for(UserToRoleAssignment assignment:assignments) {
      if(name.equals(assignment.getRoleId())) {
        IUser user= null;
        try {
          user = rsm.constructUser();
        } catch (KettleException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        user.setLogin(assignment.getUserId());
        users.add(user);
      }
    }
    return users;
  }  
  
  public static Set<IRole> getRolesForUser(String name, List<UserToRoleAssignment> assignments, IRoleSupportSecurityManager rsm) {
    Set<IRole> roles = new HashSet<IRole>();
    for(UserToRoleAssignment assignment:assignments) {
      if(name.equals(assignment.getUserId())) {
        IRole role = null;
        try {
          role = rsm.constructRole();
        } catch (KettleException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        role.setName(assignment.getRoleId());
        roles.add(role);
      }
    }
    return roles;
  }
}
