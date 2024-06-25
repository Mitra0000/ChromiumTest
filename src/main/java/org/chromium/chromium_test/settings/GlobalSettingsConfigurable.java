package org.chromium.chromium_test.settings;

import com.intellij.openapi.options.Configurable;
import java.util.Objects;
import javax.swing.JComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

public class GlobalSettingsConfigurable implements Configurable {

  private GlobalSettingsComponent mSettingsComponent;

  // A default constructor with no arguments is required because
  // this implementation is registered as an applicationConfigurable

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return "Chromium Test Settings";
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return mSettingsComponent.getPreferredFocusedComponent();
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    mSettingsComponent = new GlobalSettingsComponent();
    return mSettingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    GlobalSettings.State state =
        Objects.requireNonNull(GlobalSettings.getInstance().getState());
    return !mSettingsComponent.getCheckoutDirectory().equals(state.checkoutDirectory) ||
        !mSettingsComponent.getPhysicalBuildDirectory().equals(state.physicalBuildDirectory) ||
        !mSettingsComponent.getEmulatorBuildDirectory().equals(state.emulatorBuildDirectory);
  }

  @Override
  public void apply() {
    GlobalSettings.State state =
        Objects.requireNonNull(GlobalSettings.getInstance().getState());
    state.checkoutDirectory = mSettingsComponent.getCheckoutDirectory();
    state.physicalBuildDirectory = mSettingsComponent.getPhysicalBuildDirectory();
    state.emulatorBuildDirectory = mSettingsComponent.getEmulatorBuildDirectory();
  }

  @Override
  public void reset() {
    GlobalSettings.State state =
        Objects.requireNonNull(GlobalSettings.getInstance().getState());
    mSettingsComponent.setCheckoutDirectory(state.checkoutDirectory);
    mSettingsComponent.setPhysicalBuildDirectory(state.physicalBuildDirectory);
    mSettingsComponent.setEmulatorBuildDirectory(state.emulatorBuildDirectory);
  }

  @Override
  public void disposeUIResources() {
    mSettingsComponent = null;
  }

}
