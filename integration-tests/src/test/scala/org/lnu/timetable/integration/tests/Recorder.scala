package org.lnu.timetable.integration.tests

import io.gatling.recorder.GatlingRecorder
import io.gatling.recorder.config.RecorderPropertiesBuilder

object Recorder extends App {

  val props = new RecorderPropertiesBuilder()
    .simulationsFolder(IDEPathHelper.mavenSourcesDirectory.toString)
    .resourcesFolder(IDEPathHelper.mavenResourcesDirectory.toString)
    .simulationPackage("simulation")
    .simulationFormatScala

  GatlingRecorder.fromMap(props.build, Some(IDEPathHelper.recorderConfigFile))
}
