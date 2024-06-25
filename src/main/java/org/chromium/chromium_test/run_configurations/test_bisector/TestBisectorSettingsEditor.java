package org.chromium.chromium_test.run_configurations.test_bisector;

import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.ui.EditorTextField;
import com.intellij.util.ui.FormBuilder;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.NotNull;

public class TestBisectorSettingsEditor extends SettingsEditor<TestBisectorConfiguration> implements
    DumbAware {

  private final JPanel mPanel;
  private final EditorTextField mBadHash;
  private final EditorTextField mGoodHash;

  public TestBisectorSettingsEditor() {
    mBadHash = new EditorTextField();
    mGoodHash = new EditorTextField();
    mPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent("Bad Hash", mBadHash)
        .addLabeledComponent("Good Hash", mGoodHash)
        .getPanel();
  }

  @Override
  protected void resetEditorFrom(@NotNull TestBisectorConfiguration runConfiguration) {
    mBadHash.setText(runConfiguration.getOptions().getBadHash());
    mGoodHash.setText(runConfiguration.getOptions().getGoodHash());
  }

  @Override
  protected void applyEditorTo(@NotNull TestBisectorConfiguration runConfiguration) {
    runConfiguration.getOptions().setBadHash(mBadHash.getText());
    runConfiguration.getOptions().setGoodHash(mGoodHash.getText());
  }

  @Override
  protected @NotNull JComponent createEditor() {
    return mPanel;
  }
}
