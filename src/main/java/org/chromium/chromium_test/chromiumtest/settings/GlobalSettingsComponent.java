package org.chromium.chromium_test.chromiumtest.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.EditorTextField;
import com.intellij.util.ui.FormBuilder;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import org.jetbrains.annotations.NotNull;

public class GlobalSettingsComponent {

  private final JPanel mMainPanel;
  private final TextFieldWithBrowseButton mCheckoutDirectory;
  private final EditorTextField mPhysicalBuildDirectory;
  private final EditorTextField mEmulatorBuildDirectory;

  public GlobalSettingsComponent() {
    mCheckoutDirectory = new TextFieldWithBrowseButton();
    mCheckoutDirectory.addBrowseFolderListener("Select Your Clankium Checkout Root", null, null,
        FileChooserDescriptorFactory.createSingleFileDescriptor());
    mPhysicalBuildDirectory = new EditorTextField();
    mEmulatorBuildDirectory = new EditorTextField();
    mMainPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent("Checkout directory", mCheckoutDirectory)
        .addComponent(new Separator())
        .addLabeledComponent("Physical build directory", mPhysicalBuildDirectory)
        .addLabeledComponent("Emulator build directory", mEmulatorBuildDirectory)
        .addComponentFillVertically(new JPanel(), 0)
        .getPanel();
  }

  public JPanel getPanel() {
    return mMainPanel;
  }

  public JComponent getPreferredFocusedComponent() {
    return mCheckoutDirectory;
  }

  @NotNull
  public String getCheckoutDirectory() {
    return mCheckoutDirectory.getText();
  }

  public void setCheckoutDirectory(String checkoutDirectory) {
    mCheckoutDirectory.setText(checkoutDirectory);
  }

  public String getPhysicalBuildDirectory() {
    return mPhysicalBuildDirectory.getText();
  }

  public void setPhysicalBuildDirectory(String buildDirectory) {
    mPhysicalBuildDirectory.setText(buildDirectory);
  }

  public String getEmulatorBuildDirectory() {
    return mEmulatorBuildDirectory.getText();
  }

  public void setEmulatorBuildDirectory(String buildDirectory) {
    mEmulatorBuildDirectory.setText(buildDirectory);
  }
}
