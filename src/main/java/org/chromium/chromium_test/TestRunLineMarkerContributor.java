package org.chromium.chromium_test;

import com.intellij.codeInsight.TestFrameworks;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testIntegration.TestFramework;
import icons.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides {@code Info} objects given a PsiElement. An {@code Info} object consists of an icon,
 * metadata like title and tooltip text as well as {@code AnAction} which is performed when clicked.
 *
 * This RunLineMarkerContributor provider gutter icons for Java tests which will run the test or
 * test suite using Chromium's tools/autotest.py.
 */
public class TestRunLineMarkerContributor extends RunLineMarkerContributor implements DumbAware {

  private static final Logger LOG = Logger.getInstance(TestRunLineMarkerContributor.class);

  @Override
  public @Nullable Info getInfo(@NotNull PsiElement e) {
    if (!isIdentifier(e)) {
      return null;
    }
    PsiElement element = e.getParent();
    if (element instanceof PsiClass) {
      PsiClass psiClass = (PsiClass) element;
      if (!isTestClass(psiClass)) {
        return null;
      }
      return createInfoForClass(psiClass.getContainingFile().getName(), psiClass.getName());
    }
    if (element instanceof PsiMethod) {
      PsiMethod psiMethod = (PsiMethod) element;
      PsiClass containingClass = PsiTreeUtil.getParentOfType(psiMethod, PsiClass.class);
      if (!isTestMethod(containingClass, psiMethod)) {
        return null;
      }
      return createInfoForMethod(containingClass.getContainingFile().getName(),
          containingClass.getName(), psiMethod.getName());
    }
    return null;
  }

  private static boolean isTestClass(PsiClass clazz) {
    if (clazz == null) {
      return false;
    }
    try {
      return DumbService.getInstance(clazz.getProject())
          .computeWithAlternativeResolveEnabled(() -> {
            TestFramework framework = TestFrameworks.detectFramework(clazz);
            return framework != null && framework.isTestClass(clazz);
          });
    } catch (IndexNotReadyException e) {
      LOG.error(e);
      return false;
    }
  }

  private static boolean isTestMethod(PsiClass containingClass, PsiMethod method) {
    if (containingClass == null) {
      return false;
    }
    TestFramework framework = TestFrameworks.detectFramework(containingClass);
    return framework != null && framework.isTestMethod(method, false);
  }

  private static @NotNull Info createInfoForClass(String fileName, String className) {
    return createInfoForMethod(fileName, className, "");
  }

  private static @NotNull Info createInfoForMethod(String fileName, String className,
      String methodName) {
    AnAction[] actions = new AnAction[2];
    actions[0] = new RunTestAction(fileName, className, methodName, true);
    actions[1] = new RunTestAction(fileName, className, methodName, false);
    return new Info(Icons.RunIcon, actions, element -> "Test with autotest.py");
  }

  private static boolean isIdentifier(PsiElement e) {
    return e instanceof PsiIdentifier;
  }
}