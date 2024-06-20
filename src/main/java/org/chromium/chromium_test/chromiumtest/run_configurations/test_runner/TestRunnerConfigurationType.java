package org.chromium.chromium_test.chromiumtest.run_configurations.test_runner;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.icons.AllIcons.Xml.Browsers;

public class TestRunnerConfigurationType extends ConfigurationTypeBase {
  static final String ID = "ChromiumTestRunnerConfig";
  private static final String DISPLAY_NAME = "Chromium Test Runner";
  private static final String DESCRIPTION = "Run Chromium tests automatically from the gutter.";

  public TestRunnerConfigurationType() {
    super(ID, DISPLAY_NAME, DESCRIPTION, Browsers.Chromium);
    addFactory(new TestRunnerConfigurationFactory(this, "", "", ""));
  }
}
