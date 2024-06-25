package org.chromium.chromium_test;

import com.intellij.execution.ExecutionTarget;
import com.intellij.execution.ExecutionTargetManager;
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
import javax.swing.Icon;
import org.apache.commons.lang.StringUtils;
import org.chromium.chromium_test.run_configurations.test_runner.TestRunnerConfiguration;
import org.chromium.chromium_test.run_configurations.test_runner.TestRunnerConfigurationFactory;
import org.chromium.chromium_test.run_configurations.test_runner.TestRunnerConfigurationType;
import org.jetbrains.annotations.NotNull;

/** Implementation of AnAction which runs either an individual test or all tests in a class. */
public class RunTestAction extends AnAction implements DumbAware {

  private final TestRequest mRequest;
  private final boolean mIsPhysicalDevice;

  RunTestAction(String fileName, String className, String methodName, boolean isPhysicalDevice) {
    super(
        String.format("Run '%s' on %s", StringUtils.isNotEmpty(methodName) ? methodName : className,
            isPhysicalDevice ? "Device" : "Emulator"),
        "Runs the selected tests using autotest.",
        isPhysicalDevice ? Icons.AndroidDevice : AllIcons.CodeWithMe.CwmScreenOn);
    mRequest = new TestRequest(fileName, className, methodName);
    mIsPhysicalDevice = isPhysicalDevice;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    TestRunnerConfigurationFactory factory = new TestRunnerConfigurationFactory(
        new TestRunnerConfigurationType(), mRequest);
    RunnerAndConfigurationSettings settings = RunManager.getInstance(e.getProject())
        .createConfiguration(
            String.format("Testing %s#%s", mRequest.className(), mRequest.methodName()), factory);
    TestRunnerConfiguration config = (TestRunnerConfiguration) settings.getConfiguration();
    config.setIsPhysical(mIsPhysicalDevice);

    // Switch out the active execution target to prevent Android Studio from rejecting the run
    // configuration for the selected Android device.
    ExecutionTargetManager manager = ExecutionTargetManager.getInstance(e.getProject());
    ExecutionTarget target = manager.getActiveTarget();
    manager.setActiveTarget(EMPTY_TARGET);

    ProgramRunnerUtil.executeConfiguration(settings,
        ExecutorRegistry.getInstance().getExecutorById(ToolWindowId.RUN));
    manager.setActiveTarget(target);
  }

  @Override
  public boolean isDumbAware() {
    return true;
  }

  private static final ExecutionTarget EMPTY_TARGET = new ExecutionTarget() {
    @Override
    public String getId() {
      return "";
    }

    @Override
    public String getDisplayName() {
      return "";
    }

    @Override
    public Icon getIcon() {
      return null;
    }
  };
}
