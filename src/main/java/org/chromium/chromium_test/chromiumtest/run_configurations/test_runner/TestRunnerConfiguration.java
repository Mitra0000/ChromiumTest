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
import org.chromium.chromium_test.chromiumtest.settings.GlobalSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestRunnerConfiguration extends RunConfigurationBase<TestRunnerConfigurationOptions> {

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
    GlobalSettings.State settings = GlobalSettings.getInstance().getState();
    return new CommandLineState(environment) {
      @Override
      protected @NotNull ProcessHandler startProcess() throws ExecutionException {
        GeneralCommandLine commandLine =
            new GeneralCommandLine("tools/autotest.py", "-C", getBuildDirectory(settings),
                getOptions().getFileName(), "-f",
                "*" + getOptions().getClassName() + "#" + (
                    StringUtils.isNotEmpty(getOptions().getMethodName())
                        ? getOptions().getMethodName() : "*"));
        commandLine.setWorkDirectory(getCheckoutDirectory(settings));
        OSProcessHandler processHandler = ProcessHandlerFactory.getInstance()
            .createColoredProcessHandler(commandLine);
        ProcessTerminatedListener.attach(processHandler);
        return processHandler;
      }
    };
  }

  public String getCheckoutDirectory(GlobalSettings.State settings) {
    return settings.checkoutDirectory;
  }

  public String getBuildDirectory(GlobalSettings.State settings) {
    return mIsPhysical ? settings.physicalBuildDirectory
        : settings.emulatorBuildDirectory;
  }

  public void setIsPhysical(boolean isPhysical) {
    mIsPhysical = isPhysical;
  }
}