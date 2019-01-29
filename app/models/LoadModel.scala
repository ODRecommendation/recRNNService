package models

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Scheduler}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.concurrent.duration.Duration

trait LoadModel {

  val numPredicts = 20

  val yamlMapper = new ObjectMapper(new YAMLFactory()) with ScalaObjectMapper

  val configYaml = ModelParams.loadConfig("./conf/modelConfig").get
  val config = yamlMapper.readValue[Map[String, String]](configYaml.reader())

  var params = ModelParams(
    config("modelPath"),
    config("transformerPath").toString,
    config("lookupPath").toString,
    scala.util.Properties.envOrElse("configEnvironmewnt", "dev")
  )


//  val actorSystem = ActorSystem()
//  val scheduler: Scheduler = actorSystem.scheduler
//  private val task = new Runnable {
//    def run(): Unit = {
//      try {
////        ModelParams.downloadModel(params)
//        ModelParams.refresh(params)
//      }
//      catch {
//        case _: Exception => println("Model update has failed")
//      }
//    }
//  }
//
//  implicit val executor = actorSystem.dispatcher
//
//  scheduler.schedule(
//    initialDelay = Duration(5, TimeUnit.SECONDS),
//    interval = Duration(30, TimeUnit.MINUTES),
//    runnable = task)

  val jsonMapper = new ObjectMapper
  jsonMapper.registerModule(DefaultScalaModule)


}
