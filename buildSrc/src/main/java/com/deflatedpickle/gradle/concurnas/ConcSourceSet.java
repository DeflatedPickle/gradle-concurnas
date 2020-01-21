package com.deflatedpickle.gradle.concurnas;

import groovy.lang.Closure;
import org.gradle.api.file.SourceDirectorySet;

public interface ConcSourceSet {
    String getName();
    SourceDirectorySet getConc();
    ConcSourceSet conc(Closure closure);
}
