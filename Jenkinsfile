def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/support/1.2.0" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       // Comment public setting to get org.mule.runtime:api-annotations:jar:1.1.0-20200709 from private
                       // repo until it is released in a public repo
                       // "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime",
                       "enableMavenTestStage": false ]

runtimeBuild(pipelineParams)
