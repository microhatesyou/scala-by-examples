scalaVersion := "2.13.6"

// Set to false or remove if you want to show stubs as linking errors
nativeLinkStubs := true

enablePlugins(ScalaNativePlugin)

libraryDependencies += "com.github.scopt" %%% "scopt" % "4.0.1"
