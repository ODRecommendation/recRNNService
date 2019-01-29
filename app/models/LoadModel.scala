package models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.io.BufferedSource

trait LoadModel {
  val jsonMapper = new ObjectMapper
  jsonMapper.registerModule(DefaultScalaModule)
  val yamlMapper = new ObjectMapper(new YAMLFactory())
  yamlMapper.registerModule(DefaultScalaModule)

  val numPredicts = 20
  val configYaml: BufferedSource = ModelParams.loadConfig("./conf/modelConfig").get
  val config: Map[String, String] = yamlMapper.readValue(configYaml.reader(), classOf[Map[String, String]])

  var params: Models = config("type") match {
    case "recrnn" =>
      ModelParams(
        config("modelPath"),
        config("transformerPath"),
        config("lookupPath"),
        scala.util.Properties.envOrElse("configEnvironmewnt", "dev")
      )
    case "wnd" =>
      ModelParams(
        config("modelPath"),
        config("useIndexerPath"),
        config("itemIndexerPath"),
        config("lookupPath"),
        scala.util.Properties.envOrElse("configEnvironmewnt", "dev")
      )
  }

  params.asInstanceOf[RnnParams]









}
