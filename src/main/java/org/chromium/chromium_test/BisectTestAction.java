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
import org.apache.commons.lang.StringUtils;
import org.chromium.chromium_test.common.EmptyExecutionTarget;
import org.chromium.chromium_test.run_configurations.test_bisector.TestBisectorConfigurationFactory;
import org.chromium.chromium_test.run_configurations.test_bisector.TestBisectorConfigurationType;
import org.jetbrains.annotations.NotNull;

/** Implementation of AnAction which finds the commit that broke a test/suite of tests. */
public class BisectTestAction extends AnAction implements DumbAware {

  private final TestRequest mRequest;

  BisectTestAction(String fileName, String className, String methodName) {
    super(
        String.format("Find when '%s' started failing",
            StringUtils.isNotEmpty(methodName) ? methodName : className),
        "Bisects the selected tests to find when they started failing.",
        AllIcons.Debugger.ShowCurrentFrame);
    mRequest = new TestRequest(fileName, className, methodName);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    TestBisectorConfigurationFactory factory = new TestBisectorConfigurationFactory(
        new TestBisectorConfigurationType(), mRequest);
    RunnerAndConfigurationSettings settings = RunManager.getInstance(e.getProject())
        .createConfiguration(
            String.format("Testing %s#%s", mRequest.className(), mRequest.methodName()), factory);
    settings.setEditBeforeRun(true);

    // Switch out the active execution target to prevent Android Studio from rejecting the run
    // configuration for the selected Android device.
    ExecutionTargetManager manager = ExecutionTargetManager.getInstance(e.getProject());
    ExecutionTarget target = manager.getActiveTarget();
    manager.setActiveTarget(EmptyExecutionTarget.get());

    ProgramRunnerUtil.executeConfiguration(settings,
        ExecutorRegistry.getInstance().getExecutorById(ToolWindowId.RUN));
    manager.setActiveTarget(target);
  }
}
