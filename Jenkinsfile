def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.3.0-JULY-2022-WITH-FIXES-W-11403067-W-11147961" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime",
                       "enableMavenTestStage": false ]

runtimeBuild(pipelineParams)
