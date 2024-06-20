package org.chromium.chromium_test.chromiumtest.run_configurations.test_runner;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestRunnerConfigurationFactory extends ConfigurationFactory {
  private final @NotNull String mFileName;
  private final @NotNull String mClassName;
  private final @NotNull String mMethodName;

  public TestRunnerConfigurationFactory(ConfigurationType type, @NotNull String fileName,
      @NotNull String className, @NotNull String methodName) {
    super(type);
    mFileName = fileName;
    mClassName = className;
    mMethodName = methodName;
  }

  @Override
  public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
    return new TestRunnerConfiguration(project, this, "Chromium Test");
  }

  @Override
  public @NotNull RunConfiguration createConfiguration(@NlsSafe @Nullable String name,
      @NotNull RunConfiguration template) {
    TestRunnerConfiguration config = (TestRunnerConfiguration) template;
    config.setFileName(mFileName);
    config.setClassName(mClassName);
    config.setMethodName(mMethodName);
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
