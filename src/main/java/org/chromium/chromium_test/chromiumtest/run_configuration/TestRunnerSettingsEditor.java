package org.chromium.chromium_test.chromiumtest.run_configuration;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.EditorTextField;
import com.intellij.util.ui.FormBuilder;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import org.jetbrains.annotations.NotNull;

public class TestRunnerSettingsEditor extends SettingsEditor<TestRunnerConfiguration> implements
    DumbAware {
  private final JPanel mPanel;
  private final TextFieldWithBrowseButton mCheckoutPathField;
  private final EditorTextField mPhysicalBuildDirectory;
  private final EditorTextField mEmulatorBuildDirectory;

  public TestRunnerSettingsEditor() {
    mCheckoutPathField = new TextFieldWithBrowseButton();
    mCheckoutPathField.addBrowseFolderListener("Select Your Clankium Checkout Root", null, null,
        FileChooserDescriptorFactory.createSingleFileDescriptor());
    mPhysicalBuildDirectory = new EditorTextField();
    mEmulatorBuildDirectory = new EditorTextField();
    mPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent("Checkout directory", mCheckoutPathField)
        .addComponent(new Separator())
        .addLabeledComponent("Physical build directory", mPhysicalBuildDirectory)
        .addLabeledComponent("Emulator build directory", mEmulatorBuildDirectory)
        .getPanel();
  }

  @Override
  protected void resetEditorFrom(TestRunnerConfiguration runConfiguration) {
    mCheckoutPathField.setText(runConfiguration.getCheckoutDirectory());
    mPhysicalBuildDirectory.setText(runConfiguration.getPhysicalBuildDirectory());
    mEmulatorBuildDirectory.setText(runConfiguration.getEmulatorBuildDirectory());
  }

  @Override
  protected void applyEditorTo(@NotNull TestRunnerConfiguration runConfiguration) {
    runConfiguration.setCheckoutDirectory(mCheckoutPathField.getText());
    runConfiguration.setPhysicalBuildDirectory(mPhysicalBuildDirectory.getText());
    runConfiguration.setEmulatorBuildDirectory(mEmulatorBuildDirectory.getText());
  }

  @NotNull
  @Override
  protected JComponent createEditor() {
    return mPanel;
  }
}
