package org.chromium.chromium_test.run_configurations.test_bisector;

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
import org.chromium.chromium_test.settings.GlobalSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestBisectorConfiguration extends
    RunConfigurationBase<TestBisectorConfigurationOptions> {

  protected TestBisectorConfiguration(Project project, ConfigurationFactory factory, String name) {
    super(project, factory, name);
  }

  @Override
  @NotNull
  protected TestBisectorConfigurationOptions getOptions() {
    return (TestBisectorConfigurationOptions) super.getOptions();
  }

  @Override
  public @NotNull SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
    return new TestBisectorSettingsEditor();
  }

  @Override
  public @Nullable RunProfileState getState(@NotNull Executor executor,
      @NotNull ExecutionEnvironment environment) {
    GlobalSettings.State settings = GlobalSettings.getInstance().getState();
    return new CommandLineState(environment) {
      @Override
      protected @NotNull ProcessHandler startProcess() throws ExecutionException {
        GeneralCommandLine commandLine =
            new GeneralCommandLine("echo", "This is under development");
        commandLine.setWorkDirectory(settings.checkoutDirectory);
        OSProcessHandler processHandler = ProcessHandlerFactory.getInstance()
            .createColoredProcessHandler(commandLine);
        ProcessTerminatedListener.attach(processHandler);
        return processHandler;
      }
    };
  }
}
