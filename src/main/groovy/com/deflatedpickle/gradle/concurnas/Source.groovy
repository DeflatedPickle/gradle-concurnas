package com.deflatedpickle.gradle.concurnas

class Source {
    // The compilers directory
    String home

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
