package org.chromium.chromium_test.run_configurations.test_bisector;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsSafe;
import org.chromium.chromium_test.TestRequest;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestBisectorConfigurationFactory extends ConfigurationFactory {

  private final TestRequest mRequest;

  public TestBisectorConfigurationFactory(ConfigurationType type, TestRequest request) {
    super(type);
    mRequest = request;
  }

  @Override
  public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
    return new TestBisectorConfiguration(project, this, "Chromium Test Bisector");
  }

  @Override
  public @NotNull RunConfiguration createConfiguration(@NlsSafe @Nullable String name,
      @NotNull RunConfiguration template) {
    TestBisectorConfiguration config = (TestBisectorConfiguration) template;
    config.getOptions().setFileName(mRequest.fileName());
    config.getOptions().setClassName(mRequest.className());
    config.getOptions().setMethodName(mRequest.methodName());
    return config;
  }

  @Override
  public @NotNull @NonNls String getId() {
    return TestBisectorConfigurationType.ID;
  }

  @Override
  public @Nullable Class<? extends BaseState> getOptionsClass() {
    return TestBisectorConfigurationOptions.class;
  }

  @Override
  public boolean isApplicable(@NotNull Project project) {
    return false;
  }
}
