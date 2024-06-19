package org.chromium.chromium_test.chromiumtest;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.psi.PsiElement;
import icons.Icons;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
