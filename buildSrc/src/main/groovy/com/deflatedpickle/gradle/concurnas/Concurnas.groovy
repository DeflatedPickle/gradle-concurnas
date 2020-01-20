package com.deflatedpickle.gradle.concurnas

import org.gradle.api.Plugin
import org.gradle.api.Project

class Concurnas implements Plugin<Project> {
    @Override
    void apply(Project project) {
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
                new File(source.src).traverse {
                    "java ${source.jArgs.join(' ')} -Dcom.concurnas.home=${source.compiler} -cp ${source.compiler}\\* com.concurnas.conc.ConcWrapper concc ${source.src} ${source.cArgs.join(' ')}".execute(
                            [], rootFile
                    ).waitForProcessOutput(new StringBuffer(), System.err)
                }
            }
        })
    }
}