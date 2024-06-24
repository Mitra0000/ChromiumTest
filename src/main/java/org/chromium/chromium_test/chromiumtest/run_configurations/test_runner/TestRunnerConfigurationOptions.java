package org.chromium.chromium_test.chromiumtest.run_configurations.test_runner;

import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;
import org.jetbrains.annotations.NotNull;

public class TestRunnerConfigurationOptions extends RunConfigurationOptions {

  private final StoredProperty<String> mFileName = string("").provideDelegate(this,
      "fileName");

  private final StoredProperty<String> mClassName = string("").provideDelegate(this,
      "className");
  private final StoredProperty<String> mMethodName = string("").provideDelegate(this,
      "methodName");

  public String getFileName() {
    return mFileName.getValue(this);
  }

  public void setFileName(String fileName) {
    mFileName.setValue(this, fileName);
  }

  public String getClassName() {
    return mClassName.getValue(this);
  }

  public void setClassName(String className) {
    mClassName.setValue(this, className);
  }

  public String getMethodName() {
    return mMethodName.getValue(this);
  }

  public void setMethodName(String methodName) {
    mMethodName.setValue(this, methodName);
  }
}
