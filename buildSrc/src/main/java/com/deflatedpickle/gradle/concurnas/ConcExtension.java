package com.deflatedpickle.gradle.concurnas;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.internal.file.FileResolver;

public class ConcExtension {
    private final NamedDomainObjectContainer<ConcSourceSet> sourceSetsContainer;

    public ConcExtension(Project project, FileResolver fileResolver) {
        sourceSetsContainer = project.container(
                ConcSourceSet.class,
                new ConcSouceSetFactory(fileResolver)
        );
    }

    public NamedDomainObjectContainer<ConcSourceSet> getSourceSetsContainer() {
        return sourceSetsContainer;
    }

    public void srcDir(String file) {
        sourceSetsContainer.getByName("main").getConc().srcDir(file);
    }
}
