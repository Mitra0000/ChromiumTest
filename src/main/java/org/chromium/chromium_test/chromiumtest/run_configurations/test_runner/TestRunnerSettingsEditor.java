package org.chromium.chromium_test.chromiumtest.run_configurations.test_runner;

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
  private final TextFieldWithBrowseButton mFileName;
  private final EditorTextField mClassName;
  private final EditorTextField mMethodName;

  public TestRunnerSettingsEditor() {
    mFileName = new TextFieldWithBrowseButton();
    mFileName.addBrowseFolderListener("Select The File Containing The Tests", null, null,
        FileChooserDescriptorFactory.createSingleFileDescriptor("java"));

    mClassName = new EditorTextField();
    mMethodName = new EditorTextField();
    mPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent("File Name", mFileName)
        .addComponent(new Separator())
        .addLabeledComponent("Class Name", mClassName)
        .addLabeledComponent("Method Name", mMethodName)
        .getPanel();
  }

  @Override
  protected void resetEditorFrom(TestRunnerConfiguration runConfiguration) {
    mFileName.setText(runConfiguration.getOptions().getFileName());
    mClassName.setText(runConfiguration.getOptions().getClassName());
    mMethodName.setText(runConfiguration.getOptions().getMethodName());
  }

  @Override
  protected void applyEditorTo(@NotNull TestRunnerConfiguration runConfiguration) {
    runConfiguration.getOptions().setFileName(mFileName.getText());
    runConfiguration.getOptions().setClassName(mClassName.getText());
    runConfiguration.getOptions().setMethodName(mMethodName.getText());
  }

  @NotNull
  @Override
  protected JComponent createEditor() {
    return mPanel;
  }
}
