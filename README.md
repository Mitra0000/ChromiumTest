# ChromiumTest

Chromium Test is an IntelliJ/Android Studio plugin which lets Chromium
developers run Java tests from the editor gutter.

## Problem
Chromium doesn't use gradle as its build system. Instead, it uses Ninja. As a
result, Android Studio doesn't know how to run our tests as the gutter Run icon
defaults to using a Gradle script. While there are workarounds like adding a
call to an external shell script in the "Before Launch" section, they tend to
come with unwanted side effects like popup error messages saying that Gradle
couldn't build the test correctly.

## Setup
See build/archive/README.md for instructions on how to install the plugin. Once
the plugin is installed, go to File > Settings > Tools > Chromium Test Settings.

The setup panel should load on the right hand side. First, point the Checkout
directory to the root of your Clankium checkout and set the physical and
emulator build directories. Once that is all done, you should see the new run
icons appear in the gutter. Click one and select either Run on Device or Run on
Emulator. It's as simple as that.

If you don't see the icons showing in the gutter, you may need to select them in
Settings. Open File > Settings > Editor > General > Gutter Icons and ensure that
the Chromium Test icon (labelled Run Chromium tests) is checked.

## Under the hood
The plugin defines a new RunLineMarkerContributor. This class tells the IDE to
add a new gutter icon for certain elements in the PSI tree. By overriding
`@Nullable Info getInfo(@NotNull PsiElement e)` the RunLineMarkerContributor
can, given a PSI element, either return an Info object or null to signify that
no icon should be shown for that element.

The Info object contains an icon, title, tooltip and one or more AnAction
objects. Each AnAction defines an individual action in the menu which is shown
when the gutter icon is clicked. ChromiumTest defines two AnActions: the first
runs the given test(s) on a physical device; the second runs the test(s) on an
emulator.

Both of these AnAction objects create a new temporary TestRunnerConfiguration: a
custom Run Configuration type which launches tools/autotest.py. The AnAction
fills in the correct file name, class name and method name to pass to autotest
and then executes the Run Configuration in the IDE's run window.