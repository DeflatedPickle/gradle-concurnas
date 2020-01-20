package com.deflatedpickle.gradle.concurnas

class Source {
    // The compilers directory
    String compiler
    // The source folder
    String src = './src/main/conc'
    // The compiler version - unused
    String version = '1.14.016'
    // The JVM arguments
    String[] jArgs = []
    // The Concurnas arguments
    String[] cArgs = []
}
