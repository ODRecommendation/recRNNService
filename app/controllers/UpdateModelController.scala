package controllers

import com.intel.analytics.bigdl.dataset.Sample
import com.intel.analytics.bigdl.numeric.NumericFloat
import com.intel.analytics.bigdl.optim.LocalPredictor
import com.intel.analytics.bigdl.tensor.Tensor
import javax.inject._
import models.{LoadModel, ModelParams}
import play.api.libs.json._
import play.api.mvc._
import utilities.Helper._

import scala.collection.immutable.Map

/**
  * This controller creates an `Action` to handle recommendation prediction.
  */

@Singleton
class UpdateModelController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with LoadModel {

  /**
    * Create an Action to return @ModelController status.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `POST` request with
    * a path of `/update`.
    */

  def update: Action[JsValue] = Action(parse.json) { request =>
    try {
      val requestJson = request.body.toString()
      val requestMap = jsonMapper.readValue(requestJson, classOf[Map[String, Any]])
      val modelType = requestMap("type").asInstanceOf[String]
      modelType match {
        case "recrnn" =>
          ModelParams.refresh(
            ModelParams(
              requestMap("modelPath").asInstanceOf[String],
              requestMap("transformerPath").asInstanceOf[String],
              requestMap("lookupPath").asInstanceOf[String],
              scala.util.Properties.envOrElse("configEnvironmewnt", "dev")
            )
          )
        case "wnd" =>
          ModelParams.refresh(
            ModelParams(
              requestMap("modelPath").asInstanceOf[String],
              requestMap("useIndexerPath").asInstanceOf[String],
              requestMap("itemIndexerPath").asInstanceOf[String],
              requestMap("lookupPath").asInstanceOf[String],
              scala.util.Properties.envOrElse("configEnvironmewnt", "dev")
            )
          )
      }
      Ok(Json.obj("status" -> "ok"))
    }

    catch{
      case _:Exception => BadRequest("Nah nah nah nah nah...this request contains bad characters...")
    }
  }
}
