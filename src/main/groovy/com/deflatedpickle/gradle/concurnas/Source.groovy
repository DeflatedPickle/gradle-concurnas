package com.deflatedpickle.gradle.concurnas

class Source {
    // The compilers directory
    String home
    // The JVM arguments
    String[] javaArgs = []
    // The Concurnas arguments
    String[] conccArgs = []
    // The REPL arguments
    String[] replArgs = []

    String getHome() {
        if (home == null) {
            // I think it should be uppercase, but it isn't in the docs
            // So, for case-sensitive operating systems, it's lowercase
            System.getenv("conc_home")
        }
        else {
            home
        }
    }
}
