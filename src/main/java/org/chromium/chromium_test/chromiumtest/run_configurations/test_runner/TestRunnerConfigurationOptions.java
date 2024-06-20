package org.chromium.chromium_test.chromiumtest.run_configurations.test_runner;

import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

public class TestRunnerConfigurationOptions extends RunConfigurationOptions {

  private final StoredProperty<String> mCheckoutDirectory = string("").provideDelegate(this,
      "checkoutDirectory");

  private final StoredProperty<String> mPhysicalBuildDirectory = string(
      "out/Debug").provideDelegate(this, "physicalBuildDirectory");
  private final StoredProperty<String> mEmulatorBuildDirectory = string(
      "out/Emulator").provideDelegate(this, "emulatorBuildDirectory");

  public String getCheckoutDirectory() {
    return mCheckoutDirectory.getValue(this);
  }

  public void setCheckoutDirectory(String checkoutDirectory) {
    mCheckoutDirectory.setValue(this, checkoutDirectory);
  }

  public String getPhysicalBuildDirectory() {
    return mPhysicalBuildDirectory.getValue(this);
  }

  public void setPhysicalBuildDirectory(String buildDirectory) {
    mPhysicalBuildDirectory.setValue(this, buildDirectory);
  }

  public String getEmulatorBuildDirectory() {
    return mEmulatorBuildDirectory.getValue(this);
  }

  public void setEmulatorBuildDirectory(String buildDirectory) {
    mEmulatorBuildDirectory.setValue(this, buildDirectory);
  }
}
