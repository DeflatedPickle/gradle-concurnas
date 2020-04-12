package com.deflatedpickle.gradle.concurnas;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.internal.file.FileResolver;

public class ConcSouceSetFactory implements NamedDomainObjectFactory<ConcSourceSet> {
    private final FileResolver fileResolver;

    public ConcSouceSetFactory(FileResolver fileResolver) {
        this.fileResolver = fileResolver;
    }

    @Override
    public ConcSourceSet create(String name) {
        return new DefaultConcSourceSet(name, fileResolver);
    }
}
