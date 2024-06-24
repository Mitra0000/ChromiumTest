package org.chromium.chromium_test.chromiumtest.run_configurations.test_runner;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsSafe;
import org.chromium.chromium_test.chromiumtest.TestRequest;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestRunnerConfigurationFactory extends ConfigurationFactory {

  private final TestRequest mRequest;

  public TestRunnerConfigurationFactory(ConfigurationType type, TestRequest request) {
    super(type);
    mRequest = request;
  }

  @Override
  public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
    return new TestRunnerConfiguration(project, this, "Chromium Test");
  }

  @Override
  public @NotNull RunConfiguration createConfiguration(@NlsSafe @Nullable String name,
      @NotNull RunConfiguration template) {
    TestRunnerConfiguration config = (TestRunnerConfiguration) template;
    config.getOptions().setFileName(mRequest.fileName());
    config.getOptions().setClassName(mRequest.className());
    config.getOptions().setMethodName(mRequest.methodName());
    return config;
  }

  @Override
  public @NotNull @NonNls String getId() {
    return TestRunnerConfigurationType.ID;
  }

  @Override
  public @Nullable Class<? extends BaseState> getOptionsClass() {
    return TestRunnerConfigurationOptions.class;
  }
}
