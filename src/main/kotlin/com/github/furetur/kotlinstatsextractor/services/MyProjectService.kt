package com.github.furetur.kotlinstatsextractor.services

import com.github.furetur.kotlinstatsextractor.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
