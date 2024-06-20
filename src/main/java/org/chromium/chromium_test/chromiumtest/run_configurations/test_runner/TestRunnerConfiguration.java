package org.chromium.chromium_test.chromiumtest.run_configurations.test_runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessHandlerFactory;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestRunnerConfiguration extends RunConfigurationBase<TestRunnerConfigurationOptions> {
  private @NotNull String mFileName = "";
  private @NotNull String mClassName = "";
  private @NotNull String mMethodName = "";
  private boolean mIsPhysical = true;

  protected TestRunnerConfiguration(Project project, ConfigurationFactory factory, String name) {
    super(project, factory, name);
  }

  @Override
  @NotNull
  protected TestRunnerConfigurationOptions getOptions() {
    return (TestRunnerConfigurationOptions) super.getOptions();
  }

  @Override
  @NotNull
  public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    return new TestRunnerSettingsEditor();
  }

  @Override
  @Nullable
  public RunProfileState getState(@NotNull Executor executor,
      @NotNull ExecutionEnvironment environment) {
    return new CommandLineState(environment) {
      @Override
      protected @NotNull ProcessHandler startProcess() throws ExecutionException {
        GeneralCommandLine commandLine =
            new GeneralCommandLine("tools/autotest.py", "-C", getBuildDirectory(), mFileName, "-f",
                "*" + mClassName + "#" + (StringUtils.isNotEmpty(mMethodName) ? mMethodName : "*"));
        commandLine.setWorkDirectory(getCheckoutDirectory());
        OSProcessHandler processHandler = ProcessHandlerFactory.getInstance()
            .createColoredProcessHandler(commandLine);
        ProcessTerminatedListener.attach(processHandler);
        return processHandler;
      }
    };
  }

  public String getCheckoutDirectory() {
    return getOptions().getCheckoutDirectory();
  }

  public void setCheckoutDirectory(String scriptName) {
    getOptions().setCheckoutDirectory(scriptName);
  }

  public String getPhysicalBuildDirectory() {
    return getOptions().getPhysicalBuildDirectory();
  }

  public void setPhysicalBuildDirectory(String buildDirectory) {
    getOptions().setPhysicalBuildDirectory(buildDirectory);
  }

  public String getEmulatorBuildDirectory() {
    return getOptions().getEmulatorBuildDirectory();
  }

  public void setEmulatorBuildDirectory(String buildDirectory) {
    getOptions().setEmulatorBuildDirectory(buildDirectory);
  }

  public String getBuildDirectory() {
    return mIsPhysical ? getOptions().getPhysicalBuildDirectory()
        : getOptions().getEmulatorBuildDirectory();
  }

  public void setFileName(String fileName) {
    mFileName = fileName;
  }

  public void setClassName(String className) {
    mClassName = className;
  }

  public void setMethodName(String methodName) {
    mMethodName = methodName;
  }

  public void setIsPhysical(boolean isPhysical) {
    mIsPhysical = isPhysical;
  }
}