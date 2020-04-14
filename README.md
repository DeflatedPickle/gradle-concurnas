# gradle-concurnas
A Gradle plugin for Concurnas.

## Installation
Edit your plugins block to reference to this plugin by `id` and latest release version.

```groovy
plugins {
  id "com.deflatedpickle.gradle.concurnas" version "0.0.1"
}
```

## Requirements
To use this plugin, you will either need to have Gradle installed, or have the Gradle cache files in your directory (such as `gradlew` or `gradlew.bat`).

You are ***NOT*** required to have Concurnas installed, as the `concInstall` task can handle that.

You ***DO*** need to either set `conc.home` or set the system environment variable, `conc_home`.

## Usage
The most basic usage of this plugin is just including the plugin. It will default to using the Concurnas compiler located in `conc_home`.

A source set will be created for `src/main/concurnas`.

### Conc Extension
The `conc` extension is where variables for the compiler and REPL are to be added.

#### Compiler Location
Instead of using the compiler at `conc_home`, you can customize the location like so:

```groovy
conc {
    home = 'H:\\Concurnas'
}
```

***Or:***

```groovy
conc.home = 'H:\\Concurnas'
```

> The type of `home` is implemented as a `String`.
>
> If Concurnas-*.jar can't be found in the specified location (`home` or otherwise), the plugin will download the latest version to the specified file

#### Compiler Arguments
The Concurnas compiler can accept multiple arguments, you can specify these from Gradle like so:

```groovy
conc {
    conccArgs += '-verbose'
}

```

***Or:***

```groovy
conc.conccArgs += '-verbose'
```

> The type of `conccArgs` is implemented as a `String[]`.

#### REPL Arguments
These are arguments that are sent to the REPL (when it's run). They are given like so:

```groovy
conc {
    replArgs += '-verbose'
}
```

***Or:***

```groovy
conc.replArgs += '-verbose'
```


#### Java Arguments
These are the JVM arguments that will be used both when compiling and running the REPL. You can specify them with:

```groovy
conc {
    javaArgs += '-verbose'
}
```

***Or:***

```groovy
conc.javaArgs += '-verbose'
```

### CompileConc Task
> Depends on: `concInstall`

The `conc` task is the main task for the plugin.

### ConcInstall Task
> Depends on: `null`

This task will check the `home` directory (supplied to the `conc` task) for a suitable Concurnas JAR.

> A suitable JAR is simply named `Concurnas-*.jar`, where `*` is the version  

If a suitable JAR is ***NOT*** found, the latest release on the Concurnas GitHub repository will be downloaded and unzipped to the specified `conc.home`.

### ConcRuntimeCache Task
> Depends on: `concInstall`

This task is currently broken, you ***SHOULDN'T*** run it, but you ***SHOULD*** write a pull request if you know how to fix it!

### ConcLibCompilation Task
> Depends on: `concInstall`

This task is currently broken, you ***SHOULDN'T*** run it, but you ***SHOULD*** write a pull request if you know how to fix it!

### ConcRepl Task
> Depends on: `concInstall`

This task will launch the Concurnas REPL. It uses the arguments supplied in `conc.replArgs`.
