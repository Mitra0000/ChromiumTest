package org.chromium.chromium_test.chromiumtest;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

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
  }
}
