package org.chromium.chromium_test.chromiumtest.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;

@State(
    name = "org.chromium.chromium_test.chromiumtest.settings.GlobalSettings",
    storages = @Storage("ChromiumTestSettings.xml")
)
public final class GlobalSettings
    implements PersistentStateComponent<GlobalSettings.State> {

  public static class State {

    public String checkoutDirectory = "";
    public String physicalBuildDirectory = "";
    public String emulatorBuildDirectory = "";
  }

  private State mState = new State();

  public static GlobalSettings getInstance() {
    return ApplicationManager.getApplication()
        .getService(GlobalSettings.class);
  }

  @Override
  public State getState() {
    return mState;
  }

  @Override
  public void loadState(@NotNull State state) {
    mState = state;
  }

}