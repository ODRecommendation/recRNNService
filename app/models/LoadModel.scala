package models

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Scheduler}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.concurrent.duration.Duration

trait LoadModel {

  var params = ModelParams(
    "tmp/rnnModel",
    "tmp/skuIndexer.zip",
    "tmp/skuLookUp",
    scala.util.Properties.envOrElse("configEnvironmewnt", "dev")
  )
  val numPredicts = 20
  val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)

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



}
