package org.chromium.chromium_test.run_configurations.test_bisector;

import com.intellij.openapi.components.StoredProperty;
import org.chromium.chromium_test.run_configurations.TestRelatedConfigurationOptions;

public class TestBisectorConfigurationOptions extends TestRelatedConfigurationOptions {


  private final StoredProperty<String> mBadHash = string("").provideDelegate(this,
      "badHash");
  private final StoredProperty<String> mGoodHash = string("").provideDelegate(this,
      "goodHash");

  public String getBadHash() {
    return mBadHash.getValue(this);
  }

  public void setBadHash(String badHash) {
    mBadHash.setValue(this, badHash);
  }

  public String getGoodHash() {
    return mGoodHash.getValue(this);
  }

  public void setGoodHash(String goodHash) {
    mGoodHash.setValue(this, goodHash);
  }

}
