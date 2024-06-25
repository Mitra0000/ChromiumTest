package org.chromium.chromium_test.run_configurations.test_bisector;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.execution.configurations.VirtualConfigurationType;
import com.intellij.icons.AllIcons.Xml.Browsers;
import org.chromium.chromium_test.TestRequest;

public class TestBisectorConfigurationType extends ConfigurationTypeBase implements
    VirtualConfigurationType {

  static final String ID = "ChromiumTestBisectorConfig";
  private static final String DISPLAY_NAME = "Chromium Test Bisector";
  private static final String DESCRIPTION = "Find when Chromium tests started failing automatically.";

  public TestBisectorConfigurationType() {
    super(ID, DISPLAY_NAME, DESCRIPTION, Browsers.Chromium);
    addFactory(new TestBisectorConfigurationFactory(this, new TestRequest("", "", "")));
  }

  @Override
  public boolean isManaged() {
    return false;
  }
}