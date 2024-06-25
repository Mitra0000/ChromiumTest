package org.chromium.chromium_test;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.psi.PsiElement;
import icons.Icons;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class provides a way for the user to enable or disable showing the gutter icons for the
 * ChromiumTest plugin. The setting shows in File > Settings > Editor > General > Gutter Icons.
 */
public class TestLineMarkerDescriptor extends LineMarkerProviderDescriptor {

  @Override
  public String getName() {
    return "Run Chromium tests";
  }

  @Override
  public @Nullable Icon getIcon() {
    return Icons.RunIcon;
  }

  @Override
  public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
    return null;
  }
}
