def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.3.0-APRIL-2023-WITH-W-15141905-W-15782010" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime",
                       "enableMavenTestStage": false ]

runtimeBuild(pipelineParams)
