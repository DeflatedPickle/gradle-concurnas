package com.deflatedpickle.gradle.concurnas;

import groovy.lang.Closure;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.DefaultSourceDirectorySet;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.api.internal.file.collections.DefaultDirectoryFileTreeFactory;
import org.gradle.util.ConfigureUtil;

public class DefaultConcSourceSet implements ConcSourceSet {
    final String name;
    final SourceDirectorySet foo;

    public DefaultConcSourceSet(String displayName, FileResolver fileResolver) {
        this.name = displayName;
        DefaultDirectoryFileTreeFactory ddftf = new DefaultDirectoryFileTreeFactory();
        foo = new DefaultSourceDirectorySet(name, fileResolver, ddftf);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SourceDirectorySet getConc() {
        return foo;
    }

    @Override
    public ConcSourceSet conc(Closure closure) {
        ConfigureUtil.configure(closure, foo);
        return this;
    }
}
