package org.chromium.chromium_test.common;

import com.intellij.execution.ExecutionTarget;
import javax.swing.Icon;

public final class EmptyExecutionTarget extends ExecutionTarget {

  private static final EmptyExecutionTarget sInstance = new EmptyExecutionTarget();

  public static EmptyExecutionTarget get() {
    return sInstance;
  }

  @Override
  public String getId() {
    return "";
  }

  @Override
  public String getDisplayName() {
    return "";
  }

  @Override
  public Icon getIcon() {
    return null;
  }

  private EmptyExecutionTarget() {
  }
}
