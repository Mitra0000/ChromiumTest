package org.chromium.chromium_test.chromiumtest;

import com.intellij.execution.ExecutorRegistry;
import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.wm.ToolWindowId;
import icons.Icons;
import org.apache.commons.lang.StringUtils;
import org.chromium.chromium_test.chromiumtest.run_configurations.test_runner.TestRunnerConfiguration;
import org.chromium.chromium_test.chromiumtest.run_configurations.test_runner.TestRunnerConfigurationFactory;
import org.chromium.chromium_test.chromiumtest.run_configurations.test_runner.TestRunnerConfigurationType;
import org.jetbrains.annotations.NotNull;

public class RunTestAction extends AnAction implements DumbAware {
  private final String mFileName;
  private final String mClassName;
  private final String mMethodName;
  private final boolean mIsPhysicalDevice;

  RunTestAction(String fileName, String className, String methodName, boolean isPhysicalDevice) {
    super(
        String.format("Run '%s' on %s", StringUtils.isNotEmpty(methodName) ? methodName : className,
            isPhysicalDevice ? "Device" : "Emulator"),
        "Runs the selected tests using autotest.",
        isPhysicalDevice ? Icons.AndroidDevice : AllIcons.CodeWithMe.CwmScreenOn);
    mFileName = fileName;
    mClassName = className;
    mMethodName = methodName;
    mIsPhysicalDevice = isPhysicalDevice;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    TestRunnerConfigurationFactory factory = new TestRunnerConfigurationFactory(
        new TestRunnerConfigurationType(), mFileName, mClassName, mMethodName);
    RunnerAndConfigurationSettings settings = RunManager.getInstance(e.getProject())
        .createConfiguration(String.format("Testing %s#%s", mClassName, mMethodName), factory);
    TestRunnerConfiguration config = (TestRunnerConfiguration) settings.getConfiguration();
    config.setFileName(mFileName);
    config.setClassName(mClassName);
    config.setMethodName(mMethodName);
    config.setIsPhysical(mIsPhysicalDevice);

    ProgramRunnerUtil.executeConfiguration(settings,
        ExecutorRegistry.getInstance().getExecutorById(ToolWindowId.RUN));
  }

  @Override
  public boolean isDumbAware() {
    return true;
  }
}
