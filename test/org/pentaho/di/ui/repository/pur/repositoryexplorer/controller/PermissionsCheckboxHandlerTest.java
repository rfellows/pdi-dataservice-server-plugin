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

package org.pentaho.di.ui.repository.pur.repositoryexplorer.controller;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.EnumSet;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.platform.api.repository2.unified.RepositoryFilePermission;
import org.pentaho.ui.xul.components.XulCheckbox;

public class PermissionsCheckboxHandlerTest {
  private XulCheckbox readCheckbox;

  private XulCheckbox writeCheckbox;

  private XulCheckbox deleteCheckbox;

  private XulCheckbox manageCheckbox;

  private PermissionsCheckboxHandler permissionsCheckboxHandler;

  @Before
  public void setup() {
    readCheckbox = mock(XulCheckbox.class);
    writeCheckbox = mock(XulCheckbox.class);
    deleteCheckbox = mock(XulCheckbox.class);
    manageCheckbox = mock(XulCheckbox.class);
    permissionsCheckboxHandler = new PermissionsCheckboxHandler(readCheckbox, writeCheckbox, deleteCheckbox,
        manageCheckbox);
  }

  @Test
  public void testSetAllUncheckedUnchecksAll() {
    boolean checked = false;
    permissionsCheckboxHandler.setAllChecked(checked);
    verify(readCheckbox, times(1)).setChecked(checked);
    verify(writeCheckbox, times(1)).setChecked(checked);
    verify(deleteCheckbox, times(1)).setChecked(checked);
    verify(manageCheckbox, times(1)).setChecked(checked);
  }

  @Test
  public void testSetAllCheckedChecksAll() {
    boolean checked = true;
    permissionsCheckboxHandler.setAllChecked(checked);
    verify(readCheckbox, times(1)).setChecked(checked);
    verify(writeCheckbox, times(1)).setChecked(checked);
    verify(deleteCheckbox, times(1)).setChecked(checked);
    verify(manageCheckbox, times(1)).setChecked(checked);
  }

  @Test
  public void testSetAllDisabledDisablesAll() {
    boolean disabled = true;
    permissionsCheckboxHandler.setAllDisabled(disabled);
    verify(readCheckbox, times(1)).setDisabled(disabled);
    verify(writeCheckbox, times(1)).setDisabled(disabled);
    verify(deleteCheckbox, times(1)).setDisabled(disabled);
    verify(manageCheckbox, times(1)).setDisabled(disabled);
  }

  @Test
  public void testSetAllEnabledEnablesAll() {
    boolean disabled = false;
    permissionsCheckboxHandler.setAllDisabled(disabled);
    verify(readCheckbox, times(1)).setDisabled(disabled);
    verify(writeCheckbox, times(1)).setDisabled(disabled);
    verify(deleteCheckbox, times(1)).setDisabled(disabled);
    verify(manageCheckbox, times(1)).setDisabled(disabled);
  }

  @Test
  public void testProcessCheckboxesNoneCheckedEnableAppropriateTrue() {
    when(readCheckbox.isChecked()).thenReturn(false);
    when(writeCheckbox.isChecked()).thenReturn(false);
    when(deleteCheckbox.isChecked()).thenReturn(false);
    when(manageCheckbox.isChecked()).thenReturn(false);
    assertEquals(EnumSet.noneOf(RepositoryFilePermission.class), permissionsCheckboxHandler.processCheckboxes(true));
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
    verify(readCheckbox, times(1)).setDisabled(false);
  }

  @Test
  public void testProcessCheckboxesNoneCheckedEnableAppropriateFalse() {
    when(readCheckbox.isChecked()).thenReturn(false);
    when(writeCheckbox.isChecked()).thenReturn(false);
    when(deleteCheckbox.isChecked()).thenReturn(false);
    when(manageCheckbox.isChecked()).thenReturn(false);
    assertEquals(EnumSet.noneOf(RepositoryFilePermission.class), permissionsCheckboxHandler.processCheckboxes());
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
    verify(readCheckbox, never()).setDisabled(false);
  }

  @Test
  public void testProcessCheckboxesReadCheckedEnableAppropriateTrue() {
    when(readCheckbox.isChecked()).thenReturn(true);
    when(writeCheckbox.isChecked()).thenReturn(false);
    when(deleteCheckbox.isChecked()).thenReturn(false);
    when(manageCheckbox.isChecked()).thenReturn(false);
    assertEquals(EnumSet.of(RepositoryFilePermission.READ), permissionsCheckboxHandler.processCheckboxes(true));
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(false);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testProcessCheckboxesReadCheckedEnableAppropriateFalse() {
    when(readCheckbox.isChecked()).thenReturn(true);
    when(writeCheckbox.isChecked()).thenReturn(false);
    when(deleteCheckbox.isChecked()).thenReturn(false);
    when(manageCheckbox.isChecked()).thenReturn(false);
    assertEquals(EnumSet.of(RepositoryFilePermission.READ), permissionsCheckboxHandler.processCheckboxes());
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testProcessCheckboxesWriteCheckedEnableAppropriateTrue() {
    when(readCheckbox.isChecked()).thenReturn(false);
    when(writeCheckbox.isChecked()).thenReturn(true);
    when(deleteCheckbox.isChecked()).thenReturn(false);
    when(manageCheckbox.isChecked()).thenReturn(false);
    assertEquals(EnumSet.of(RepositoryFilePermission.READ, RepositoryFilePermission.WRITE),
        permissionsCheckboxHandler.processCheckboxes(true));
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(false);
    verify(deleteCheckbox, times(1)).setDisabled(false);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testProcessCheckboxesWriteCheckedEnableAppropriateFalse() {
    when(readCheckbox.isChecked()).thenReturn(false);
    when(writeCheckbox.isChecked()).thenReturn(true);
    when(deleteCheckbox.isChecked()).thenReturn(false);
    when(manageCheckbox.isChecked()).thenReturn(false);
    assertEquals(EnumSet.of(RepositoryFilePermission.READ, RepositoryFilePermission.WRITE),
        permissionsCheckboxHandler.processCheckboxes());
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testProcessCheckboxesDeleteCheckedEnableAppropriateTrue() {
    when(readCheckbox.isChecked()).thenReturn(false);
    when(writeCheckbox.isChecked()).thenReturn(false);
    when(deleteCheckbox.isChecked()).thenReturn(true);
    when(manageCheckbox.isChecked()).thenReturn(false);
    assertEquals(
        EnumSet.of(RepositoryFilePermission.READ, RepositoryFilePermission.WRITE, RepositoryFilePermission.DELETE),
        permissionsCheckboxHandler.processCheckboxes(true));
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(false);
    verify(manageCheckbox, times(1)).setDisabled(false);
  }

  @Test
  public void testProcessCheckboxesDeleteCheckedEnableAppropriateFalse() {
    when(readCheckbox.isChecked()).thenReturn(false);
    when(writeCheckbox.isChecked()).thenReturn(false);
    when(deleteCheckbox.isChecked()).thenReturn(true);
    when(manageCheckbox.isChecked()).thenReturn(false);
    assertEquals(
        EnumSet.of(RepositoryFilePermission.READ, RepositoryFilePermission.WRITE, RepositoryFilePermission.DELETE),
        permissionsCheckboxHandler.processCheckboxes());
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testProcessCheckboxesManageCheckedEnableAppropriateTrue() {
    when(readCheckbox.isChecked()).thenReturn(false);
    when(writeCheckbox.isChecked()).thenReturn(false);
    when(deleteCheckbox.isChecked()).thenReturn(false);
    when(manageCheckbox.isChecked()).thenReturn(true);
    assertEquals(EnumSet.of(RepositoryFilePermission.READ, RepositoryFilePermission.WRITE,
        RepositoryFilePermission.DELETE, RepositoryFilePermission.ACL_MANAGEMENT),
        permissionsCheckboxHandler.processCheckboxes(true));
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(false);
  }

  @Test
  public void testProcessCheckboxesManageCheckedEnableAppropriateFalse() {
    when(readCheckbox.isChecked()).thenReturn(false);
    when(writeCheckbox.isChecked()).thenReturn(false);
    when(deleteCheckbox.isChecked()).thenReturn(false);
    when(manageCheckbox.isChecked()).thenReturn(true);
    assertEquals(EnumSet.of(RepositoryFilePermission.READ, RepositoryFilePermission.WRITE,
        RepositoryFilePermission.DELETE, RepositoryFilePermission.ACL_MANAGEMENT),
        permissionsCheckboxHandler.processCheckboxes());
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testUpdateCheckboxesNoPermissionsAppropriateTrue() {
    permissionsCheckboxHandler.updateCheckboxes(true, EnumSet.noneOf(RepositoryFilePermission.class));
    verify(readCheckbox, times(1)).setChecked(false);
    verify(writeCheckbox, times(1)).setChecked(false);
    verify(deleteCheckbox, times(1)).setChecked(false);
    verify(manageCheckbox, times(1)).setChecked(false);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
    verify(readCheckbox, times(1)).setDisabled(false);
  }

  @Test
  public void testUpdateCheckboxesNoPermissionsAppropriateFalse() {
    permissionsCheckboxHandler.updateCheckboxes(false, EnumSet.noneOf(RepositoryFilePermission.class));
    verify(readCheckbox, times(1)).setChecked(false);
    verify(writeCheckbox, times(1)).setChecked(false);
    verify(deleteCheckbox, times(1)).setChecked(false);
    verify(manageCheckbox, times(1)).setChecked(false);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
    verify(readCheckbox, never()).setDisabled(false);
  }

  @Test
  public void testUpdateCheckboxesReadPermissionsAppropriateTrue() {
    permissionsCheckboxHandler.updateCheckboxes(true, EnumSet.of(RepositoryFilePermission.READ));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(false);
    verify(deleteCheckbox, times(1)).setChecked(false);
    verify(manageCheckbox, times(1)).setChecked(false);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(false);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testUpdateCheckboxesReadPermissionsAppropriateFalse() {
    permissionsCheckboxHandler.updateCheckboxes(false, EnumSet.of(RepositoryFilePermission.READ));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(false);
    verify(deleteCheckbox, times(1)).setChecked(false);
    verify(manageCheckbox, times(1)).setChecked(false);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testUpdateCheckboxesWritePermissionsAppropriateTrue() {
    permissionsCheckboxHandler.updateCheckboxes(true,
        EnumSet.of(RepositoryFilePermission.WRITE, RepositoryFilePermission.READ));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(true);
    verify(deleteCheckbox, times(1)).setChecked(false);
    verify(manageCheckbox, times(1)).setChecked(false);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(false);
    verify(deleteCheckbox, times(1)).setDisabled(false);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testUpdateCheckboxesWritePermissionsAppropriateFalse() {
    permissionsCheckboxHandler.updateCheckboxes(false,
        EnumSet.of(RepositoryFilePermission.WRITE, RepositoryFilePermission.READ));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(true);
    verify(deleteCheckbox, times(1)).setChecked(false);
    verify(manageCheckbox, times(1)).setChecked(false);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testUpdateCheckboxesDeletePermissionsAppropriateTrue() {
    permissionsCheckboxHandler.updateCheckboxes(true,
        EnumSet.of(RepositoryFilePermission.DELETE, RepositoryFilePermission.WRITE, RepositoryFilePermission.READ));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(true);
    verify(deleteCheckbox, times(1)).setChecked(true);
    verify(manageCheckbox, times(1)).setChecked(false);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(false);
    verify(manageCheckbox, times(1)).setDisabled(false);
  }

  @Test
  public void testUpdateCheckboxesDeletePermissionsAppropriateFalse() {
    permissionsCheckboxHandler.updateCheckboxes(false,
        EnumSet.of(RepositoryFilePermission.DELETE, RepositoryFilePermission.WRITE, RepositoryFilePermission.READ));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(true);
    verify(deleteCheckbox, times(1)).setChecked(true);
    verify(manageCheckbox, times(1)).setChecked(false);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testUpdateCheckboxesManagePermissionsAppropriateTrue() {
    permissionsCheckboxHandler.updateCheckboxes(true, EnumSet.of(RepositoryFilePermission.ACL_MANAGEMENT,
        RepositoryFilePermission.DELETE, RepositoryFilePermission.WRITE, RepositoryFilePermission.READ));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(true);
    verify(deleteCheckbox, times(1)).setChecked(true);
    verify(manageCheckbox, times(1)).setChecked(true);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(false);
  }

  @Test
  public void testUpdateCheckboxesManagePermissionsAppropriateFalse() {
    permissionsCheckboxHandler.updateCheckboxes(false, EnumSet.of(RepositoryFilePermission.ACL_MANAGEMENT,
        RepositoryFilePermission.DELETE, RepositoryFilePermission.WRITE, RepositoryFilePermission.READ));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(true);
    verify(deleteCheckbox, times(1)).setChecked(true);
    verify(manageCheckbox, times(1)).setChecked(true);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }

  @Test
  public void testUpdateCheckboxesAllPermissionsAppropriateTrue() {
    permissionsCheckboxHandler.updateCheckboxes(true, EnumSet.of(RepositoryFilePermission.ALL));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(true);
    verify(deleteCheckbox, times(1)).setChecked(true);
    verify(manageCheckbox, times(1)).setChecked(true);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(false);
  }

  @Test
  public void testUpdateCheckboxesAllPermissionsAppropriateFalse() {
    permissionsCheckboxHandler.updateCheckboxes(false, EnumSet.of(RepositoryFilePermission.ALL));
    verify(readCheckbox, times(1)).setChecked(true);
    verify(writeCheckbox, times(1)).setChecked(true);
    verify(deleteCheckbox, times(1)).setChecked(true);
    verify(manageCheckbox, times(1)).setChecked(true);
    verify(readCheckbox, times(1)).setDisabled(true);
    verify(writeCheckbox, times(1)).setDisabled(true);
    verify(deleteCheckbox, times(1)).setDisabled(true);
    verify(manageCheckbox, times(1)).setDisabled(true);
  }
}
