package com.deflatedpickle.gradle.concurnas

import org.codehaus.groovy.runtime.InvokerHelper
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.Convention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

class Concurnas implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.pluginManager.apply(Concurnas.class);
        ConcExtension extension = project.extensions.create(
                "concurnas",
                ConcExtension.class,
                project,
                project.fileResolver
        )

        project.properties.get("sourceSets").all { SourceSet sourceSet ->
            def concSourceSet = extension.getSourceSetsContainer().maybeCreate(sourceSet.name)
            concSourceSet.getConc().srcDir(project.file("src/" + sourceSet.name + "/concurnas"))

            (InvokerHelper.getProperty(sourceSet, "convention") as Convention).plugins.put("concurnas", concSourceSet)
        } as SourceSetContainer

        def source = project.extensions.create('conc', Source)

        def rootFile = new File('./')

        /*
        Exception in thread "main" java.lang.NullPointerException
            at com.concurnas.runtimeCache.RuntimeCache.addEntry(RuntimeCache.java:77)
            at com.concurnas.runtimeCache.RuntimeCache.createConcCoreJar(RuntimeCache.java:155)
            at com.concurnas.runtimeCache.RuntimeCache.createConcCoreGo(RuntimeCache.java:127)
            at com.concurnas.runtimeCache.RuntimeCache.doAgumentation(RuntimeCache.java:66)
            at com.concurnas.runtimeCache.RuntimeCacheCreator.makeCache(RuntimeCacheCreator.java:19)
            at com.concurnas.runtimeCache.RuntimeCacheCreator.main(RuntimeCacheCreator.java:48)
         */
        project.getTasks().create("concRuntimeCache", { task ->
            doLast {
                "java -cp ${source.compiler}\\* com.concurnas.runtimeCache.RuntimeCacheCreator".execute(
                        [], rootFile
                ).waitForProcessOutput(new StringBuffer(), System.err)
            }
        })

        // Error: Could not find or load main class com.concurnas.build.LibCompilation
        project.getTasks().create("concLibCompilation", { task ->
                doLast {
                "java -cp ${source.compiler}\\* com.concurnas.build.LibCompilation".execute(
                        [], rootFile
                ).waitForProcessOutput(new StringBuffer(), System.err)
            }
        })

        project.getTasks().create("compileConc", { task ->
            doLast {
                (SourceSetContainer) project.properties.get("sourceSets").all { SourceSet sourceSet ->
                    ConcSourceSet concSourceSet = project.extensions
                            .getByType(ConcExtension.class)
                            .sourceSetsContainer
                            .maybeCreate(sourceSet.name)

                    concSourceSet.conc.srcDirs.each {
                        if (it.exists()) {
                            it.absoluteFile.traverse { file ->
                                new File("${project.buildDir}\\classes\\${it.name}\\${sourceSet.name}").mkdirs()

                                "java ${source.jArgs.join(' ')} -Dcom.concurnas.home=${source.compiler} -cp ${source.compiler}\\* com.concurnas.conc.ConcWrapper concc -verbose ${source.cArgs.join(' ')} -d ${project.buildDir}\\classes\\${it.name}\\${sourceSet.name} ${file.name}".execute(
                                        [], it.absoluteFile
                                ).waitForProcessOutput(System.out, System.err)
                            }
                        }
                    }
                }
            }
        })
    }
}