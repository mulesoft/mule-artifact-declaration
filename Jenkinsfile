def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.2.2-MAY-2021" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime",
                       "enableMavenTestStage": false ]

runtimeBuild(pipelineParams)
