package models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.io.BufferedSource

trait LoadModel {

  val numPredicts = 20
  val jsonMapper = new ObjectMapper
  jsonMapper.registerModule(DefaultScalaModule)

  val yamlMapper = new ObjectMapper(new YAMLFactory())
  yamlMapper.registerModule(DefaultScalaModule)

  val configYaml: BufferedSource = ModelParams.loadConfig("./conf/modelConfig").get
  val config: Map[String, String] = yamlMapper.readValue(configYaml.reader(), classOf[Map[String, String]])

  var params = ModelParams(
    config("modelPath"),
    config("transformerPath").toString,
    config("lookupPath").toString,
    scala.util.Properties.envOrElse("configEnvironmewnt", "dev")
  )

}
