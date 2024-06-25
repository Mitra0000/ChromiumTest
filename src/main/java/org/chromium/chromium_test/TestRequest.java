package org.chromium.chromium_test;

import org.jetbrains.annotations.NotNull;

/**
 * A simple, immutable struct which stores all the data needed about a given test or suite of tests.
 */
public class TestRequest {
  private final @NotNull String mFileName;
  private final @NotNull String mClassName;
  private final @NotNull String mMethodName;

  public TestRequest(@NotNull String fileName, @NotNull String className, @NotNull String methodName) {
    mFileName = fileName;
    mClassName = className;
    mMethodName = methodName;
  }

  public @NotNull String fileName() {
    return mFileName;
  }

  public @NotNull String className() {
    return mClassName;
  }

  public @NotNull String methodName() {
    return mMethodName;
  }
}
